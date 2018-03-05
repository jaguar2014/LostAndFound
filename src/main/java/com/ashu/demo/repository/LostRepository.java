package com.ashu.demo.repository;

import com.ashu.demo.model.AppUser;
import com.ashu.demo.model.Lost;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LostRepository extends CrudRepository<Lost, Long> {

    List<Lost> findLostsByAppUsersIn(List<AppUser> appUsers);

    List<Lost> findByAppUsers(AppUser appUser);

    Iterable<Lost> findByFoundTrueAndAppUsers(AppUser appUser);

    Iterable<Lost> findByFoundFalse();

    List<Lost> findByCategoryNameIgnoreCaseContaining(String category);

    List<Lost> findByCategoryNameIgnoreCase(String category);

    List<Lost> findByCategoryNameIgnoreCaseAndAppUsers(String category, AppUser appUser);


    List<Lost> findByFoundTrue();
}
