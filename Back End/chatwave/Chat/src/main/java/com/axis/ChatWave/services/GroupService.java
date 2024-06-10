package com.axis.ChatWave.services;

import com.axis.ChatWave.models.Group;
import com.axis.ChatWave.models.GroupUsers;

import java.util.List;

public interface GroupService {
    List<Group> getAllGroups();
    Group getGroupById(Long id);
    Group createGroup(Long userId, String name);
    Group updateGroup(Long id, Group group);
    void deleteGroup(Long id);
    GroupUsers addUserToGroup(Long userId, Long groupId);
    void removeUserFromGroup(Long userId, Long groupId);
}


