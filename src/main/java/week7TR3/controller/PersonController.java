package week7TR3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import week7TR3.mediator.Login;
import week7TR3.mediator.PostMapper;
import week7TR3.model.Comment;
import week7TR3.model.Person;
import week7TR3.model.Post;
import week7TR3.service.serviceImplementation.PersonServiceImpl;
import week7TR3.service.serviceImplementation.PostServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class PersonController {

    private final PersonServiceImpl userService;

    private final PostServiceImpl postService;

    public PersonController(PersonServiceImpl userService, PostServiceImpl postService) {
        this.userService = userService;
        this.postService = postService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.removeAttribute("message");

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("person", new Person());
        mav.addObject("login", new Login());

        return mav;
    }


    @PostMapping("/validate")
    public ResponseEntity<String> validateObject(@RequestBody @Valid Person person) {
        return new ResponseEntity("User validated successfully", HttpStatus.OK);
    }


    @RequestMapping(value = "/processLogout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/";
    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView showHome(HttpServletRequest request, HttpServletResponse response) {

        HttpSession httpSession = request.getSession();
        Person person = (Person) httpSession.getAttribute("user");

        if(person == null) {
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("person", new Person());
            mav.addObject("login", new Login());
            httpSession.setAttribute("mess", "!!!Please Login");
            return mav;
        }

        ModelAndView mav = new ModelAndView("home");
        mav.addObject("post", new Post());
        mav.addObject("commentData", new Comment());

        List<PostMapper> post = postService.getPost(person);

        mav.addObject("user", person);
        mav.addObject("posts", post);

        return mav;
    }


    @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute("person") Person user) {

        HttpSession httpSession = request.getSession();

        if(userService.createUser(user)){
            httpSession.setAttribute("mess", "Successfully registered!!!");
        }else{
            httpSession.setAttribute("mess", "Failed to register or email already exist");
        }

        return "redirect:/";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("login", new Login());

        return mav;
    }


    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public String loginProcess(HttpServletRequest request, HttpServletResponse response,
                                     @ModelAttribute("login") Login login) {

        Person user = userService.getUser(login.getEmail(), login.getPassword());

        HttpSession httpSession = request.getSession();

        if (user != null) {
            httpSession.setAttribute("user", user);
            return "redirect:/home";
        } else {
            httpSession.setAttribute("mess", "Email or Password is wrong!!!");
            return "redirect:/";
        }
    }
}
