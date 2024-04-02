package com.example.capstone_backend.domain.user.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@Table(name = "competitor")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Competitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from")
    private UserInfo from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to")
    private UserInfo to;



}
