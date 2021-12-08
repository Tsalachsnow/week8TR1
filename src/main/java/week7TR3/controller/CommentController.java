package week7TR3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import week7TR3.mediator.CommentMapper;
import week7TR3.model.Comment;
import week7TR3.model.Person;
import week7TR3.service.serviceImplementation.CommentServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;


    @RequestMapping(value = "/processComment", method = RequestMethod.POST)
    public String makeComment(HttpServletRequest request, HttpServletResponse response,
                              @ModelAttribute("commentData") Comment comment, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        //get the post id that comment was made on
        Long postId =  Long.parseLong(request.getParameter("postId"));

        //set the person
        comment.setPerson(person);

        if(commentService.createComment(person.getId(), postId, comment)){
            session.setAttribute("message", "Comment made successfully");
        }else{
            session.setAttribute("message", "Failed to comment");
        }

        return "redirect:/home";
    }


    @RequestMapping(value = "/comment/{post}", method = RequestMethod.GET)
    public String showComment(@PathVariable("post") Long post_id, Model model,  HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        Long postId = post_id;
        List<CommentMapper> commentList = commentService.getComments(postId);

        if (commentList.size() == 0){
            return "redirect:/home";
        }

        model.addAttribute("commentData", commentList);
        model.addAttribute("user", person);

        return "comment";
    }


    @RequestMapping(value = "/editComment", method = RequestMethod.POST)
    public String editComment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Long postId = Long.parseLong(request.getParameter("postId"));
        Long userId = Long.parseLong(request.getParameter("userId"));
        Long commentId = Long.parseLong(request.getParameter("commentId"));
        String comment = request.getParameter("editedComment");

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        if(commentService.editComment(commentId, person, postId, comment)){
            session.setAttribute("message", "Comment edited successfully");
        }else {
            session.setAttribute("message", "Failed to edit comment");
        }

        return "redirect:/comment/"+postId;
    }


    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    public @ResponseBody String deleteComment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "successful";

        Long postId = Long.parseLong(request.getParameter("postId"));
        Long userId = Long.parseLong(request.getParameter("userId"));
        Long commentId = Long.parseLong(request.getParameter("commentId"));

        if(commentService.deleteComment(commentId)){
            session.setAttribute("message", "comment deleted successfully");
            return "successful";
        }else {
            session.setAttribute("message", "Failed to delete comment");
            return "failed";
        }
    }

}
