package com.ashokit.services;

import java.util.List;

import com.ashokit.dto.DashboardDto;
import com.ashokit.dto.EnquiryDto;

public interface EnquiryService {

	public DashboardDto getDashboardInfo(Integer counsellorId);

	public boolean upsertEnquiry(EnquiryDto enqDto, Integer counsellorId);

	public List<EnquiryDto> getEnquiries(Integer counsellorId);

	public List<EnquiryDto> filterEnqs(EnquiryDto filterDto, Integer counsellorId);

	public EnquiryDto getEnquiry(Integer enqId);

}
