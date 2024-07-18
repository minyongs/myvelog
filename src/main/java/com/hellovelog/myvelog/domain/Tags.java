package com.hellovelog.myvelog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "tags",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<PostTag> postTags = new HashSet<>();


}
