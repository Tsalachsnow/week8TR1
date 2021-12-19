package week7TR3.service.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import week7TR3.mediator.CommentMapper;
import week7TR3.model.Comment;
import week7TR3.model.Person;
import week7TR3.model.Post;
import week7TR3.repository.CommentRepository;
import week7TR3.repository.PostRepository;
import week7TR3.service.CommentService;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }


    public boolean createComment(Long userId, Long postId,Comment comment){
        boolean result = false;
        Post post = postRepository.findById(postId).get();
        if(post != null){
            comment.setPost(post);

            commentRepository.save(comment);
            result = true;
        }
        return result;
    }


    public List<CommentMapper> getComments(Long postId){
        List<CommentMapper> comments = new ArrayList();
        List<Comment> commentsData = commentRepository.findAllByPostPostId(postId);

        for (Comment commentEach:commentsData) {
            CommentMapper comment = new CommentMapper();
            comment.setId(commentEach.getId());
            comment.setPostId(commentEach.getPost().getPostId());
            comment.setComment(commentEach.getComment());
            comment.setUsername(commentEach.getPerson().getLastname()+" "+commentEach.getPerson().getFirstname());
            comment.setTitle(commentEach.getPost().getTitle());
            comment.setImageName("/image/"+commentEach.getPost().getImageName());
            comment.setUserId(commentEach.getPerson().getId());

            comments.add(comment);
        }

        return comments;
    }


    public boolean editComment(Long commentId, Person person, Long postId, String comment) {
        boolean status = false;
        Post post = postRepository.findById(postId).get();

        Comment data = commentRepository.findCommentById(commentId);

        if(post != null && data != null) {
            data.setComment(comment);
            data.setPerson(person);
            data.setPost(post);
            commentRepository.save(data);

            status = true;
        }

        return status;
    }


    public boolean deleteComment(Long commentId){
        boolean status =  false;

        if(commentRepository.existsById(commentId)) {
            commentRepository.deleteCommentById(commentId);
            status = true;
        }
        return status;
    }
}
