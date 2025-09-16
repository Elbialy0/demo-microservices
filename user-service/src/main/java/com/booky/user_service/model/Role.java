package com.booky.user_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    private String roleName;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;










}
