package net.jazflix.springboot3mediaapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDao {
    /*private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User(1L, "John",
                LocalDate.now().minusYears(30)));

        users.add(new User(2L, "Jane",
                LocalDate.now().minusYears(25)));

        users.add(new User(3L, "John",
                LocalDate.now().minusYears(40)));
    }*/

    private final UserRepository repository;

    @Autowired
    public UserDao(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        // return users;

        return repository.findAll();
    }

    public Optional<User> findOne(Long id) {
        /* return users.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst(); */

        return repository.findById(id);
    }

    public Long insertUser(User user) {
        /* Long id = users.stream()
                .map(x -> x.getId())
                .reduce((x, y) -> x > y ? x : y)
                .orElse(0L);

        user.setId(++id);
        users.add(user);

        return id; */

        User result = repository.save(user);
        return user.getId();
    }

    public Optional<User> deleteUser(Long id) {
        Optional<User> user = findOne(id);
        /* user.ifPresent(users::remove);
        return user; */

        repository.deleteById(id);
        return user;
    }
}
