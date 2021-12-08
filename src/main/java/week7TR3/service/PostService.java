package week7TR3.service;

import week7TR3.mediator.PostMapper;
import week7TR3.model.Person;
import week7TR3.model.Post;
import java.util.List;

public interface PostService {
    boolean createPost(Long userId, Post post);
    List<PostMapper> getPost(Person currentUser);
}
