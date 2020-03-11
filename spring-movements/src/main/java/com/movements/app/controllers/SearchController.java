package com.movements.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;
import com.movements.app.models.service.ICompanyService;
import com.movements.app.models.service.IEmployeeService;
import com.movements.app.models.service.ITaskService;

@Controller
@SessionAttributes("search")
public class SearchController {

	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private ICompanyService companyService;
	
	@Autowired
	private ITaskService taskService;
	
	
	@GetMapping(value = "/upload-companies/{term}", produces= {"application/json"})
	public @ResponseBody List<Company> uploadCompanies(@PathVariable String term){
		return companyService.findCompanyByName(term);
	}
	
	//@GetMapping(value = "/upload-employees/{idcompany}/{term}", produces= {"application/json"})
	//public @ResponseBody List<Employee> uploadEmployees(@PathVariable String term, @RequestBody Long idcompany){
	//	return employeeService.findEmployeeByCompanyIdAndName(idcompany, term);
	//}
	
	@GetMapping(value = "/upload-employees/{idcompany}/{term}", produces= {"application/json"})
	public @ResponseBody List<Employee> uploadEmployeesWithCompany(@PathVariable(value="idcompany") Long companyId, @PathVariable String term){
		return employeeService.findEmployeeByCompanyIdAndName(companyId, term);
	}
	
	@GetMapping(value = "/upload-employees//{term}", produces= {"application/json"})
	public @ResponseBody List<Employee> uploadEmployees(String term){
		return employeeService.findEmployeeByName(term);
	}
	
	
}
