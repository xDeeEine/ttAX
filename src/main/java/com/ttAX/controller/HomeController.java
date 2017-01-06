package com.ttAX.controller;

import com.ttAX.model.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.ttAX.model.Users;
import com.ttAX.model.Roles;
import com.ttAX.service.UserService;




@Controller
public class HomeController {

    private UserService userService;
    private final String loginMessage = "Login already exist";

    @Autowired(required=true)
    @Qualifier(value = "userService")
    public void setUserService(UserService us){
        this.userService = us;
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String welcomePage(Model model, String login, Roles roles, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        login = auth.getName();
        model.addAttribute("title", "Главная");
        model.addAttribute("messages", new Messages());

        if (request.isUserInRole("admin")){
            model.addAttribute("listMessages", this.userService.listMessages());
        }

        if (request.isUserInRole("user")){
            model.addAttribute("listMessages", this.userService.listMessagesByLogin(login));
        }

        return "homePage";
    }

    @RequestMapping("/home/remove/{id}")
    public String removeMessages(@PathVariable("id") int id){
        this.userService.removeMessage(id);
        return "redirect:/home";
    }

    @RequestMapping("/home/sort_sender")
    public String sortSender(Model model, String query, HttpServletRequest request){
        query = "from Messages order by sender asc";
        model.addAttribute("listMessages", this.userService.sortTable(query));
        return "homePage";
    }
}
