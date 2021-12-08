package week7TR3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import week7TR3.model.Person;
import week7TR3.service.serviceImplementation.LikeServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LikeController {
    @Autowired
    LikeServiceImpl likeService;


    @RequestMapping(value = "/processLike", method = RequestMethod.POST)
    public @ResponseBody String likePost(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        Long postId = Long.parseLong(request.getParameter("postId"));
        String action = request.getParameter("action");

        if(likeService.likePost(person, postId, action)){
            return "successful";
        }else{
            session.setAttribute("message", "server error");
        }

        return "";
    }
}
