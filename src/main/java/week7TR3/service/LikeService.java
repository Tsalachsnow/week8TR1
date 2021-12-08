package week7TR3.service;

import week7TR3.model.Person;

public interface LikeService {
    public boolean likePost(Person person, Long postId, String action);
}
