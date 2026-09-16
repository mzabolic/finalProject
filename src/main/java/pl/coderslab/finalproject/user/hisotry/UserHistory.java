package pl.coderslab.finalproject.user.hisotry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.finalproject.user.User;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @Lob
    private String sqlText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
