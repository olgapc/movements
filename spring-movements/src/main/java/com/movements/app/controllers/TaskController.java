package com.movements.app.controllers;

import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movements.app.editors.PascalCaseEditor;
import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;
import com.movements.app.models.entity.Information;
import com.movements.app.models.entity.Task;
import com.movements.app.models.entity.TaskInformation;
import com.movements.app.models.pks.TaskInformationPK;
import com.movements.app.models.service.ICompanyService;
import com.movements.app.models.service.IEmployeeService;
import com.movements.app.models.service.ITaskService;

@Controller
@SessionAttributes("task")
public class TaskController {

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private ITaskService taskService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// If binder.registerCustomEditor(String.class,new UpperCaseEditor()); all the
		// String attributes to UpperCase
		binder.registerCustomEditor(String.class, "description", new PascalCaseEditor());
		binder.registerCustomEditor(String.class, "nameTemplate", new PascalCaseEditor());

	}

	@RequestMapping(value = { "/task/list", "/" }, method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("title", "Llistat de tasques");
		model.addAttribute("tasks", taskService.findAll());
		return "/task/list";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/task/view/{idTask}")
	public String view(@PathVariable(value = "idTask") Long idTask, Model model, RedirectAttributes flash) {

		Task task = taskService.fetchByIdWithEmployeeWithCompanyWithTaskInformationWithInformationWithSubtask(idTask);

		if (task == null) {
			flash.addFlashAttribute("error", "La tasca no existeix a la BdD");
			return "redirect:/task/list";
		}

		model.addAttribute("task", task);
		model.addAttribute("title", "Tasca: ".concat(task.getDescription()));

		return "/task/view";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/task/form")
	public String create(Map<String, Object> model, RedirectAttributes flash) {

		Task task = new Task();
		task.setCompany(null);
		task.setEmployee(null);
		task.setTaskMain(true);
		model.put("company", task.getCompany());
		model.put("employee", task.getEmployee());

		model.put("task", task);
		model.put("title", "Crear tasca");

		return "/task/form";

	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/task/form/task/{taskId}")
	public String edit(@PathVariable(value = "taskId", required = false) Long id, Map<String, Object> model,
			RedirectAttributes flash) {

		Task task = null;

		if (id > 0) {
			task = taskService.findTaskById(id);

			if (task == null) {
				flash.addFlashAttribute("error", "La tasca no existeix a la BdD");
				return "redirect:/task/list";
			}
		} else {
			flash.addFlashAttribute("error", "L'identificador de la tasca no pot ser zero");
			return "redirect:/task/list";
		}

		model.put("task", task);
		model.put("title", "Crear tasca");

		return "/task/form";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/task/form/{companyId}")
	public String create(@PathVariable(value = "companyId") Long companyId, Map<String, Object> model,
			RedirectAttributes flash) {

		Company company = companyService.findById(companyId);

		if (company == null) {
			flash.addFlashAttribute("error", "L'empresa no existeix a la BdD");
			return "redirect:/task/list";
		}

		Task task = new Task();
		task.setCompany(company);
		task.setTaskMain(true);

		model.put("task", task);
		model.put("title", "Crear tasca");

		return "/task/form";

	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/task/form/subtask/{mainTaskId}")
	public String createSubtask(@PathVariable(value = "mainTaskId") Long mainTaskId, Map<String, Object> model,
			RedirectAttributes flash) {

		Task mainTask = taskService.findTaskById(mainTaskId);

		if (mainTask == null) {
			flash.addFlashAttribute("error", "La tasca principal no existeix a la BdD");
			return "redirect:/task/list";
		}

		Task task = new Task();
		task.setMainTask(mainTask);
		task.setTaskMain(false);
		task.setDeadline(mainTask.getDeadline());

		model.put("task", task);
		model.put("title", "Crear subtasca de: " + mainTask.getDescription());

		return "/task/form";

	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/task/form/{companyId}/{employeeId}")
	public String create(@PathVariable(value = "companyId") Long companyId,
			@PathVariable(value = "employeeId") Long employeeId, Map<String, Object> model, RedirectAttributes flash) {

		Company company = companyService.findById(companyId);
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
		task.setTaskMain(true);

		model.put("task", task);
		model.put("title", "Crear tasca");

		return "/task/form";
	}

	@GetMapping(value = "/task/upload-informations/{term}", produces = { "application/json" })
	public @ResponseBody List<Information> uploadInformations(@PathVariable String term) {
		return taskService.findInformationByDescription(term);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/task/form")
	public String save(@Valid Task task, BindingResult result, Model model,
			@RequestParam(name = "company.id", required = false) Long idCompany,
			@RequestParam(name = "company.name", required = false) String nameCompany,
			@RequestParam(name = "employee.id", required = false) Long idEmployee,
			@RequestParam(name = "employee.name", required = false) String nameEmployee,
			@RequestParam(name = "information_id[]", required = false) Long[] informationId,
			@RequestParam(name = "comment[]", required = false) String[] comment,
			@RequestParam(name = "information_done[]", required = false) String[] informationDone,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Crear tasca");
			return "/task/form";
		}

		if (idCompany != null) {
			Company company = new Company();
			company = taskService.findCompanyById(idCompany);
			task.setCompany(company);
			company.addTask(task);
		} else {
			if (!nameCompany.isEmpty()) {
				result.rejectValue("company.name", "error.user", "L'empresa informada no existeix");
			} else {
				task.setCompany(null);
			}
		}

		if (idEmployee != null) {
			Employee employee = new Employee();
			employee = taskService.findEmployeeById(idEmployee);
			if(employee.getCompany().getId() == idCompany) {
				task.setEmployee(employee);
				employee.addTask(task);
			}else {
				result.rejectValue("employee.name", "error.user", "Treballador no pertany a l'empresa informada");
			}
			
		} else {
			if(!nameEmployee.isEmpty()) {
				result.rejectValue("employee.name", "error.user", "El treballador informat no existeix");
			} else {
				task.setEmployee(null);
			}
		}

		if (informationId != null) {
			for (int i = 0; i < informationId.length; i++) {

				Information information = taskService.findInformationById(informationId[i]);

				TaskInformationPK taskInformationPK = new TaskInformationPK(task, information);
				TaskInformation taskInformation = new TaskInformation (taskInformationPK);
				
				
				if (comment.length > 0) {
					if (!comment[i].isEmpty()) {
						taskInformation.setComment(comment[i]);
					}
				}

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

		/*
		 * if (result.hasErrors()) { model.addAttribute("title", "Crear tasca"); return
		 * "/task/form"; }
		 */

		taskService.save(task);

		status.setComplete();

		flash.addFlashAttribute("success", "Tasca creada correctament");
		return "redirect:/task/list";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/task/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Task task = taskService.findTaskById(id);

		if (task != null) {
			taskService.delete(id);
			flash.addFlashAttribute("success", "Tasca eliminada correctament");
			return "redirect:/task/list";
		}

		flash.addFlashAttribute("error", "La tasca no existeix a la BdD, no s'ha pogut eliminar");
		return "redirect:/task/list";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = { "/information/list" }, method = RequestMethod.GET)
	public String listInformation(Model model) {
		model.addAttribute("title", "Llistat d'informacions");
		model.addAttribute("informations", taskService.findAllInformations());
		return "/information/list";
	}

	@Secured("ROLE_USER")
	@GetMapping("/information/view/{idInformation}")
	public String viewInformation(@PathVariable(value = "idInformation") Long idInformation, Model model,
			RedirectAttributes flash) {

		Information information = taskService.findInformationById(idInformation);

		if (information == null) {
			flash.addFlashAttribute("error", "La informació no existeix a la BdD");
			return "redirect:/information/list";
		}

		model.addAttribute("information", information);
		model.addAttribute("title", "Informació: ".concat(information.getDescription()));

		return "/information/view";
	}

	@Secured("ROLE_USER")
	@GetMapping("/information/form")
	public String createInformation(Map<String, Object> model, RedirectAttributes flash) {

		Information information = new Information();

		model.put("information", information);
		model.put("title", "Formulari d'informació");

		return "/information/form";

	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/information/form/{id}")
	public String editInformation(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {

		Information information = null;

		if (id > 0) {
			information = taskService.findInformationById(id);
			if (information == null) {
				flash.addFlashAttribute("error", "L'identificador de la informació no existeix a la BdD");
				return "redirect:/information/list";
			}
		} else {
			flash.addFlashAttribute("error", "L'identificador de la informació no pot ser zero");
			return "redirect:/information/list";
		}
		
		model.put("information", information);
		model.put("title", "Formulari d'Informació");
		
		return "/information/form";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/information/form", method = RequestMethod.POST)
	public String saveInformation(@Valid Information information, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Formulari d'Informació");
			return "/information/form";
		}

		String flashMessage = (information.getId() != null) ? "Informació modificada correctament"
				: "Informació creada correctament";

		taskService.saveInformation(information);
		status.setComplete();
		flash.addFlashAttribute("success", flashMessage);
		
		return "redirect:/information/list";
	}

	@Secured("ROLE_USER")
	@GetMapping("/information/delete/{id}")
	public String deleteInformation(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Information information = taskService.findInformationById(id);

		if (information != null) {
			taskService.deleteInformation(id);
			;
			flash.addFlashAttribute("success", "Informació eliminada correctament");
			return "redirect:/information/list";
		}

		flash.addFlashAttribute("error", "La informació no existeix a la BdD, no s'ha pogut eliminar");
		return "redirect:/information/list";
	}

}
