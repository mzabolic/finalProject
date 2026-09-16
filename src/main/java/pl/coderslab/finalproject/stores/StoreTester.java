package pl.coderslab.finalproject.stores;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StoreTester implements CommandLineRunner {
    private final StoreRepository storeRepository;
    @Override
    public void run(String... args) throws Exception {
//        Long test = storeRepository.count();
//        log.info("There are :{} stores", test);
//        log.info("Znaleziono sklep: {}", storeRepository.findFirstByCity("Quito").toString());
    }
}
