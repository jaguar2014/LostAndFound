package com.ashu.demo.controller;

import com.ashu.demo.model.AppUser;
import com.ashu.demo.model.Lost;

import com.ashu.demo.repository.AppRoleRepository;
import com.ashu.demo.repository.AppUserRepository;
import com.ashu.demo.repository.LostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    AppRoleRepository roleRepository;

    @Autowired
    AppUserRepository userRepository;

    @Autowired
    LostRepository lostRepository;

    @GetMapping("/")
    public String showIndex(Model model,Authentication auth) {

        Iterable<Lost> losts  = lostRepository.findAll();
       // Iterable<AppUser> appUsers = userRepository.findByPotLucksIn(losts);
       // Iterable<AppUser> appUsers = potLuckRepository.find
        model.addAttribute("newLosts", losts);
       //model.addAttribute("appUser",appUsers);


        return "index";

    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(Model model)
    {
        model.addAttribute("newuser",new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("newuser") AppUser user, BindingResult result, HttpServletRequest request)
    {
        if(result.hasErrors())
        {
            return "register";
        }


        user.addRole(roleRepository.findAppRoleByRoleName("USER"));
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/addlost")
    private String addLost(Model model){
        Lost lost = new Lost();
        model.addAttribute("lost", lost);


        return "lostform";
    }

    @PostMapping("/addlost")
    public String addLostInfo(@Valid @ModelAttribute("lost") Lost lost, Model model, BindingResult result, Authentication auth) {
        if (result.hasErrors()) {
            return "lostform";
        }
       AppUser appUser=  userRepository.findAppUserByUsername(auth.getName());
       lost.addAppUser(appUser);

       lostRepository.save(lost);




        return "redirect:/lostlost";
    }

    @GetMapping("/listlost")
    public String showLostInfo(Model model,Authentication auth) {

        AppUser appUser=  userRepository.findAppUserByUsername(auth.getName());

        List<Lost> losts = lostRepository.findByAppUsers(appUser);
       // List<PotLuck> potLucks  = potLuckRepository.findPotLucksByAppUsersIn(Arrays.asList(appUser));
        model.addAttribute("newPots", losts);
        model.addAttribute("appUser", appUser);
        return "listlostform";
    }
    @GetMapping("/editlost/{id}")
    public String editLost(@PathVariable("id") long id, Model model,Authentication auth){
        Lost lost = lostRepository.findOne(id);
        model.addAttribute("lostAndFound", lost);

        return "lostform";


    }
}
