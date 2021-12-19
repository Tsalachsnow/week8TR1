package week7TR3.service.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import week7TR3.mediator.PostMapper;
import week7TR3.model.Comment;
import week7TR3.model.Likes;
import week7TR3.model.Person;
import week7TR3.model.Post;
import week7TR3.repository.CommentRepository;
import week7TR3.repository.LikesRepository;
import week7TR3.repository.PersonRepository;
import week7TR3.repository.PostRepository;
import week7TR3.service.PostService;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    final PostRepository postRepository;
    final LikesRepository likesRepository;
    final CommentRepository commentRepository;
    final PersonRepository personRepository;


    public PostServiceImpl(PostRepository postRepository, LikesRepository likesRepository, CommentRepository commentRepository, PersonRepository personRepository) {
        this.postRepository = postRepository;
        this.likesRepository = likesRepository;
        this.commentRepository = commentRepository;
        this.personRepository = personRepository;
    }


    public boolean createPost(Long userId, Post post) {
        boolean result = false;

        Person user = personRepository.findById(userId).get();

        if(user != null){
            post.setChecker("ACTIVE");
            postRepository.save(post);
            result = true;
        }else result = false;

        return result;
    }


    public List<Post> getPostById(Long postId){
        List<Post> postList = postRepository.findPostByPostId(postId);

      if(postList == null) {
          System.out.println("Something went wrong1 ");
      }
        return postList;
    }


    public List<PostMapper> getPost(Person currentUser) {
        List<PostMapper> posts = new ArrayList<>();

        List<Post> postData = postRepository.findAllByCheckerIsOrderByPostIdDesc("ACTIVE");

        for (Post postEach:postData) {

            PostMapper post = new PostMapper();
            post.setId(postEach.getPostId());
            post.setTitle(postEach.getTitle());
            post.setBody(postEach.getBody());
            post.setImageName("/image/"+postEach.getImageName());
            post.setName(postEach.getPerson().getLastname()+ " "+ postEach.getPerson().getFirstname());

            //the total number of likes on this particular post
            List<Likes> numberOfLikes = likesRepository.findAllByPostPostId(postEach.getPostId());
            int likeCount = numberOfLikes.size();
            post.setNoLikes(likeCount);

            //the total number of comments on this particular post
            List<Comment> noOfComment = commentRepository.findAllByPostPostId(postEach.getPostId());
            int commentCount = noOfComment.size();
            post.setNoComments(commentCount);

            //return true if current user liked this post, else false
            List<Likes> postLiked = likesRepository.findAllByPostPostIdAndPersonId(postEach.getPostId(), currentUser.getId());
            if(postLiked.size() > 0){
                post.setLikedPost(true);
            }

            posts.add(post);
        }


        return posts;
    }


    public boolean editPost(Person person, Long postId, String title, String body) {
        boolean status = false;
        Post post = postRepository.findById(postId).get();
           if(post != null){
               post.setTitle(title);
               post.setBody(body);
               postRepository.save(post);

               status = true;
           }
        return status;
    }


    public boolean deletePost(Long postId, Long personId){
        boolean status =  false;
        Post post = postRepository.findPostByPostIdAndPersonId(postId, personId);

        if(post != null){
            post.setChecker("INACTIVE");
            postRepository.save(post);
            status = true;
        }
        return status;
    }
}
