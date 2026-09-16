package pl.coderslab.finalproject.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    Boolean existsByUserName(String userName);
    List<User> findFirstByUserName(String userName);
}
