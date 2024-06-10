package com.axis.userservice.controller;

import com.axis.userservice.entity.Group;
import com.axis.userservice.entity.GroupUser;
import com.axis.userservice.entity.dto.GroupDto;
import com.axis.userservice.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    GroupService groupService;

    @PostMapping("/{adminUserId}")
    public ResponseEntity createGroup(@Valid @RequestBody Group group,@PathVariable int adminUserId) throws URISyntaxException {
        Group result = groupService.createGroup(group,adminUserId);
        return ResponseEntity.created(new URI("/api/users/" + result.getId())).body(group);
    }
    @PostMapping("/{userId}/members")
    public GroupUser addMemberToGroup(@PathVariable Integer userId, @RequestBody GroupUser groupUser) {
        return groupService.addMemberToGroup(userId, groupUser);
    }

    @GetMapping("/")
    public ResponseEntity getAllGroups() {
        List<Group> result = groupService.getAllGroups();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getAllGroupById(@PathVariable int id) {
        Group group = groupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/name")
    public ResponseEntity getGroupByName(@RequestParam String name) {
        List<Group> groups = groupService.getGroupsByName(name);
        return ResponseEntity.ok(groups);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity editGroup(@PathVariable int id, @RequestBody GroupDto groupDto){
        Optional<Group> group = groupService.editGroup(id,groupDto);
        return ResponseEntity.ok(group);
}

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGroup(@PathVariable int id){
        Group group = groupService.getGroupById(id);
                      groupService.deleteById(id);
        return ResponseEntity.ok("Group deleted successfully"+group);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity getAllGroupUsers(@PathVariable int id) {
        List<GroupUser> groupUserList = groupService.getGroupMember(id);
        return ResponseEntity.ok(groupUserList);
    }

    @PostMapping("/exit")
    public ResponseEntity ExitGroup(@RequestParam int groupId, int userId ) {
        GroupUser groupUser1 = groupService.exitGroup(groupId, userId);
        return ResponseEntity.ok(groupUser1);
    }

    @GetMapping("/currentusers/{id}")
    public ResponseEntity getCurrentGroupUsers(@PathVariable int id) {
        List<GroupUser> groupUserList = groupService.getCurrentMembers(id);
        return ResponseEntity.ok(groupUserList);
    }

}
