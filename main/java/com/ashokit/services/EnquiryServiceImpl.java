package com.ashokit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.dto.DashboardDto;
import com.ashokit.dto.EnquiryDto;
import com.ashokit.entities.Counsellor;
import com.ashokit.entities.Enquiry;
import com.ashokit.repository.CounsellorRepo;
import com.ashokit.repository.EnquiryRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	EnquiryRepo enquiryRepo;

	@Autowired
	CounsellorRepo counsellorRepo;

	@Override
	public DashboardDto getDashboardInfo(Integer counsellorId) {
		List<Enquiry> enquiries = enquiryRepo.findAll().stream()
				.filter(e -> e.getCounsellorId().getCounsellorId().equals(counsellorId)).collect(Collectors.toList());

		DashboardDto dashboardDto = new DashboardDto();

		dashboardDto.setTotalEnqs(enquiries.size());

		dashboardDto
				.setOpenEnqs((int) enquiries.stream().filter(e -> "Open".equalsIgnoreCase(e.getEnqStatus())).count());

		dashboardDto.setEnrolledEnqs(
				(int) enquiries.stream().filter(e -> "Enrolled".equalsIgnoreCase(e.getEnqStatus())).count());

		dashboardDto
				.setLostEnqs((int) enquiries.stream().filter(e -> "Lost".equalsIgnoreCase(e.getEnqStatus())).count());

		return dashboardDto;

	}

	@Override
	public boolean upsertEnquiry(EnquiryDto enqDto, Integer counsellorId) {
		Enquiry enquiry = new Enquiry();
		BeanUtils.copyProperties(enqDto, enquiry);

		Optional<Counsellor> counsellorOpt = counsellorRepo.findById(counsellorId);
		if (counsellorOpt.isPresent()) {
			enquiry.setCounsellorId(counsellorOpt.get());
			enquiryRepo.save(enquiry);
			return true;
		}

		return false;
	}

	@Override
	public List<EnquiryDto> getEnquiries(Integer counsellorId) {
		List<Enquiry> enquires = enquiryRepo.findAll().stream()
				.filter(e -> e.getCounsellorId().getCounsellorId().equals(counsellorId)).collect(Collectors.toList());

		List<EnquiryDto> result = new ArrayList<>();
		for (Enquiry e : enquires) {
			EnquiryDto dto = new EnquiryDto();
			BeanUtils.copyProperties(e, dto);
			result.add(dto);
		}
		return result;

	}

	@Override
	public List<EnquiryDto> filterEnqs(EnquiryDto filterDto, Integer counsellorId) {
		return getEnquiries(counsellorId).stream()
				.filter(e -> (filterDto.getCourseName() == null || filterDto.getCourseName().isBlank()
						|| filterDto.getCourseName().equalsIgnoreCase(e.getCourseName()))
						&& (filterDto.getClassMode() == null || filterDto.getClassMode().isBlank()
								|| filterDto.getClassMode().equalsIgnoreCase(e.getClassMode()))
						&& (filterDto.getEnqStatus() == null || filterDto.getEnqStatus().isBlank()
								|| filterDto.getEnqStatus().equalsIgnoreCase(e.getEnqStatus())))
				.collect(Collectors.toList());
	}

	@Override
	public EnquiryDto getEnquiry(Integer enqId) {
		Optional<Enquiry> optional = enquiryRepo.findById(enqId);
		if (optional.isPresent()) {
			EnquiryDto dto = new EnquiryDto();
			BeanUtils.copyProperties(optional.get(), dto);
			return dto;

		}
		return null;
	}

}
