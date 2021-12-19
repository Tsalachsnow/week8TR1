package week7TR3.service.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import week7TR3.model.Likes;
import week7TR3.model.Person;
import week7TR3.model.Post;
import week7TR3.repository.LikesRepository;
import week7TR3.repository.PostRepository;
import week7TR3.service.LikeService;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;

    public LikeServiceImpl(LikesRepository likesRepository, PostRepository postRepository) {
        this.likesRepository = likesRepository;
        this.postRepository = postRepository;
    }


    public boolean likePost(Person person, Long postId, String action){
        boolean result = false;

        Post post = postRepository.findById(postId).get();
        if(post != null){
            Likes like = new Likes();
            like.setPerson(person);
            like.setPost(post);

            if(action.equals("1")){
                likesRepository.save(like);
                System.out.println("save");
            }else{
                likesRepository.deleteLikesByPostAndPerson(post,person);
                System.out.println("delete");
            }

            result = true;
        }

        return result;
    }
}
