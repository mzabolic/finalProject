package pl.coderslab.finalproject.stores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreRepository extends JpaRepository<Store,Long> {


    Store findFirstByCity(String city);


}
