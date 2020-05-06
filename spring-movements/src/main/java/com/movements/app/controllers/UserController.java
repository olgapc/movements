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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movements.app.editors.LowerCaseEditor;
import com.movements.app.editors.RolesEditor;
import com.movements.app.models.entity.AppUser;
import com.movements.app.models.entity.Role;
import com.movements.app.models.entity.UserRole;
import com.movements.app.models.pks.UserRolePK;
import com.movements.app.models.service.IUserService;

@Secured("ROLE_ADMIN")
@Controller
@SessionAttributes("user")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private RolesEditor rolesEditor;

	@ModelAttribute("rolesList")
	public List<Role> rolesList() {
		return this.userService.listRoles();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Role.class, "roles", rolesEditor);

		binder.registerCustomEditor(String.class, "username", new LowerCaseEditor());

	}

	@GetMapping(value = "/user/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {

		AppUser user = userService.findById(id);

		if (user == null) {
			flash.addFlashAttribute("error", "L'usuari no existeix a la BdD");
			return "redirect:/user/list";
		}
		model.put("user", user);
		model.put("title", "Usuari: " + user.getUsername());
		return "/user/view";
	}

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("title", "Llistat d'usuaris");
		model.addAttribute("users", userService.findAll());
		return "/user/list";
	}

	@GetMapping(value = "/user/form")
	public String create(Map<String, Object> model) {

		AppUser user = new AppUser();

		model.put("user", user);
		model.put("title", "Formulari d'Usuari");

		return "/user/form";
	}

	@RequestMapping(value = "/user/form/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {

		AppUser user = null;

		if (id!=null) {
			user = userService.findById(id);
			if (user == null) {
				flash.addFlashAttribute("error", "L'identificador de l'usuari no existeix a la BdD");
				return "redirect:/user/list";
			}
		} else {
			flash.addFlashAttribute("error", "L'identificador de l'usuari no pot ser zero");
			return "redirect:/user/list";
		}

		model.put("user", user);
		model.put("title", "Formulari d'Usuari");

		return "/user/form";
	}

	@RequestMapping(value = "/user/form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("user") AppUser user, BindingResult result, Model model,
			// @RequestParam(name = "roles[0].role.id", required = false) Long role1,
			// @RequestParam(name = "roles[1].role.id", required = false) Long role2,
			//@RequestParam(value = "roles", required = false) Long[] rolesId, 
			RedirectAttributes flash,
			SessionStatus status) {

		// System.out.println("hola " + roleId.length);

		/*
		 * if (role1 == null & role2 == null) { user.setRoles(null);
		 * result.rejectValue("roles", "error.user", "Indicar com a mínim un rol");
		 * System.out.println("holnjhh "); } else { if (role1 != null) { Role role =
		 * userService.findRoleById(role1); System.out.println("aquest role : " +
		 * role.getRole()); // UserRolePK userRolePK = new UserRolePK(user, role); //
		 * UserRole userRole = new UserRole(userRolePK);
		 * 
		 * user.addRoles(role);
		 * 
		 * } if (role2 != null) { Role role = userService.findRoleById(role2);
		 * System.out.println("aquest role : " + role.getRole()); // UserRolePK
		 * userRolePK = new UserRolePK(user, role); // UserRole userRole = new
		 * UserRole(userRolePK);
		 * 
		 * user.addRoles(role);
		 * 
		 * } }
		 */
		/*
		 * for (int i = 0; i < roleId.length; i++) { Role role =
		 * userService.findRoleById(roleId[i]); System.out.println("aquest role : " +
		 * role.getRole()); UserRolePK userRolePK = new UserRolePK(user, role); UserRole
		 * userRole = new UserRole(userRolePK);
		 * 
		 * user.addRoles(userRole);
		 * 
		 * for (UserRole ro : user.getRoles()) { System.out.println("rol: " +
		 * ro.getDescription()); } System.out.println(user.getRoles()); }
		 */

		/*
		 * if (rolesId == null || rolesId.length == 0) { user.setRoles(null);
		 * result.rejectValue("roles", "error.user", "Indicar com a mínim un rol");
		 * System.out.println("holnjhh "); } else { for (int i = 0; i < rolesId.length;
		 * i++) { Role role = userService.findRoleById(rolesId[i]);
		 * System.out.println("aquest role : " + role.getRole()); user.addRoles(role);
		 * 
		 * for (UserRole ro : user.getRoles()) { System.out.println("rols num: " +
		 * rolesId.length + " rols a usuari: " + user.getRoles().size() + "usuari: " +
		 * user.getUsername() + " rol: " + ro.getDescription()); }
		 * System.out.println(user.getRoles()); } }
		 */

		if (result.hasErrors()) {
			System.out.println("errors");
			System.out.println(result.getAllErrors());
			model.addAttribute("title", "Formulari d'Usuari");
			return "/user/form";
		}

		String flashMessage = (user.getId() != null) ? "Usuari modificat correctament" : "Usuari creat correctament";

		userService.save(user);

		status.setComplete();
		flash.addFlashAttribute("success", flashMessage);

		/*
		 * for (UserRole ro : user.getRoles()) { System.out.println("rol: " +
		 * ro.getDescription() + " usuari: " + ro.getUser().getUsername()); }
		 * System.out.println(user.getRoles());
		 */

		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id!=null) {
			userService.delete(id);
			flash.addFlashAttribute("success", "Usuari eliminat correctament");
		}
		return "redirect:/user/list";

	}

}
