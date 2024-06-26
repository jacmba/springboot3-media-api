package net.jazbelt.springboot3mediaapi.user;

import jakarta.validation.Valid;
import net.jazbelt.springboot3mediaapi.post.Post;
import net.jazbelt.springboot3mediaapi.post.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDao userDao;
    private final PostDao postDao;
    private final MessageSource messageSource;

    @Autowired
    public UserController(UserDao userDao,
                          PostDao postDao,
                          MessageSource messageSource) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.messageSource = messageSource;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @GetMapping("{id}")
    public EntityModel<User> getUser(@PathVariable("id") Long id) {
        Optional<User> user = userDao.findOne(id);
        if (user.isPresent()) {
            EntityModel<User> model = EntityModel.of(user.get());

            WebMvcLinkBuilder link = linkTo(
                    methodOn(getClass()).getAllUsers()
            );
            model.add(link.withRel("all-users"));

            return model;
        }

        throw new UserNotFoundException(id);
    }

    @PostMapping
    public ResponseEntity<User> postUser(@Valid @RequestBody User user) {
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

    @GetMapping("hello/{id}")
    public String helloUser(@PathVariable("id") Long id) {
        EntityModel<User> user = getUser(id);
        Locale locale = LocaleContextHolder.getLocale();
        String msg = messageSource.getMessage("hello", null, locale);

        return msg + ", " + user.getContent().getName() + "!!";
    }

    @GetMapping("{id}/posts")
    public List<Post> getUserPosts(@PathVariable("id") Long id) {
        Optional<User> user = userDao.findOne(id);
        if(user.isPresent()) {
            return user.get().getPosts();
        }

        throw new UserNotFoundException(id);
    }

    @PostMapping("{id}/posts")
    public ResponseEntity<Post> postCreateUserPost(
            @PathVariable("id") Long id,
            @Valid @RequestBody Post post
    ) {
        Optional<User> user = userDao.findOne(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        post.setUser(user.get());
        Post result = postDao.savePost(post);
        return ResponseEntity.ofNullable(result);
    }
}
