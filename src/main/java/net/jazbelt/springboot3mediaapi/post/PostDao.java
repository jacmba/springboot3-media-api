package net.jazbelt.springboot3mediaapi.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostDao {

    private final PostRepository postRepository;

    @Autowired
    public PostDao(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(Post post) {
        return this.postRepository.save(post);
    }
}
