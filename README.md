# Favorita – Analiza Sprzedaży z Zapytaniami w Języku Naturalnym

Aplikacja webowa (Spring Boot) do analizy danych sprzedażowych sieci sklepów spożywczych (na bazie zbioru danych **Corporación Favorita** z Kaggle). Użytkownik może zadawać pytania w języku naturalnym (np. *"Które sklepy miały najwyższą sprzedaż w grudniu 2016?"*), a system za pomocą modelu AI generuje odpowiednie zapytanie SQL, wykonuje je na bazie danych i prezentuje wynik w formie tabeli oraz automatycznie dopasowanego wykresu.

> **[MIEJSCE NA ZDJĘCIE]**
> Zrzut ekranu głównego dashboardu / panelu nawigacyjnego aplikacji (`/dashboard`).

---

## Spis treści

- [Funkcjonalności](#funkcjonalności)
- [Stos technologiczny](#stos-technologiczny)
- [Architektura i przepływ zapytania NLP → SQL](#architektura-i-przepływ-zapytania-nlp--sql)
- [Struktura projektu](#struktura-projektu)
- [Model danych](#model-danych)
- [Endpointy API / Widoki](#endpointy-api--widoki)
- [Wymagania](#wymagania)
- [Instalacja i konfiguracja](#instalacja-i-konfiguracja)
- [Uruchomienie aplikacji](#uruchomienie-aplikacji)
- [Dane wejściowe (import CSV)](#dane-wejściowe-import-csv)
- [Znane ograniczenia / TODO](#znane-ograniczenia--todo)
- [Bezpieczeństwo](#bezpieczeństwo)

---

## Funkcjonalności

- **Rejestracja i logowanie użytkowników** (Spring Security, hasła haszowane BCrypt).
- **Zapytania w języku naturalnym do bazy danych** – użytkownik opisuje słowami, jakich danych potrzebuje, a asystent AI (Groq / model Qwen) doprecyzowuje pytanie i generuje bezpieczne zapytanie SQL.
- **Automatyczne dopasowanie wizualizacji** – wynik zapytania jest analizowany (liczba kolumn/wierszy, typy danych) i prezentowany jako metryka, wykres słupkowy, kołowy, liniowy lub tabela.
- **Historia zapytań użytkownika** – każde wykonane zapytanie SQL jest zapisywane i dostępne do przeglądu.
- **Zarządzanie danymi referencyjnymi** – panele CRUD z paginacją dla sklepów (`Store`) i świąt/wydarzeń specjalnych (`HolidayEvent`).
- **Walidacja i sanityzacja SQL** wygenerowanego przez model AI przed wykonaniem na bazie.

> **[MIEJSCE NA ZDJĘCIE]**
> Zrzut ekranu formularza zapytania w języku naturalnym (`/nlp/query`) wraz z pytaniami doprecyzowującymi.

> **[MIEJSCE NA ZDJĘCIE]**
> Zrzut ekranu wyniku zapytania – tabela danych + wygenerowany wykres (`/nlp/query/sql`).

---

## Stos technologiczny

| Kategoria | Technologia |
|---|---|
| Język | Java 17 |
| Framework | Spring Boot 4.1.1 |
| Warstwa danych | Spring Data JPA / Hibernate |
| Baza danych | MySQL |
| Widoki | Thymeleaf + Thymeleaf Extras Spring Security 6 |
| Bezpieczeństwo | Spring Security (BCrypt) |
| AI / LLM | Spring AI (BOM 2.0.1), Groq API (model Qwen) |
| Wykonywanie SQL | Spring `JdbcTemplate` |
| Build | Maven (Maven Wrapper `mvnw`) |
| Inne | Lombok, Spring Boot DevTools |

---

## Architektura i przepływ zapytania NLP → SQL

1. Użytkownik wpisuje pytanie w języku naturalnym w formularzu (`query/enterQuery.html`).
2. `NLPControler` → `NlpQueryService` przy pomocy `PromptCreatorService` i `SchemaDescriptionService` budują prompt opisujący schemat bazy i pytanie użytkownika.
3. Zapytanie trafia do modelu AI przez `GrogClient` (klient API Groq).
4. Jeśli pytanie jest niejednoznaczne, model zwraca pytania doprecyzowujące (`query/questions.html`), kontekst rozmowy trzymany jest w `ConversationMemory`.
5. Po uzyskaniu odpowiedzi model generuje zapytanie SQL, które przechodzi przez:
   - `SQLPrepare` – czyszczenie/formatowanie odpowiedzi modelu,
   - `SQLvalidator` – walidacja bezpieczeństwa zapytania.
6. `ExecuteQueryService` wykonuje zapytanie na bazie MySQL przez `JdbcTemplate`.
7. `DataProfiler` analizuje strukturę wyniku (typy kolumn, liczba wierszy), a `VisualizationChartAnalizer` dobiera typ wizualizacji (`METRIC`, `BAR_CHART`, `PIE_CHART`, `LINE_CHART`, `TABLE`).
8. `QueryResponseService` składa pełną odpowiedź (dane + sugerowany wykres), która trafia do widoku `query/result.html`.
9. Zapytanie SQL zapisywane jest w historii użytkownika (`UserHistory`).

---

## Struktura projektu

```
src/main/java/pl/coderslab/finalproject/
├── FinalProjectApplication.java     # klasa startowa Spring Boot
├── charts/                          # dobór i przygotowanie danych do wykresów
├── dashbord/                        # kontroler głównego panelu
├── dataprofila/                     # analiza struktury wyniku zapytania
├── holidayevent/                    # CRUD świąt/wydarzeń specjalnych
├── items/                           # encja produktów (katalog)
├── nlp/                             # pipeline NLP → SQL (integracja z AI)
│   └── conversationmemory/          # pamięć kontekstu rozmowy z użytkownikiem
├── oil/                             # dane cen ropy (szereg czasowy)
├── queryresoult/                    # wykonywanie zapytań SQL i budowanie odpowiedzi
├── sale/                            # encja sprzedaży
├── sql/                             # przygotowanie i walidacja SQL wygenerowanego przez AI
├── stores/                          # CRUD sklepów
├── storetransaction/                # dane transakcji sklepowych
└── user/                            # rejestracja, logowanie, bezpieczeństwo
    ├── hisotry/                     # historia zapytań SQL użytkownika
    └── security/                    # konfiguracja Spring Security

src/main/resources/
├── application.properties           # konfiguracja (poza repozytorium, patrz sekcja Instalacja)
└── templates/                        # widoki Thymeleaf (dashboard, user, store, holiday, query)
```

---

## Model danych

| Encja | Opis | Kluczowe pola |
|---|---|---|
| `User` | Konto użytkownika | `id`, `userName` (unikalny), `password` (hash) |
| `UserHistory` | Historia wykonanych zapytań SQL | `id`, `sqlText`, `user` |
| `Store` | Sklep | `storeNumber` (PK), `city`, `state`, `type`, `cluster` |
| `Item` | Produkt | `itemNumber` (PK), `itemFamily`, `itemClass`, `perishable` |
| `Sale` | Wpis sprzedażowy | `id`, `date`, `store`, `item`, `unitSales`, `onPromotion` |
| `HolidayEvent` | Święto / wydarzenie specjalne | `id`, `date`, `holidayType`, `locale`, `localeName`, `description`, `transferred` |
| `Oil` | Dzienna cena ropy | `date` (PK), `price` |
| `StoreTransaction` | Liczba transakcji w sklepie danego dnia | `id`, `transactionDate`, `store`, `transactionsCount` |

**Relacje:** `User` 1—N `UserHistory`, `Store` 1—N `Sale`, `Store` 1—N `StoreTransaction`, `Item` 1—N `Sale`.

> **[MIEJSCE NA ZDJĘCIE]**
> Diagram ERD modelu danych (schemat relacji między tabelami).

---

## Endpointy API / Widoki

### Użytkownik
| Metoda | Ścieżka | Opis |
|---|---|---|
| GET | `/user/save` | Formularz rejestracji |
| POST | `/user/save` | Rejestracja nowego użytkownika |
| GET | `/user/login` | Formularz logowania |
| POST | `/user/login` | Logowanie |
| GET | `/user/logout` | Wylogowanie |

### Dashboard
| Metoda | Ścieżka | Opis |
|---|---|---|
| GET | `/dashboard` | Panel nawigacyjny |

### Sklepy
| Metoda | Ścieżka | Opis |
|---|---|---|
| GET | `/stores/list?page=0` | Lista sklepów (paginacja, 20/str.) |
| GET | `/stores/new` | Formularz dodania sklepu |
| POST | `/stores/new?isEdit=false` | Zapis / edycja sklepu |
| GET | `/stores/{storeNumber}/edit` | Formularz edycji |
| GET | `/stores/{storeNumber}/delete` | Usunięcie sklepu |

### Święta / wydarzenia
| Metoda | Ścieżka | Opis |
|---|---|---|
| GET | `/holiday/list?page=0` | Lista świąt (paginacja, 20/str.) |
| GET | `/holiday/new` | Formularz dodania |
| POST | `/holiday/new?isEdit=false` | Zapis / edycja |
| GET | `/holiday/{holidayID}/edit` | Formularz edycji |
| GET | `/holiday/{holidayId}/delete` | Usunięcie |

### Zapytania NLP → SQL
| Metoda | Ścieżka | Opis |
|---|---|---|
| GET | `/nlp/query` | Formularz wpisania pytania w języku naturalnym |
| POST | `/nlp/query/claryfication` | Generowanie pytań doprecyzowujących (AI) |
| POST | `/nlp/query/sql` | Generowanie i wykonanie SQL na podstawie odpowiedzi użytkownika |
| POST | `/nlp/query/exec` | Wykonanie własnego zapytania SQL |
| GET | `/historySql/list?page=0` | Historia zapytań zalogowanego użytkownika |

> **Uwaga:** kontroler `charts/ChartsControler` jest obecnie niekompletny (brak zaimplementowanych metod) – patrz [Znane ograniczenia](#znane-ograniczenia--todo).

---

## Wymagania

- Java 17+
- Maven (lub użyj załączonego `./mvnw`)
- MySQL 8.x (lokalnie lub w kontenerze)
- Klucz API do Groq (https://groq.com) do działania modułu NLP

---

## Instalacja i konfiguracja

1. **Sklonuj repozytorium i przejdź do katalogu projektu.**

2. **Utwórz bazę danych MySQL:**

   ```sql
   CREATE DATABASE favorita_db CHARACTER SET utf8mb4;
   ```

3. **Skonfiguruj `src/main/resources/application.properties`** (plik jest w `.gitignore` – nie jest wersjonowany, musisz go stworzyć samodzielnie na podstawie poniższego wzoru):

   ```properties
   spring.application.name=finalProject

   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

   spring.datasource.url=jdbc:mysql://localhost:3306/favorita_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=TWOJE_HASLO

   # Klucz API do Groq – NIE wklejaj go do repozytorium
   grog.api.key=TWOJ_KLUCZ_GROQ

   spring.mvc.async.request-timeout=300000
   ```

   > ⚠️ **Bezpieczeństwo:** w lokalnym pliku `application.properties` znajduje się aktywny klucz API do Groq w postaci zwykłego tekstu. Plik jest na szczęście objęty `.gitignore`, więc nie trafił do repozytorium – **należy jednak jak najszybciej wyrotować (zregenerować) ten klucz** w panelu Groq, ponieważ mógł zostać ujawniony poza kontrolą wersji (np. w backupach, historii terminala czy udostępnionych plikach).

4. Baza tabel zostanie utworzona automatycznie przy starcie aplikacji (`spring.jpa.hibernate.ddl-auto=update`).

---

## Uruchomienie aplikacji

```bash
./mvnw spring-boot:run
```

Aplikacja domyślnie wystartuje na `http://localhost:8080`.

Pierwsze kroki po uruchomieniu:
1. Zarejestruj konto: `http://localhost:8080/user/save`
2. Zaloguj się: `http://localhost:8080/user/login`
3. Wejdź na panel: `http://localhost:8080/dashboard`

---

## Dane wejściowe (import CSV)

Katalog `data/` (nieśledzony przez Git – zbyt duże pliki) zawiera zbiór danych **Corporación Favorita Grocery Sales Forecasting** z Kaggle:

| Plik | Zawartość |
|---|---|
| `stores.csv` | Dane sklepów |
| `items.csv` | Katalog produktów |
| `train.csv` / `trainsmall.csv` | Historyczne dane sprzedażowe |
| `test.csv` | Zbiór testowy |
| `transactions.csv` | Liczba transakcji per sklep/dzień |
| `holidays_events.csv` | Kalendarz świąt i wydarzeń |
| `oil.csv` | Ceny ropy w czasie |
| `sample_submission.csv` | Szablon zgłoszenia (Kaggle) |

Dane te należy zaimportować do tabel MySQL odpowiadających encjom (`store`, `item`, `sale`, `holiday_event`, `oil`, `store_transaction`) przed korzystaniem z modułu zapytań NLP.

---

## Znane ograniczenia / TODO

- `charts/ChartsControler` – kontroler bez zaimplementowanej logiki, do dokończenia.
- `SecurityConfig` – wszystkie żądania są obecnie dopuszczone (`permitAll`), a CSRF jest wyłączone; przed wdrożeniem produkcyjnym wymaga przeglądu i zawężenia uprawnień.
- Literówki w nazwach pakietów/klas do rozważenia w ramach refaktoryzacji: `hisotry` (→ `history`), `OilReposiotry` (→ `OilRepository`), `DashboeardControler`/`ChartsControler` (→ `...Controller`).
- Brak zestawu testów automatycznych pokrywających pipeline NLP → SQL.

---

## Bezpieczeństwo

- Hasła użytkowników są haszowane (BCrypt) przed zapisem do bazy.
- Wygenerowane przez AI zapytania SQL przechodzą walidację (`SQLvalidator`) przed wykonaniem – zalecany dalszy przegląd pod kątem ochrony przed SQL injection / niebezpiecznymi operacjami (np. `DROP`, `DELETE` bez `WHERE`).
- Plik `application.properties` z danymi dostępowymi do bazy i kluczem API **nie powinien** być commitowany do repozytorium (obecnie poprawnie ujęty w `.gitignore`).
