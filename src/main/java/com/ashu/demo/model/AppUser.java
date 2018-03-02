package com.ashu.demo.model;




import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class AppUser {

  @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

   @NotEmpty
   @Size(min=3)
    private String username;

    @NotEmpty
    @Size(min=3)
    private String firstName;

    @NotEmpty
    @Size(min=3)
    public String password;

    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppRole> appRoles;

   @ManyToMany(mappedBy = "appUsers")
    private List<Lost> losts;

    public AppUser(){

        appRoles = new HashSet<>();
        losts = new ArrayList<>();

    }
    public List<Lost> getLosts() {
        return losts;
    }

    public void setLosts(List<Lost> losts) {
        this.losts = losts;
    }

    public void addLost(Lost L){
        losts.add(L);
    }





    public void addRole(AppRole role) {
        appRoles.add(role);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<AppRole> getAppRoles() {
        return appRoles;
    }

    public void setAppRoles(Set<AppRole> appRoles) {
        this.appRoles = appRoles;
    }
}
