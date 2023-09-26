package com.pringboot.blog.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "post", uniqueConstraints = {@UniqueConstraint(columnNames = "title")})

public class Post {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "title", nullable = false)
        private String title;

        @Column(name = "description", nullable = false)
        private String description;

        @Column(name = "content", nullable = false)
        private String content;

        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
        private Set<Comment> comment = new HashSet<>();




}
