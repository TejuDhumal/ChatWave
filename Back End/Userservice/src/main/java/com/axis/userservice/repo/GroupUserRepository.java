package com.axis.userservice.repo;

import com.axis.userservice.entity.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupUserRepository extends JpaRepository<GroupUser, Integer> {
    List<GroupUser> findBygroupId(int groupId);

    Optional<GroupUser> findByGroupIdAndUserId(int groupId, int userId);

    List<GroupUser> findByGroupIdAndUserGroupStatus(int groupId, String activated);

    void deleteByUserId(int userId);
}
