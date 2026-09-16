package pl.coderslab.finalproject.user.hisotry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.finalproject.user.User;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    Page<UserHistory> findAllByUser(
            User user,
            Pageable pageable
    );
}
