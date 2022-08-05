package com.capgemini.managedepartmentservice;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.*;
import com.capgemini.managedepartmentservice.entity.Department;
import com.capgemini.managedepartmentservice.mapper.DepartmentMapper;
import com.capgemini.managedepartmentservice.model.DepartmentModel;
import com.capgemini.managedepartmentservice.repository.DepartmentRepository;

@SpringBootTest
class ManageDepartmentServiceApplicationTests {
	@Autowired
	private Validator validator;

	@Autowired
	DepartmentMapper mapper;

	@Autowired
	DepartmentRepository repository;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@Order(1)
	public void testAdd() {
		Department department = new Department();
		department.setId(101);
		department.setName("Harsh");
		department.setHodName("Vishu");
		department.setHodPhoneNo("9536133303");
		department.setSizeOfDepartment(20);
		repository.save(department);
		assertNotNull(repository.findById(101));
		department.setId(102);
		department.setName("Harsh");
		department.setHodName("Vishu");
		department.setHodPhoneNo("9536133303");
		department.setSizeOfDepartment(20);
		repository.save(department);
		assertNotNull(repository.findById(101));
	}

	// if any entity is filled with wrong data
	@Test
	public void testAdd2() {
		DepartmentModel department = new DepartmentModel();
		department.setId(103);
		department.setName("Divuyansh");
		department.setHodName("Vishu");
		department.setSizeOfDepartment(20);
		repository.save(mapper.mapDtoToEntity(department));

		assertNull(repository.findById(103).getHodPhoneNo());
	}

	@Test
// when a validation is missed
	public void testAdd3() {
		DepartmentModel department = new DepartmentModel();
		department.setId(104);
		department.setName("Divuyansh");
		department.setHodName("Vishu");
		department.setHodPhoneNo("9536133");
		department.setSizeOfDepartment(20);

		Set<ConstraintViolation<DepartmentModel>> violations = validator.validate(department);
		assertFalse(violations.isEmpty());
	}

	@Test
	// when a validation is missed
	public void testAdd4() {
		DepartmentModel department = new DepartmentModel();
		department.setId(105);
		department.setName("");
		department.setHodName("Vishu");
		department.setHodPhoneNo("9536133303");
		department.setSizeOfDepartment(20);

		Set<ConstraintViolation<DepartmentModel>> violations = validator.validate(department);
		assertFalse(violations.isEmpty());
	}

	@Test
	// when a validation is missed
	public void testAdd5() {
		DepartmentModel department = new DepartmentModel();
		department.setId(106);
		department.setName("Divuyansh");
		department.setHodName("");
		department.setHodPhoneNo("9536133303");
		department.setSizeOfDepartment(20);

		Set<ConstraintViolation<DepartmentModel>> violations = validator.validate(department);
		assertFalse(violations.isEmpty());
	}

	@SuppressWarnings("unused")
	@Test
	// when a validation is missed
	public void testAdd6() {
		DepartmentModel department = new DepartmentModel();
		department.setId(106);
		department.setName("Divuyansh");
		department.setHodName("Vishu");
		department.setHodPhoneNo("9536133303");
		department.setSizeOfDepartment(20);
		Department entity = repository.save(mapper.mapDtoToEntity(department));
		assertNotNull(repository.findById(106));

	}

	@SuppressWarnings("unused")
	@Test
	@Order(2)
	public void testUpdate() {
		DepartmentModel department = mapper.mapEntityToDto(repository.findById(106));
		department.setSizeOfDepartment(20);
		Department entity = repository.save(mapper.mapDtoToEntity(department));
		assertNotEquals(19, repository.findById(106).getSizeOfDepartment());
	}

	@Test
	// when a validation is missed
	public void testUpdate2() {
		DepartmentModel department = mapper.mapEntityToDto(repository.findById(102));
		department.setHodPhoneNo("953613");

		Set<ConstraintViolation<DepartmentModel>> violations = validator.validate(department);
		assertFalse(violations.isEmpty());
	}

	@Test
	@Order(3)
	public void testView() {

		assertEquals("Harsh", repository.findById(101).getName());
	}

	@Test
	@Order(4)
	public void testViewAll() {
		List<Department> list = repository.findAll();
		assertThat(list).size().isGreaterThan(0);
	}

	@Test
	@Order(8)
	public void testDelete() {
		repository.deleteById(101);
		assertThat(repository.existsById(101)).isFalse();
	}

	@Test

	public void testDelete2() {
		repository.deleteById(102);
		assertThat(repository.existsById(101)).isTrue();
	}

}