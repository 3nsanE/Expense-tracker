package com.newlearn.expenses_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name", "user_id"
        })
})
@Getter
@Setter
@ToString(exclude = {"user","expenses","budgets"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 30)
    private String name;

    @Column(length = 200)
    private  String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Expense> expenses=new ArrayList<>();


}
