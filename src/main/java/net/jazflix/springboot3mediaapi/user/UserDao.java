package net.jazflix.springboot3mediaapi.user;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDao {
    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User(1L, "John",
                LocalDate.now().minusYears(30)));

        users.add(new User(2L, "Jane",
                LocalDate.now().minusYears(25)));

        users.add(new User(3L, "John",
                LocalDate.now().minusYears(40)));
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findOne(Long id) {
        return users.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    public Long insertUser(User user) {
        Long id = users.stream()
                .map(x -> x.getId())
                .reduce((x, y) -> x > y ? x : y)
                .orElse(0L);

        user.setId(++id);
        users.add(user);

        return id;
    }

    public Optional<User> deleteUser(Long id) {
        Optional<User> user = findOne(id);
        user.ifPresent(users::remove);
        return user;
    }
}
