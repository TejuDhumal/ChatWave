package com.axis.userservice.service;

import java.util.UUID;

import com.axis.userservice.entity.Account;

public interface AccountService {

    Account save(Account account);

	void deleteById(int userId);
	
//	public void sendResetToken(String email);
////
//	public void resetPassword(String token, String newPassword);
	
	
	
	
}
