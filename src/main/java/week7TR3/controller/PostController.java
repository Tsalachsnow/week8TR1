package week7TR3.controller;

import org.apache.logging.log4j.Marker;
import org.slf4j.IMarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import week7TR3.model.Person;
import week7TR3.model.Post;
import week7TR3.service.serviceImplementation.PostServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@MultipartConfig
public class PostController {

    @Autowired
    PostServiceImpl postService;


    @RequestMapping(value = "/postProcessing", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, HttpServletResponse response,
                          @ModelAttribute("post") Post post,  HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        try {

            Part part = request.getPart("file");

            //set imageName
            String imageName = part.getSubmittedFileName();
            post.setImageName(imageName);

            //set person
            post.setPerson(person);

            //path to store image
            String path = "/Users/decagon/Desktop/week7TR3/src/main/resources/static/image"+File.separator+post.getImageName();

            InputStream in = part.getInputStream();
            uploadFile(in, path);

            if(postService.createPost(person.getId(), post)) {
                session.setAttribute("message", "File uploaded successfully");
            }else{
                session.setAttribute("message", "Error uploading image to database");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }


    @RequestMapping(value = "/edit/{post}", method = RequestMethod.GET)
    public String editComment(@PathVariable("post") Long post_id, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        Long postId = post_id;

        List<Post> post = postService.getPostById(postId);

        model.addAttribute("postData", post.get(0));
        model.addAttribute("user", person);

        return "edit";
    }


    @RequestMapping(value = "/editProcessing", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                           @ModelAttribute("post") Post post) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        if(postService.editPost(person,post.getPostId(),post.getTitle(),post.getBody())) {
            session.setAttribute("message", "Post edited successfully");
        }else{
            session.setAttribute("message", "Error editing post!");
        }

        return "redirect:/home";
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    public String deleteComment(HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        Long postId = Long.parseLong(request.getParameter("postId"));

        if(postService.deletePost(postId,person.getId())){
            session.setAttribute("message", "Post deleted successfully");
        }else {
            session.setAttribute("message", "Error deleting post! or you don't have access to delete this post");
        }

        return "redirect:/home";
    }



    public boolean uploadFile(InputStream in, String path){
        boolean test = false;

        try{
            byte[] byt = new byte[in.available()];
            in.read(byt);
            FileOutputStream fops = new FileOutputStream(path);
            fops.write(byt);
            fops.flush();
            fops.close();
            test = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return test;
    }
}

