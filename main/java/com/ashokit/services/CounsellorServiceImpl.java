package com.ashokit.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.dto.CounsellorDto;
import com.ashokit.entities.Counsellor;
import com.ashokit.repository.CounsellorRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	@Autowired
	CounsellorRepo counsellorRepo;

	@Override
	public CounsellorDto login(String email, String pwd) {
		Optional<Counsellor> optional = counsellorRepo.findByEmailAndPwd(email, pwd);
		if (optional.isPresent()) {
			CounsellorDto counsellorDto = new CounsellorDto();
			BeanUtils.copyProperties(optional.get(), counsellorDto);
			return counsellorDto;
		}

		return null;
	}

	@Override
	public boolean isEmailUniqe(String email) {
		return !counsellorRepo.existsByEmail(email);

	}

	@Override
	public boolean register(CounsellorDto counsellorDto) {
		Counsellor counsellor = new Counsellor();
		BeanUtils.copyProperties(counsellorDto, counsellor);
		Counsellor saved = counsellorRepo.save(counsellor);
		
		return saved.getCounsellorId() != null;

	}

}
