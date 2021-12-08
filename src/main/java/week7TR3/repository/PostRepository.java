package week7TR3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import week7TR3.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostByPostId(Long postId);
    @Transactional
    void deletePostByPostIdAndPersonId(Long postId, Long personId);
    Post findPostByPostIdAndPersonId(Long postId, Long personId);
    List<Post> findAllByCheckerIsOrderByPostIdDesc(String checker);
}
