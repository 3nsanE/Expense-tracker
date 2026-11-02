package com.newlearn.expenses_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@ToString(exclude={"categories","expenses","budgets"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 30)
    private String name;

    @Column(nullable = false,length = 130,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "user")
    @Builder.Default
    private List<Category> categories=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "user")
    @Builder.Default
    private List<Expense> expenses=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "user")
    @Builder.Default
    private List<Budget> budgetss=new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
