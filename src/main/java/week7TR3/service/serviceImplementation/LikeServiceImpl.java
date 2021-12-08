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

    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private PostRepository postRepository;


    public boolean likePost(Person person, Long postId, String action){
        boolean result = false;

        Post post = postRepository.findById(postId).get();

        try{
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
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
