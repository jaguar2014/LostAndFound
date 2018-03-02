package com.ashu.demo.config;

import com.ashu.demo.model.AppRole;
import com.ashu.demo.model.AppUser;
import com.ashu.demo.model.Category;
import com.ashu.demo.model.Lost;
import com.ashu.demo.repository.AppRoleRepository;
import com.ashu.demo.repository.AppUserRepository;
import com.ashu.demo.repository.CategoryRepository;
import com.ashu.demo.repository.LostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    AppRoleRepository roleRepo;

    @Autowired
    AppUserRepository userRepository;

    @Autowired
    LostRepository lostRepository;

    @Autowired
    CategoryRepository categoryRepository;



    @Override
    public void run(String... strings) throws Exception {

        //Add all data that should be in the database at the beginning of the application
        AppRole role = new AppRole();
        role.setRoleName("USER");
        roleRepo.save(role);

        role = new AppRole();
        role.setRoleName("ADMIN");
        roleRepo.save(role);


        AppUser appUser = new AppUser();

        appUser.setUsername("Ashu");
        appUser.setPassword("pass");
        appUser.setFirstName("Ashenafi");
        appUser.addRole(roleRepo.findAppRoleByRoleName("USER"));
        userRepository.save(appUser);


        Lost lost = new Lost();
        lost.setTitle("pet lost");
        lost.setImageUrl("");
        lost.setDescription("she is lost and lost");

        Category category = new Category();
        category.setName("Pet");
        categoryRepository.save(category);

        lost.setCategory(category);
        lostRepository.save(lost);

        category.addLosts(lost);
        categoryRepository.save(category);
        lost.addAppUser(appUser);
        lostRepository.save(lost);

        appUser= new AppUser();
        appUser.setUsername("riri");
        appUser.setFirstName("rekik");
        appUser.setPassword("pass");
        appUser.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        userRepository.save(appUser);


        lost = new Lost();
        lost.setImageUrl("");
        lost.setTitle("Clothes lost");
        lost.setDescription("I lost my clothes");


        category = new Category();
        category.setName("Cloth");
        //repeat below code for other items
        categoryRepository.save(category);

        lost.setCategory(category);
        lostRepository.save(lost);

        category.addLosts(lost);
        categoryRepository.save(category);
        lost.addAppUser(appUser);
        lostRepository.save(lost);



        lost = new Lost();
        lost.setTitle("other lost");
        lost.setDescription("I lost my other stuff");
        lost.setImageUrl("");

        category = new Category();
        category.setName("Other");
        categoryRepository.save(category);


        lost.setCategory(category);
        lostRepository.save(lost);

        category.addLosts(lost);
        categoryRepository.save(category);
        lost.addAppUser(appUser);
        lostRepository.save(lost);

















//        appUser.addLost(lost);
//        userRepository.save(appUser);


//        appUser.addLost(lost);
//        userRepository.save(appUser);


//        appUser= new AppUser();
//        appUser.setUsername("riri");
//        appUser.setFirstName("rekik");
//        appUser.setPassword("pass");
//        appUser.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
//        userRepository.save(appUser);
//
//
//        lost = new Lost();
//        lost.setTitle("Clothes lost");
//        lost.setDescription("I lost my clothes");
//
//        category = new Category();
//        category.setName("Cloth");
//        categoryRepository.save(category);
//
//        lost.setCategory(category);
//        lostRepository.save(lost);
//        appUser.addLost(lost);
//        userRepository.save(appUser);
//
//
//
//        lost = new Lost();
//        lost.setTitle("other lost");
//        lost.setDescription("I lost my other stuff");
//
//        category = new Category();
//        category.setName("Other");
//        categoryRepository.save(category);
//
//
//        lost.setCategory(category);
//        lostRepository.save(lost);
//        appUser.addLost(lost);
//        userRepository.save(appUser);






    }
}