package net.jazflix.springboot3mediaapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") Long id) {
        Optional<User> user = userDao.findOne(id);
        if (user.isPresent()) {
            return user.get();
        }

        throw new UserNotFoundException(id);
    }

    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody User user) {
        Long id =  userDao.insertUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable("id") Long id) {
        Optional<User> user = userDao.deleteUser(id);
        if(user.isPresent()) {
            return user.get();
        }

        throw new UserNotFoundException(id);
    }
}
