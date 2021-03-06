package templates.taskold;


import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@SessionAttributes("task")
public class TaskController {

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private ITaskService taskService;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("/task/form/{companyId}/{employeeId}")
	public String create(
			@PathVariable(value = "companyId", required = false) Long companyId,
			@PathVariable(value = "employeeId", required = false) Long employeeId, 
			Map<String, Object> model,
			RedirectAttributes flash) {

		if (employeeId == null) {
			return "redirect:/company/list";
		}
		Employee employee = employeeService.findOne(employeeId);

		if (employee == null) {
			flash.addFlashAttribute("error", "El treballador no existeix a la BdD");
			return "redirect:/company/list";
		}
		
		Task task = new Task();
		task.setCompany(employee.getCompany());
		task.setEmployee(employee);
		model.put("task", task);
		model.put("title", "Crear tasca");

		return "/task/form";
	}

	@GetMapping(value = "task/form")
	public String create(Map<String, Object> model) {
		Task task = new Task();
		task.setCompany(null);
		task.setEmployee(null);
		model.put("company", task.getCompany());
		model.put("employee", task.getEmployee());
		model.put("task", task);
		model.put("title", "Crear tasca");
		return "/task/form";
	}

	@GetMapping("/task/form/{companyId}")
	public String create(@PathVariable(value = "companyId", required = false) Long companyId, Map<String, Object> model,
			RedirectAttributes flash) {

		if (companyId == null) {
			return "redirect:/company/list";
		}

		Company company = companyService.findById(companyId);

		if (company == null) {
			flash.addFlashAttribute("error", "L'empresa no existeix a la BdD");
			return "redirect:/company/list";
		}
		Task task = new Task();
		task.setCompany(company);
		model.put("task", task);
		model.put("title", "Crear tasca");

		return "/task/form";
	}

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

//	@GetMapping(value = "/upload-employees/{term}", produces= {"application/json"})
//	public @ResponseBody List<Employee> uploadEmployees(@PathVariable String term){
//		return taskService.findEmployeeByName(term);
//	}

	// @PostMapping("/form")
	// public String save(Task task, @RequestParam(name="")) {
	// return

	// }

	@RequestMapping(value = "/task/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("title", "Llistat de tasques");
		model.addAttribute("tasks", taskService.findAll());
		return "/task/list";
	}

	@GetMapping(value = "/task/upload-informations/{term}", produces = { "application/json" })
	public @ResponseBody List<Information> uploadInformations(@PathVariable String term) {
		return taskService.findInformationByDescription(term);
	}

	@PostMapping("/task/form")
	public String save(@Valid Task task, BindingResult result, Model model,
			@RequestParam(name = "search_company_id", required = false) Long searchCompanyId,
			@RequestParam(name = "search_employee_id", required = false) Long searchEmployeeId,
			@RequestParam(name = "information_id[]", required = false) Long[] informationId,
			@RequestParam(name = "comment[]", required = false) String[] comment,
			@RequestParam(name = "checkbox_done[]", required = false) Boolean[] checkbox_done, 
			RedirectAttributes flash,
			SessionStatus status) {

		//System.out.println("AQUI: " + checkbox_done.toString());


		if (result.hasErrors()) {
			model.addAttribute("title", "Crear tasca");
			return "task/form";
		}

		if (searchCompanyId != null) {
			Company company = new Company();
			company = taskService.findCompanyById(searchCompanyId);
			task.setCompany(company);
			company.addTask(task);
		}

		if (searchEmployeeId != null) {
			Employee employee = new Employee();
			employee = taskService.findEmployeeById(searchEmployeeId);
			task.setEmployee(employee);
			employee.addTask(task);
		}

		if (informationId != null) {
			for (int i = 0; i < informationId.length; i++) {

				Information information = taskService.findInformationById(informationId[i]);
				Boolean done = false;
				TaskInformation taskInformation = new TaskInformation();
				taskInformation.setInformation(information);
					

				System.out.println("INFORMACIONS: " + informationId[i]);
				if (comment.length > 0) {
					if (!comment[i].isEmpty()) {
						taskInformation.setComment(comment[i]);
					}
				}
				if (checkbox_done[i] != null) {
					done = true;
					System.out.println("RESULTATS CHECKBOX: " + checkbox_done[i]);
					taskInformation.setDone(done);
				}
				
				//taskInformation.setTask(task);
				task.addTaskInformation(taskInformation);

			}
		}

		taskService.save(task);
		status.setComplete();
		flash.addFlashAttribute("success", "Tasca creada correctament");
		return "redirect:/task/list";

	}

	@GetMapping("/task/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Task task = taskService.findTaskById(id);

		if (task == null) {
			flash.addAttribute("error", "La tasca no existeix a la BdD");
			return "redirect:/task/list";
		}

		model.put("task", task);
		model.put("title", "Tasca : ".concat(task.getDescription()));
		if (task.getCompany() != null) {
			model.put("companyName", task.getCompany().getName());
		}
		if (task.getEmployee() != null) {
			model.put("employeeName", task.getEmployee().getName());
		}

		return "task/view";

	}

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

	@RequestMapping(value = "/information/list", method = RequestMethod.GET)
	public String listInformations(Model model) {
		model.addAttribute("title", "Llistat d'informacions");
		model.addAttribute("informations", taskService.findAllInformations());
		return "/information/list";
	}

	@GetMapping("/information/view/{id}")
	public String viewInformation(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {

		Information information = taskService.findInformationById(id);

		if (information == null) {
			flash.addAttribute("error", "La informació no existeix a la BdD");
			return "redirect:/information/list";
		}

		model.put("information", information);
		model.put("title", "Informació : ".concat(information.getDescription()));

		return "information/view";
	}

	
	@GetMapping(value = "/information/form")
	public String createInformation(Map<String, Object> model) {
		
		
		Information information = new Information();
		
		model.put("information", information);
		model.put("title", "Formulari d'Informació");
		return "/information/form";
	}
	
	
	@GetMapping("/information/form/{id}")
	public String editInformation(@PathVariable(value = "id", required = false) Long id, 
			Map<String, Object> model,
			RedirectAttributes flash) {

		Information information = null;
		System.out.println("form/id edit information");
		if (id > 0) {
			information = taskService.findInformationById(id);
				
			if (information == null) {
				flash.addFlashAttribute("error", "La informació no existeix a la BdD");
				return "redirect:/information/list";
			}
			System.out.println("information/id edit: "+ information + " " +  information.getId());
			
		} else {
			flash.addFlashAttribute("error", "L'identificador de la informació no pot ser zero");
			return "redirect:/information/list";
		}
		model.put("information", information);
		model.put("title", "Modificar informació");

		return "/information/form";
	}



	@RequestMapping(value = "/information/form", method = RequestMethod.POST)
	public String saveInformation(@Valid Information information, 
			BindingResult result, 
			Model model,
			RedirectAttributes flash, 
			SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Formulari d'Informació");
			return "/information/form";
		}
		System.out.println("information/form saveInformation: " + information + " " + information.getId());
	
		
		String flashMessage = (information.getId() != null) ? "Informació modificada correctament"
				: "Informació creada correctament";

		taskService.saveInformation(information);
		status.setComplete();
		flash.addFlashAttribute("success", flashMessage);
		return "redirect:/information/list";
	}

	
	@RequestMapping(value = "/information/delete/{id}")
	public String deleteInformation(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			taskService.deleteInformation(id);
			flash.addFlashAttribute("success", "Informació eliminada correctament");
		}
		return "redirect:/information/list";

	}

}
