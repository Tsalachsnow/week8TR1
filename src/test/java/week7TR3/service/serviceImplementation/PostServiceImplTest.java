package week7TR3.service.serviceImplementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import week7TR3.model.Person;
import week7TR3.model.Post;
import week7TR3.repository.PersonRepository;
import week7TR3.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
  private  PostRepository postRepository;
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PostServiceImpl PostServiceImpl;



    Post post;
    Person person;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setBody("Hello World");
        post.setImageName("img04.png");
        post.setTitle("greetings");
        post.setChecker("ACTIVE");

        person = new Person();
        person.setFirstname("emeka");
        person.setLastname("enyiocha");
        person.setPassword("er0swccd-snow");
        person.setEmail("snowshaddy@gmail.com");
        person.setGender("male");
        person.setDob("02/04/1995");
    }

    @Test
    void createPost() {
        //mock personRepository
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        //mock postRepository
        when(postRepository.save(any(Post.class))).thenReturn(post);

        boolean result = PostServiceImpl.createPost(1L, post);
        assertTrue(result);

//        personServiceImpl.getUser("snowshaddy@gmail.com", person.getPassword());

        verify(postRepository, times(1)).save(any(Post.class));
        verify(personRepository, times(1)).findById(anyLong());
//        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void editPost() {
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        boolean status = PostServiceImpl.editPost(person,1L, "get started", "we are getting started");
        assertTrue(status);

        verify(postRepository, times(1)).save(any(Post.class));
        verify(postRepository, times(1)).findById(anyLong());
    }

    @Test
    void deletePost() {
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postRepository.findPostByPostIdAndPersonId(1L, 1L)).thenReturn(post);

        boolean status = PostServiceImpl.deletePost(1L,1L);
        assertTrue(status);

        verify(postRepository, times(1)).save(any(Post.class));
        verify(postRepository, times(1)).findPostByPostIdAndPersonId(1L,1L);
    }

}