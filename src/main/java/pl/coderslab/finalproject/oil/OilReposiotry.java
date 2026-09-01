package pl.coderslab.finalproject.oil;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface OilReposiotry extends JpaRepository<Oil, LocalDate> {
    
}
