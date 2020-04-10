package com.movements.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;
import com.movements.app.models.service.ICompanyService;
import com.movements.app.models.service.IEmployeeService;
import com.movements.app.validation.EmployeeValidator;

@Controller
@RequestMapping("/employee")
@SessionAttributes("employee")
public class EmployeeController {

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private EmployeeValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);

	}

	@Secured("ROLE_USER")
	@GetMapping(value = "/view/{employeeId}")
	public String view(@PathVariable(value = "employeeId", required = false) Long employeeId, Map<String, Object> model,
			RedirectAttributes flash) {
		Employee employee = employeeService.fetchByIdWithTasksWithCompany(employeeId);

		if (employee == null) {
			flash.addFlashAttribute("error", "El treballador no existeix a la BdD");
			return "redirect:/company/list";
		}

		model.put("employee", employee);
		model.put("title", employee.getName());
		model.put("companyName", employee.getCompany().getName());
		return "/employee/view";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{companyId}")
	public String create(@PathVariable(value = "companyId", required = false) Long companyId, Map<String, Object> model,
			RedirectAttributes flash) {

		if (companyId == null) {
			return "redirect:/company/list";
		}
		Company company = companyService.findOne(companyId);

		if (company == null) {
			flash.addFlashAttribute("error", "L\'empresa no existeix a la BdD");
			return "redirect:/company/list";
		}
		Employee employee = new Employee();
		employee.setCompany(company);
		model.put("employee", employee);
		model.put("title", "Crear treballador");

		return "/employee/form";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{companyId}/{employeeId}")
	public String edit(@PathVariable(value = "companyId", required = false) Long companyId,
			@PathVariable(value = "employeeId", required = false) Long employeeId, Map<String, Object> model,
			RedirectAttributes flash) {
		Employee employee = null;
		if (employeeId > 0) {
			employee = employeeService.findOne(employeeId);

			if (employee == null) {
				flash.addFlashAttribute("error", "L'identificador del treballador no existeix a la BdD");
				return "redirect:/company/view/{companyId}";
			}
		} else {
			flash.addFlashAttribute("error", "L'identificador del treballador no pot ser zero");
			return "redirect:/company/view/{companyId}";
		}
		model.put("employee", employee);
		model.put("title", "Formulari de Treballador");
		return "/employee/form";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/form")
	public String create(Map<String, Object> model) {
		Employee employee = new Employee();
		employee.setCompany(null);
		model.put("company", employee.getCompany());
		model.put("employee", employee);
		model.put("title", "Crear treballador");
		return "/employee/form";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid Employee employee, BindingResult result,
			@RequestParam(name = "company_id", required = false) Long companyId, Model model, RedirectAttributes flash,
			SessionStatus status) {

		// validator.validate(employee, result);


		if (companyId != null) {
			Company company = new Company();
			company = employeeService.findCompanyById(companyId);
			if (employee.getId() == null) { // creació del treballador
				employee.setCompany(company);
				company.addEmployee(employee);
			}
		} else {
			employee.setCompany(null);
			result.rejectValue("company.name", "error.user", "L'empresa informada no existeix");	
		}
		
		
		
		if (result.hasErrors()) {
			model.addAttribute("title", "Formulari de Treballador");
			
				return "/employee/form";
			
		}

		String flashMessage = (employee.getId() != null) ? "Treballador modificat correctament"
				: "Treballador creat correctament";

		employeeService.save(employee);
		status.setComplete();
		flash.addFlashAttribute("success", flashMessage);

		return "redirect:/company/view/" + employee.getCompany().getId();
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("title", "Llistat de treballadors");
		model.addAttribute("employees", employeeService.findAll());
		return "/employee/list";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/delete/{companyId}/{employeeId}")
	public String delete(@PathVariable(value = "companyId", required = false) Long companyId,
			@PathVariable(value = "employeeId", required = false) Long employeeId, RedirectAttributes flash) {
		if (employeeId > 0) {
			employeeService.delete(employeeId);
			flash.addFlashAttribute("success", "Treballador eliminat correctament");
		}
		return "redirect:/company/view/" + companyId;
	}

}
