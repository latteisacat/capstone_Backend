package com.example.capstone_backend.domain.fileserver;


import com.example.capstone_backend.domain.fileserver.entity.Shortform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortformRepository extends JpaRepository<Shortform, Long> {
}
