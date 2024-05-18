package com.example.capstone_backend.domain.user.entity;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.management.ThreadInfo;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(
        name = "user_info",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        }
)
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
    private String userName;

    @NotNull
    @Column
    private String userPassword;

    @Column
    private String userProfile;

    @Column
    private Double height;

    @Column
    private Double weight;

    @Column
    private Double muscleMass;

    @Column
    private Double fatMass;

    @Column
    private Double bodyScore;

    @Column(name="bmi")
    private Double BMI;

    @Column
    private String sex;

    @Column(name="is_dummy")
    @Builder.Default
    @NotNull
    private Boolean isDummy = false;

    @CreatedDate
    @Column
    private LocalDate createdAt;

    @LastModifiedDate
    @Column
    private LocalDate updatedAt;
}
