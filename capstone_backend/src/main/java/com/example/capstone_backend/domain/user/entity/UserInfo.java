package com.example.capstone_backend.domain.user.entity;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "user_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 30)
    private String email;

    @NotNull
    @Column(length = 30)
    private String name;

    @NotNull
    @Column
    private String password;

    @Column
    private String profile;

    @Column
    private Float height;

    @Column
    private Float weight;

    @Column
    private Float muscleMass;

    @Column
    private Float fatMass;

    @CreatedDate
    @Column
    private LocalDate createdAt;

    @LastModifiedDate
    @Column
    private LocalDate updatedAt;
}
