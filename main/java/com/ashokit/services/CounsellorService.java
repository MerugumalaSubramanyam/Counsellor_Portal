package com.ashokit.services;

import com.ashokit.dto.CounsellorDto;

public interface CounsellorService {

	public CounsellorDto login(String email, String pwd);

	public boolean isEmailUniqe(String email);

	public boolean register(CounsellorDto counsellorDto);
}
