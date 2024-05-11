package com.example.capstone_backend.domain.fileserver;


import com.example.capstone_backend.domain.fileserver.entity.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortformRepository extends JpaRepository<Contents, Long> {
}
