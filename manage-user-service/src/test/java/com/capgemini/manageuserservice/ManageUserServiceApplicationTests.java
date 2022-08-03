package com.capgemini.manageuserservice;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.manageuserservice.entity.User;
import com.capgemini.manageuserservice.repository.UserRepository;

@SpringBootTest
class ManageUserServiceApplicationTests {

	@Autowired
	UserRepository repo;
	
	@Test
	public void addTest() {
		User user = new User();
		user.setUsername("vishu@capegemini.com");
		user.setName("Vishu");
		user.setPassword("Vishu@123");
		user.setRole("Admin");
		repo.save(user);
		assertNotNull(repo.findByUsername("vishu@capegemini.com"));	
	   	 
	}
	
	@Test
	public void updateTest()
	{
		User user = repo.findByUsername("vishu@capegemini.com");
		user.setRole("Manager");
		repo.save(user);
		assertNotEquals("Admin",repo.findByUsername("vishu@capegemini.com").getRole());
	}

	@Test
	public void viewTest()
	{
		assertEquals("Vishu",repo.getUserByUsername("vishu@capegemini.com").getName());
	}
	
	@Test
	public void deleteTest()
	{
		repo.deleteById("vishu@capegemini.com");
		assertThat(repo.existsById("vishu@capegemini.com")).isFalse();
		
	}
}