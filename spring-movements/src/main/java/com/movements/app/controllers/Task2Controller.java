package com.movements.app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;
import com.movements.app.models.entity.Information;
import com.movements.app.models.entity.Task;
import com.movements.app.models.service.ICompanyService;
import com.movements.app.models.service.IEmployeeService;
import com.movements.app.models.service.ITaskService;

@Controller
@SessionAttributes("task2")
public class Task2Controller {

	@Autowired
	private ICompanyService companyService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private ITaskService taskService;
	
	
	@GetMapping("/task2/form/{companyId}/{employeeId}")
	public String create(@PathVariable(value="companyId") Long companyId, 
			@PathVariable(value="employeeId") Long employeeId, 
			Map<String, Object> model, 
			RedirectAttributes flash) {
		
		Company company = companyService.findOne(companyId);
		if (company == null) {
			flash.addFlashAttribute("error", "L'empresa no existeix a la BdD");
			return "redirect:/list";
		}
		
		Employee employee  = employeeService.findOne(employeeId);
		if (employee == null) {
			flash.addFlashAttribute("error", "El treballador no existeix a la BdD");
			return "redirect:/list";
		}
		
		Task task = new Task();
		task.setCompany(company);
		task.setEmployee(employee);
		
		model.put("task", task);
		model.put("title", "Crear tasca");
			
		
		return "task2/form";
	}

@GetMapping(value="/task2/upload-informations/{term}", produces= {"application/json"})
public @ResponseBody List<Information> uploadInformations(@PathVariable String term){
	return taskService.findByDescription(term);
	
}


}
