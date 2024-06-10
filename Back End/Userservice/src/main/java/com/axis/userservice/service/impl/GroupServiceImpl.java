package com.axis.userservice.service.impl;

import com.axis.userservice.entity.Group;
import com.axis.userservice.entity.GroupUser;
import com.axis.userservice.entity.dto.GroupDto;
import com.axis.userservice.exception.GlobalExceptionHandler;
import com.axis.userservice.repo.GroupRepository;
import com.axis.userservice.repo.GroupUserRepository;
import com.axis.userservice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupUserRepository groupUserRepository;


    @Override
    public Group createGroup(Group group,int adminUserId) {
        // Set the admin user ID for the group
        group.setGroupAdminUserId(adminUserId);
        Group savedGroup = groupRepository.save(group);

        // Add the admin user to the group with the role 'admin'
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(savedGroup.getId());
        groupUser.setUserId(adminUserId);
        groupUser.setUserRole("admin");
        groupUserRepository.save(groupUser);

        return savedGroup;
    }

    @Override
    public GroupUser addMemberToGroup(int userId, GroupUser groupUser) {
        Group group = groupRepository.findById(groupUser.getGroupId()).orElse(null);
        if (group == null) {
            throw new GlobalExceptionHandler.BadRequestException("Group with id " + groupUser.getGroupId() + " is not available");
        } else if (group.getGroupAdminUserId() != userId) {
            throw new GlobalExceptionHandler.BadRequestException("You are not the admin of this group");
        }

        Optional<GroupUser> existingGroupUser = groupUserRepository.findByGroupIdAndUserId(groupUser.getGroupId(), groupUser.getUserId());
        if (existingGroupUser.isEmpty()) {
            groupUser.setUserGroupStatus("activated");
            groupUser.setUserRole("member");
            return groupUserRepository.save(groupUser);
        } else {
            // If member already exists
            GroupUser groupUserToUpdate = existingGroupUser.get();
            groupUserToUpdate.setUserGroupStatus("activated");
            return groupUserRepository.save(groupUserToUpdate);
        }
    }


    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group getGroupById(int id) {
        Group group = groupRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"group with this id not found"));
        return group;
    }

    @Override
    public List<Group> getGroupsByName(String groupName) {
        List<Group> groupList = groupRepository.findBygroupName(groupName);
        if(groupList.isEmpty()){
          throw  new GlobalExceptionHandler.BadRequestException("Group with  name " + groupName + "  is not available");
        }
        else {
            return groupList;
        }
    }

    @Override
    public void deleteById(int id) {
        Group group = this.getGroupById(id);
        if(group == null){
            throw  new GlobalExceptionHandler.BadRequestException("Group with  id " + group.getId() + "  is not available");
        }
         groupRepository.deleteById(id);
    }

    @Override
    public Optional<Group> editGroup(int id, GroupDto groupDto) {
        Group group = this.getGroupById(id);
        if(group==null){
            throw  new GlobalExceptionHandler.BadRequestException("Group with  id " + group.getId() + "  is not available");
        }
        return groupRepository
                .findById(id)
                .map(existingGroup -> {
                    if (groupDto.getGroupName() != null) {
                        existingGroup.setGroupName(groupDto.getGroupName());
                    }
                    if (groupDto.getDesc() != null) {
                        existingGroup.setGroup_desc(groupDto.getDesc());
                    }

                    return existingGroup;
                })
                .map(groupRepository::save);
    }

    @Override
    public List<GroupUser> getGroupMember(int id) {
        List<GroupUser> list = groupUserRepository.findBygroupId(id);
        if(list.isEmpty()){
            throw new GlobalExceptionHandler.BadRequestException("group not exist with group id " +id);
        }
        return list;

    }

    @Override
    public GroupUser exitGroup(int groupId, int userId) {
        Optional<GroupUser> optionalGroupUser = groupUserRepository.findByGroupIdAndUserId(groupId, userId);

        if (optionalGroupUser.isPresent()) {
            GroupUser groupUser = optionalGroupUser.get();
            groupUser.setUserGroupStatus("deactivated"); // change user status
           return groupUserRepository.save(groupUser);

        } else {
            throw new RuntimeException("GroupUser not found");
        }
    }

        @Override
        public List<GroupUser> getCurrentMembers(int groupId) {
            List<GroupUser> list =  groupUserRepository.findByGroupIdAndUserGroupStatus(groupId, "activated");
            if(list.isEmpty()){
                throw new GlobalExceptionHandler.BadRequestException("Not active members with group id " +groupId);
            }
            return list;
        }
}
