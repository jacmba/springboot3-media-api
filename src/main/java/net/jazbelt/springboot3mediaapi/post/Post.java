package net.jazbelt.springboot3mediaapi.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.jazbelt.springboot3mediaapi.user.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
