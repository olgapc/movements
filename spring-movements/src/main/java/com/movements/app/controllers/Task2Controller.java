package com.movements.app.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;
import com.movements.app.models.entity.Information;
import com.movements.app.models.entity.Task;
import com.movements.app.models.entity.TaskInformation;
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

	
	@GetMapping("/task2/form/")
	public String create(
			Map<String,Object> model, RedirectAttributes flash ) {
		
		
		Task task = new Task();
		task.setCompany(null);
		task.setEmployee(null);
		model.put("company", task.getCompany());
		model.put("employee", task.getEmployee());
		
		model.put("task", task);
		model.put("title", "Crear tasca");

		return "/task2/form";
		
	}
	
	@GetMapping("/task2/form/{companyId}")
	public String create(@PathVariable(value = "companyId") Long companyId,
			Map<String,Object> model, RedirectAttributes flash ) {
		
		Company company = companyService.findOne(companyId);
		
		if (company == null) {
			flash.addFlashAttribute("error", "L'empresa no existeix a la BdD");
			return "redirect:/task/list";
		}
		
		Task task = new Task();
		task.setCompany(company);
		
		model.put("task", task);
		model.put("title", "Crear tasca");

		return "/task2/form";
		
	}
	
	
	
	@GetMapping("/task2/form/{companyId}/{employeeId}")
	public String create(@PathVariable(value = "companyId") Long companyId,
			@PathVariable(value = "employeeId") Long employeeId, Map<String, Object> model, RedirectAttributes flash) {

		Company company = companyService.findOne(companyId);
		if (company == null) {
			flash.addFlashAttribute("error", "L'empresa no existeix a la BdD");
			return "redirect:/task/list";
		}

		Employee employee = employeeService.findOne(employeeId);
		if (employee == null) {
			flash.addFlashAttribute("error", "El treballador no existeix a la BdD");
			return "redirect:/task/list";
		}

		Task task = new Task();
		task.setCompany(company);
		task.setEmployee(employee);

		model.put("task", task);
		model.put("title", "Crear tasca");

		return "/task2/form";
	}

	@GetMapping(value = "/task2/upload-informations/{term}", produces = { "application/json" })
	public @ResponseBody List<Information> uploadInformations(@PathVariable String term) {
		return taskService.findInformationByDescription(term);
	}

	@PostMapping("/task2/form")
	public String save(Task task, 
			@RequestParam(name = "company_id", required = false) Long idCompany,
			@RequestParam(name = "employee_id", required = false) Long idEmployee,
			@RequestParam(name = "information_id[]", required = false) Long[] informationId,
			@RequestParam(name = "comment[]", required = false) String[] comment,
			@RequestParam(name = "information_done[]", required = false) String[] informationDone,
			RedirectAttributes flash, SessionStatus status) {

		System.out.println("idcompany: " +idCompany);
		if(idCompany != null) {
			
			Company company = new Company();
			company = taskService.findCompanyById(idCompany);
			System.out.println(company);
			task.setCompany(company);
			company.addTask(task);
		}
	
		if(idEmployee != null) {
			
			Employee employee = new Employee();
			employee = taskService.findEmployeeById(idEmployee);
			task.setEmployee(employee);
			employee.addTask(task);
		}
		
		if (informationId != null) {
			for (int i = 0; i < informationId.length; i++) {

				Information information = taskService.findInformationById(informationId[i]);

				TaskInformation taskInformation = new TaskInformation();
				if (comment.length > 0) {
					if (!comment[i].isEmpty()) {
						taskInformation.setComment(comment[i]);
					}
				}

				taskInformation.setInformation(information);

				if (informationDone.length > 0) {
					if (!informationDone[i].isEmpty()) {
						boolean done = Boolean.parseBoolean(informationDone[i]);
						taskInformation.setDone(done);
						if (done) {
							Date date = new Date();
							taskInformation.setDoneAt(date);
						}
					}
				}

				task.addTaskInformation(taskInformation);
			}
		}

		taskService.save(task);

		status.setComplete();

		flash.addFlashAttribute("success", "Tasca creada correctament");
		return "redirect:/company/view/1";
	}
}
