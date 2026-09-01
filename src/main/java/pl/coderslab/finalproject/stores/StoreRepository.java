package pl.coderslab.finalproject.stores;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {


    Store findFirstByCity(String city);
}
