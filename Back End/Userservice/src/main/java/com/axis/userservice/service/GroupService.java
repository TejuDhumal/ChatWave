package com.axis.userservice.service;

import com.axis.userservice.entity.Group;
import com.axis.userservice.entity.GroupUser;
import com.axis.userservice.entity.dto.GroupDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Group createGroup(Group group,int adminUserId);

    List<Group> getAllGroups();

    Group getGroupById(int id);

    List<Group> getGroupsByName(String groupName);

    void deleteById(int id);

    Optional<Group> editGroup(int id, GroupDto groupDto);

    GroupUser addMemberToGroup(int userId, GroupUser groupUser);

    List<GroupUser> getGroupMember(int id);

    GroupUser exitGroup(int groupId, int userId);

    List<GroupUser> getCurrentMembers(int id);
}
