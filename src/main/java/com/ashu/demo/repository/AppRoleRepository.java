package com.ashu.demo.repository;

import com.ashu.demo.model.AppRole;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepository extends CrudRepository<AppRole,Long> {

    AppRole findAppRoleByRoleName(String roleName);
}
