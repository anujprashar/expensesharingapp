package com.example.expense.repo;

import com.example.expense.model.Membership;
import com.example.expense.model.User;
import com.example.expense.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepo extends JpaRepository<Membership, Long> {
    List<Membership> findByUser(User user);
    List<Membership> findByGroup(GroupEntity group);
    Optional<Membership> findByUserAndGroup(User user, GroupEntity group);
}
