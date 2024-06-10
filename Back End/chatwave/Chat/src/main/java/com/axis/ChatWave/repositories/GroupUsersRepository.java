package com.axis.ChatWave.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axis.ChatWave.models.Group;
import com.axis.ChatWave.models.GroupUsers;

@Repository
public interface GroupUsersRepository extends JpaRepository<GroupUsers, Long> {

	Optional<GroupUsers> findByUserIdAndGroupId(Long userId, Long groupId);

}
