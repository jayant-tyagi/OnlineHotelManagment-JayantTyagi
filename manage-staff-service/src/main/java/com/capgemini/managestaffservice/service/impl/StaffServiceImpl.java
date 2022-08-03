package com.capgemini.managestaffservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.managestaffservice.entity.Staff;
import com.capgemini.managestaffservice.mapper.AddressMapper;
import com.capgemini.managestaffservice.mapper.StaffMapper;
import com.capgemini.managestaffservice.model.AddressModel;
import com.capgemini.managestaffservice.model.StaffList;
import com.capgemini.managestaffservice.model.StaffModel;
import com.capgemini.managestaffservice.model.StaffReportModel;
import com.capgemini.managestaffservice.repository.StaffRepository;
import com.capgemini.managestaffservice.service.StaffService;

@Component
public class StaffServiceImpl implements StaffService {
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private StaffRepository staffRepository;
	
	public StaffModel addStaffService(StaffModel staff) {
		validateEntity(staff.getAddress());
		validateEntity(staff);
		Staff staffEntity= staffRepository.save(staffMapper.mapDtoToEntity(staff));
		return staffMapper.mapEntityToDto(staffEntity);
	}
	
	public StaffModel updateStaffService(StaffModel staff) {
		validateEntity(staff.getAddress());
		validateEntity(staff);
		Staff staffEntity =staffRepository.findById(staff.getCode());
		staffEntity.setFirstname(staff.getFirstname());
		staffEntity.setLastname(staff.getLastname());
		staffEntity.setSalary(staff.getSalary());
		staffEntity.setJoinedon(staff.getJoinedon());
		staffEntity.setAge(staff.getAge());
		staffEntity.setOccupation(staff.getOccupation());
		staffEntity.setEmail(staff.getEmail());
		staffEntity.setAddress(addressMapper.mapDtoToEntity(staff.getAddress()));
		staffEntity=staffRepository.save(staffEntity);
		return staffMapper.mapEntityToDto(staffEntity);
	}
	public String deleteStaffService(int id) {
		try {
			staffRepository.deleteById(id);
			return "Successfully deleted";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "can't delete";
	}
	public StaffModel viewStaffService(int id) {
		Staff staffEntity =staffRepository.findById(id);
		return staffMapper.mapEntityToDto(staffEntity);
	}
	public List<StaffModel> viewAllList(){
		List<Staff> staffList= staffRepository.findAll();
		List<StaffModel> modelList= new ArrayList<StaffModel>();
		for(Staff staff: staffList) {
			modelList.add(staffMapper.mapEntityToDto(staff));
		}
		return modelList;
	}
	
	public StaffList generateReport() {
		List<Staff> demoList = staffRepository.findAll();
		List<StaffReportModel> reportModelList = new ArrayList<StaffReportModel>();
		for(Staff staffins : demoList) {
			StaffReportModel reportModel = new StaffReportModel();
			reportModel.setCode(staffins.getCode());
			reportModel.setFirstname(staffins.getFirstname());
			reportModel.setLastname(staffins.getLastname());
			reportModel.setSalary(staffins.getSalary());
			reportModel.setJoinedon(staffins.getJoinedon());
			reportModelList.add(reportModel);
		}
		StaffList modelList = new StaffList();
		modelList.setStaffReportList(reportModelList);
		return modelList;
	}
	
	private void validateEntity(StaffModel staff) {
		List<String> errorMessage = new ArrayList<>();
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		Set<ConstraintViolation<StaffModel>> constraintViolations = validator.validate(staff);

		for (ConstraintViolation<StaffModel> constraintViolation : constraintViolations) {
			errorMessage.add(constraintViolation.getMessage());
		}

		if (errorMessage.size() > 0) {
			throw new ConstraintViolationException(constraintViolations);
		}

	}
	private void validateEntity(AddressModel address) {
		List<String> errorMessage = new ArrayList<>();
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		Set<ConstraintViolation<AddressModel>> constraintViolations = validator.validate(address);

		for (ConstraintViolation<AddressModel> constraintViolation : constraintViolations) {
			errorMessage.add(constraintViolation.getMessage());
		}

		if (errorMessage.size() > 0) {
			throw new ConstraintViolationException(constraintViolations);
		}

	}


}