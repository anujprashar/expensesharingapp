package com.example.expense.controller;

import com.example.expense.model.GroupEntity;
import com.example.expense.model.Membership;
import com.example.expense.model.User;
import com.example.expense.repo.UserRepo;
import com.example.expense.service.GroupService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
	@Autowired
    private final GroupService groupService;
	@Autowired
    private final UserRepo userRepo;

    @PostMapping("/createGroup")
    public ResponseEntity<GroupEntity> createGroup(@RequestParam String name) {
        return ResponseEntity.ok(groupService.createGroup(name));
    }

    @PostMapping("/{groupId}/addUser")
    public ResponseEntity<Membership> addUser(@PathVariable Long groupId, @RequestParam Long userId) {
        User u = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        GroupEntity g = new GroupEntity(); g.setId(groupId);
        return ResponseEntity.ok(groupService.addUserToGroup(u, g));
    }
}
