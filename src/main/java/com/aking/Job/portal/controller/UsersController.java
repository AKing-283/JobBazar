package com.aking.Job.portal.controller;


import com.aking.Job.portal.entity.UserType;
import com.aking.Job.portal.entity.Users;
import com.aking.Job.portal.services.UsersService;
import com.aking.Job.portal.services.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String register(Model model){
       List<UserType> userTypes = usersTypeService.getAll();
       model.addAttribute("getAllTypes",userTypes);
       model.addAttribute("user", new Users());
       return "register";
    }

    @PostMapping("/register/new_user")
    public String userRegistration(@Valid Users users,Model model){
        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());

        if (optionalUsers.isPresent()){
            model.addAttribute("error","This Email  already exists please login with this or click on forgot password");
            List<UserType> userTypes = usersTypeService.getAll();
            model.addAttribute("getAllTypes",userTypes);
            model.addAttribute("user", new Users());
            return "register";
        };
        usersService.addNew(users);
     return "redirect:/dashboard/";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null) {
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }
}
