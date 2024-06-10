package com.axis.ChatWave.controllers;

import com.axis.ChatWave.dtos.GroupCreationRequest;
import com.axis.ChatWave.dtos.GroupUserRequest;
import com.axis.ChatWave.models.Group;
import com.axis.ChatWave.models.GroupUsers;
import com.axis.ChatWave.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody GroupCreationRequest request) {
        Group createdGroup = groupService.createGroup(request.getUserId(), request.getName());
        return ResponseEntity.ok(createdGroup);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addUser")
    public ResponseEntity<GroupUsers> addUserToGroup(@RequestBody GroupUserRequest request) {
        GroupUsers groupUser = groupService.addUserToGroup(request.getUserId(), request.getGroupId());
        return ResponseEntity.ok(groupUser);
    }

    @DeleteMapping("/removeUser")
    public ResponseEntity<Void> removeUserFromGroup(@RequestBody GroupUserRequest request) {
        groupService.removeUserFromGroup(request.getUserId(), request.getGroupId());
        return ResponseEntity.noContent().build();
    }
}
