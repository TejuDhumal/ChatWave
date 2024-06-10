package com.axis.ChatWave.services;

import com.axis.ChatWave.models.Group;
import com.axis.ChatWave.models.GroupUsers;
import com.axis.ChatWave.repositories.GroupRepository;
import com.axis.ChatWave.repositories.GroupUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;
    

    @Autowired
    private GroupUsersRepository groupUsersRepository;

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
    }

    @Override
    public Group createGroup(Long userId, String name) {
        Group group = new Group();
        group.setUserId(userId);
        group.setName(name);
        group.setCreatedAt(LocalDateTime.now());
        group.setUpdatedAt(LocalDateTime.now());
        group.setCreatedBy("SYSTEM");  // Set appropriate value or parameter
        group.setUpdatedBy("SYSTEM");  // Set appropriate value or parameter
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Long id, Group group) {
        Group existingGroup = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
        existingGroup.setName(group.getName());
        existingGroup.setUserId(group.getUserId());
        existingGroup.setUpdatedAt(LocalDateTime.now());
        existingGroup.setCreatedBy(group.getCreatedBy());
        existingGroup.setUpdatedBy(group.getUpdatedBy());
        return groupRepository.save(existingGroup);
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
    
    @Override
    public GroupUsers addUserToGroup(Long userId, Long groupId) {
        GroupUsers groupUser = new GroupUsers();
        groupUser.setUserId(userId);
        groupUser.setGroupId(groupId);
        groupUser.setCreatedAt(LocalDateTime.now());
        groupUser.setUpdatedAt(LocalDateTime.now());
        groupUser.setCreatedBy("SYSTEM");  // Set appropriate value or parameter
        groupUser.setUpdatedBy("SYSTEM");  // Set appropriate value or parameter
        return groupUsersRepository.save(groupUser);
    }

    @Override
    public void removeUserFromGroup(Long userId, Long groupId) {
        GroupUsers groupUser = groupUsersRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("GroupUser relationship not found"));
        groupUsersRepository.delete(groupUser);
    }
}

