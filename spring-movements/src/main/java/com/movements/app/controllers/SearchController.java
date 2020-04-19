package com.movements.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;
import com.movements.app.models.service.ICompanyService;
import com.movements.app.models.service.IEmployeeService;

@Controller
@SessionAttributes("search")
public class SearchController {

	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private ICompanyService companyService;
	
	
	
	@GetMapping(value = "/upload-companies/{term}", produces= {"application/json"})
	public @ResponseBody List<Company> uploadCompanies(@PathVariable String term){
		return companyService.findCompanyByName(term);
	}
	
	
	@GetMapping(value = "/upload-employees/{idcompany}/{term}", produces= {"application/json"})
	public @ResponseBody List<Employee> uploadEmployeesWithCompany(@PathVariable(value="idcompany") Long companyId, @PathVariable String term){
		if (companyId == -1) {
			return employeeService.findEmployeeByName(term);
		} else {
			return employeeService.findEmployeeByCompanyIdAndName(companyId, term);}
	}
	

	
	
}
