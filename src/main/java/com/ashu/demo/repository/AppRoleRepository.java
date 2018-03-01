package com.ashu.demo.repository;

import com.ashu.demo.model.AppRole;
import com.ashu.demo.model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepository extends CrudRepository<AppRole,Long> {

    AppRole findAppRoleByRoleName(String roleName);

    AppRole findByAppUsers(AppUser appUser);
}
