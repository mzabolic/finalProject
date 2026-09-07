package pl.coderslab.finalproject.storetransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreTransactionRepository extends JpaRepository<StoreTransaction, Long> {

    @Query(value = "select coalesce(sum(transactions_count),0) from store_transaction",
    nativeQuery = true)
    Long countAlltransactions ();
}
