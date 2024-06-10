package com.axis.userservice.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axis.userservice.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	 Account findByEmail(String email);
	 Account findByResetToken(String resetToken);
}
