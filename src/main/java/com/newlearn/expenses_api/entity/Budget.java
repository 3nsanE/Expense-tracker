package com.newlearn.expenses_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="budgets")
@Getter
@Setter
@ToString(exclude={"user","category"})
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,precision=12,scale=2)
    private BigDecimal monthlyLimit;

    @Column(nullable=false)
    private Integer month;

    @Column(nullable=false)
    private Integer year;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",nullable=false)
    private User user;
}
