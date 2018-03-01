package com.ashu.demo.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Lost> losts;

    public void addLosts(Lost lost){
        losts.add(lost);
    }

    public Set<Lost> getLosts() {
        return losts;
    }

    public void setLosts(Set<Lost> losts) {
        this.losts = losts;
    }

    public Category(){
        losts = new HashSet<>();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

