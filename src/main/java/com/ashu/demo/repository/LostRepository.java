package com.ashu.demo.repository;

import com.ashu.demo.model.AppUser;
import com.ashu.demo.model.Lost;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LostRepository extends CrudRepository<Lost, Long> {

    List<Lost> findLostsByAppUsersIn(List<AppUser> appUsers);

    List<Lost> findByAppUsers(AppUser appUser);

    Iterable<Lost> findByFoundTrue();

    Iterable<Lost> findByFoundFalse();

    Iterable<Lost> findByCategoryNameIgnoreCaseContaining(String category);

    Iterable<Lost> findByCategoryNameIgnoreCase(String category);

    Iterable<Lost> findByCategoryNameIgnoreCaseAndAppUsers(String category, AppUser appUser);



}
