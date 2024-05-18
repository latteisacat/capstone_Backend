package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {


    @Query("select e from Exercise e "
            + " join e.userId"
            + " where e.userId = :user")
    List<Exercise> findAllByUser(@Param("user") UserInfo user);


    @Query(
            " select count(*) from Exercise e" +
            " join e.userId " +
            " where e.exerciseName = :exerciseName" +
            " and e.record > :record and e.userId.sex = :sex"
    )
    Long getBetterExerciseUserCount(@Param("exerciseName") String exerciseName, @Param("record") Double record, @Param("sex") String sex);

    @Query(
            "select count(*) from Exercise e" +
            " where e.exerciseName = :exerciseName and e.userId.sex = :sex"
    )
    Long getExerciseUserCount( @Param("exerciseName") String exerciseName, @Param("sex") String sex);

    @Query(
            "select avg(e.record) from Exercise e" +
            " where e.exerciseName = :exerciseName and e.userId.sex = :sex")
    Double getAverageRecord( @Param("exerciseName") String exerciseName, @Param("sex") String sex);
}
