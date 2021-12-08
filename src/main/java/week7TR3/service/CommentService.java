package week7TR3.service;

import week7TR3.mediator.CommentMapper;
import week7TR3.model.Comment;
import week7TR3.model.Person;
import java.util.List;

public interface CommentService {
    boolean createComment(Long userId, Long postId,Comment comment);
    public List<CommentMapper> getComments(Long postId);
    boolean editComment(Long commentId, Person person, Long postId, String comment);
    boolean deleteComment(Long commentId);
}
