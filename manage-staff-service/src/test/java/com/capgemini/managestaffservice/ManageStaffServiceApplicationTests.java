package com.capgemini.managestaffservice;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.managestaffservice.entity.Address;
import com.capgemini.managestaffservice.entity.Staff;
import com.capgemini.managestaffservice.repository.StaffRepository;

@SpringBootTest
class ManageStaffServiceApplicationTests {

	@Autowired
	StaffRepository repo;
	
	@SuppressWarnings("deprecation")
	@Test
	@Order(1)
	public void addTest() {
		Date d1 = new Date(2000, 11, 21);
		Staff staff = new Staff();
		Address address = new Address();
		address.setId(1);
		address.setHouseNo("H.no 20");
        address.setStreetName("Kawad road");
        address.setCity("Kanth");
        address.setState("U.P");
        address.setPincode(243601);
        address.setCountry("India");
		staff.setCode(101);
		staff.setFirstname("Vishu");
		staff.setLastname("Gupta");
		staff.setSalary(15000);
		staff.setJoinedon(d1);
		staff.setEmail("vishwade@gmail.com");
		staff.setAge(21);
		staff.setOccupation("CHIJ");
		staff.setAddress(address);
		repo.save(staff);
		assertNotNull(repo.findById(101));
				
		Date d2 = new Date(2000, 11, 21);
		
		
		address.setId(2);
		address.setHouseNo("H.no 20");
        address.setStreetName("Kawad road");
        address.setCity("Kanth");
        address.setState("U.P");
        address.setPincode(243601);
        address.setCountry("India");
		staff.setCode(102);
		staff.setFirstname("Vishu");
		staff.setLastname("Gupta");
		staff.setSalary(15000);
		staff.setJoinedon(d2);
		staff.setEmail("vishwade@gmail.com");
		staff.setAge(21);
		staff.setOccupation("CHIJ");
		staff.setAddress(address);
		repo.save(staff);
		assertNotNull(repo.findById(102));
	}
	
	@Test
	@Order(2)
	public void updateTest()
	{
		
      Staff staff = repo.findById(101);
      Address address = new Address();
      staff.setAge(22);
      staff.setAddress(address);
      address.setCity("Darbhanga");
      repo.save(staff);
      assertNotEquals(21, repo.findById(101).getAge());
	assertNotEquals("Kanth", repo.findById(101).getAddress().getCity());
	}
	
	@Test
	@Order(3)
	public void viewTest()
	{
		Staff staff = repo.findById(101);
		assertEquals("CHIJ", staff.getOccupation());
	}
	
	@Test
	@Order(4)
	public void viewAllTest()
	{
		List<Staff> list = repo.findAll();
		assertThat(list).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void deleteTest()
	{
		repo.deleteById(102);
		assertThat(repo.existsById(102)).isFalse();
	}
}