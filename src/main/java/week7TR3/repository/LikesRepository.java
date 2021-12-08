package week7TR3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import week7TR3.model.Likes;
import week7TR3.model.Person;
import week7TR3.model.Post;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllByPostPostId(Long postId);
    List<Likes> findAllByPostPostIdAndPersonId(Long postId, Long personId);
    @Transactional
    void deleteLikesByPostAndPerson(Post post, Person person);
}
