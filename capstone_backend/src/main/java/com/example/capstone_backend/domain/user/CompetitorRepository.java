package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitorRepository extends JpaRepository<Competitor, Long> {
}
