package com.capgemini.managedepartmentservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.managedepartmentservice.model.DepartmentModel;
import com.capgemini.managedepartmentservice.service.DepartmentService;
import com.capgemini.managedepartmentservice.service.impl.MessageListener;

@RestController
@RequestMapping("/ManageDepartment")
@CrossOrigin
public class DepartmentController {

	Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentService;

	@GetMapping(value = "/HelloTest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> helloTest() {
		logger.info("Hello Test has been accessed");
		return ResponseEntity.ok("Hello World-1");
	}

	@PostMapping(value = "/adddepartment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DepartmentModel> addDepartment(@RequestBody DepartmentModel department) {
		logger.info("Add Department has been accessed");
		return ResponseEntity.ok(departmentService.addDepartmentService(department));
	}

	@PutMapping(value = "/updatedepartment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DepartmentModel> updateDepartment(@RequestBody DepartmentModel department) {
		logger.info("Update Department has been accessed");
		return ResponseEntity.ok(departmentService.updateDepartmentService(department));
	}

	@DeleteMapping(value = "/deletedepartment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteDepartment(@PathVariable int id) {
		logger.info("Delete Department has been accessed");
		return ResponseEntity.ok(departmentService.deleteDepartmentService(id));
	}

	@GetMapping(value = "/viewdepartment/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DepartmentModel> findDepartmentByName(@PathVariable String name) {
		logger.info("view department by name has been accessed");
		return ResponseEntity.ok(departmentService.viewDepartmentByName(name));

	}

	@GetMapping(value = "/viewall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DepartmentModel>> viewAll() {
		logger.info("View All Department has been accessed");
		return ResponseEntity.ok(departmentService.viewAll());

	}

	@GetMapping(value = "/notification", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getnNotification() {
		logger.info("Notifications has been accessed");
		return ResponseEntity.ok(MessageListener.getMessageList());

	}

}