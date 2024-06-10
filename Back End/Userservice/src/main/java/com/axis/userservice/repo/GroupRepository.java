package com.axis.userservice.repo;

import com.axis.userservice.entity.Group;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findBygroupName(String groupName);


    ObservationFilter getGroupById(int id);
}
