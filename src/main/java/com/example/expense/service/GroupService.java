package com.example.expense.service;

import com.example.expense.model.GroupEntity;
import com.example.expense.model.Membership;
import com.example.expense.model.User;
import com.example.expense.repo.GroupRepo;
import com.example.expense.repo.MembershipRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepo groupRepo;
    private final MembershipRepo membershipRepo;

    public GroupEntity createGroup(String name) {
        GroupEntity g = GroupEntity.builder().name(name).build();
        return groupRepo.save(g);
    }

    @Transactional
    public Membership addUserToGroup(User user, GroupEntity group) {
        Membership m = Membership.builder().user(user).group(group).build();
        return membershipRepo.save(m);
    }

    public List<Membership> membersOf(GroupEntity group) {
        return membershipRepo.findByGroup(group);
    }
}
