package com.ashu.demo.controller;

import com.ashu.demo.model.AppRole;
import com.ashu.demo.model.AppUser;
import com.ashu.demo.model.Lost;
import com.ashu.demo.repository.AppRoleRepository;
import com.ashu.demo.repository.AppUserRepository;
import com.ashu.demo.repository.CategoryRepository;
import com.ashu.demo.repository.LostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/")
    public String showIndex(Model model, Authentication auth) {

        Iterable<Lost> losts = lostRepository.findAll();

        Iterable<Lost> petCategory = lostRepository.findByCategoryNameIgnoreCase("Pet");
        Iterable<Lost> clothCategory = lostRepository.findByCategoryNameIgnoreCase("Cloth");
        Iterable<Lost> otherCategory = lostRepository.findByCategoryNameIgnoreCase("Other");


        model.addAttribute("petCategory", petCategory);
        model.addAttribute("clothCategory", clothCategory);
        model.addAttribute("otherCategory", otherCategory);



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
    public String registerUser(Model model) {
        model.addAttribute("newuser", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("newuser") AppUser user, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "register";
        }


        user.addRole(roleRepository.findAppRoleByRoleName("USER"));
        userRepository.save(user);
        return "redirect:/";
    }


    @GetMapping("/addlost")
    public String addLostGeneral(Authentication auth){
        AppRole role = roleRepository.findByAppUsers(userRepository.findAppUserByUsername(auth.getName()));
        String rolename = role.getRoleName();

        if(rolename.equals("ADMIN")){
            return "redirect:/addlostadmin";
        }
        return "redirect:/addlostuser";


    }

    @GetMapping("/addlostuser")
    private String addLost(Model model,Authentication auth) {

        Lost lost = new Lost();
        model.addAttribute("lost", lost);
        model.addAttribute("categories", categoryRepository.findAll());


        return "lostform";
    }

    @PostMapping("/addlostuser")
    public String addLostInfo(@Valid @ModelAttribute("lost") Lost lost, Model model, BindingResult result, Authentication auth) {
        if (result.hasErrors()) {
            return "lostform";
        }
        AppUser appUser = userRepository.findAppUserByUsername(auth.getName());
        lost.addAppUser(appUser);

        lostRepository.save(lost);


        return "redirect:/listlost";
    }

    @GetMapping("/addlostadmin")
    private String addLostAdmin(Model model) {

        Lost lost = new Lost();
        model.addAttribute("lost", lost);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("registeredusers", userRepository.findAll());


        return "lostformadmin";
    }

    @PostMapping("/addlostadmin")
    public String addLostInfoAdmin(@Valid @ModelAttribute("lost") Lost lost, Model model, BindingResult result, @RequestParam("regusername") String username) {
        if (result.hasErrors()) {
            return "lostformadmin";
        }
        AppUser appUser = userRepository.findAppUserByUsername(username);
        lost.addAppUser(appUser);

        lostRepository.save(lost);


        AppRole role = roleRepository.findByAppUsers(userRepository.findAppUserByUsername(username));
        String rolename = role.getRoleName();

        if (rolename.equals("USER")) {
            return "redirect:/";
        }
        return "redirect:/listlost";
    }

    @GetMapping("/listlost")
    public String showLostInfo(Model model, Authentication auth) {

        AppUser appUser = userRepository.findAppUserByUsername(auth.getName());

        List<Lost> losts = lostRepository.findByAppUsers(appUser);

        Iterable<Lost> petCategory = lostRepository.findByCategoryNameIgnoreCaseAndAppUsers("Pet",appUser);
        Iterable<Lost> clothCategory = lostRepository.findByCategoryNameIgnoreCaseAndAppUsers("Cloth",appUser);
        Iterable<Lost> otherCategory = lostRepository.findByCategoryNameIgnoreCaseAndAppUsers("Other",appUser);


        model.addAttribute("petCategory", petCategory);
        model.addAttribute("clothCategory", clothCategory);
        model.addAttribute("otherCategory", otherCategory);


        model.addAttribute("newLosts", losts);
        model.addAttribute("appUser", appUser);
        return "listlostform";
    }

    @GetMapping("/editlost/{id}")
    public String editLost(@PathVariable("id") long id, Model model, Authentication auth) {
        Lost lost = lostRepository.findOne(id);
        model.addAttribute("lost", lost);
        model.addAttribute("categories", categoryRepository.findAll());


        return "lostform";


    }



    @GetMapping("/lost/{id}")
    public String changeLostStatus(Model model, @PathVariable("id") String lostId) {

        Lost lost = lostRepository.findOne(new Long(lostId));

        lost.setFound(!lost.isFound());
        lostRepository.save(lost);


        return "redirect:/";
    }


    @GetMapping("/default")
    public String defaultAfterLogin(Authentication auth){
        AppRole role = roleRepository.findByAppUsers(userRepository.findAppUserByUsername(auth.getName()));
        String rolename = role.getRoleName();

        if (rolename.equals("ADMIN")) {
            return "redirect:/";
        }
        return "redirect:/listlost";

    }

    @PostMapping("/search")
    public String search(Model model , HttpServletRequest request){

        String srch = request.getParameter("srch-term");


        Iterable<Lost> petCategory = lostRepository.findByCategoryNameIgnoreCaseContaining(srch);
        Iterable<Lost> clothCategory = lostRepository.findByCategoryNameIgnoreCaseContaining(srch);
        Iterable<Lost> otherCategory = lostRepository.findByCategoryNameIgnoreCaseContaining(srch);


        model.addAttribute("petCategory", petCategory);
        model.addAttribute("clothCategory", clothCategory);
        model.addAttribute("otherCategory", otherCategory);


     //   model.addAttribute("newLosts", lostRepository.findByCategoryNameIgnoreCaseContaining(srch));




        return "index";
    }





}

