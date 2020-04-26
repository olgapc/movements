package com.movements.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.movements.app.editors.PascalCaseEditor;
import com.movements.app.editors.RolesEditor;
import com.movements.app.editors.UpperCaseEditor;
import com.movements.app.models.entity.AppUser;
import com.movements.app.models.entity.Role;
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
	public String view(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

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
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		AppUser user = null;

		if (id > 0) {
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
	public String save(@Valid AppUser user, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Formulari d'Usuari");
			return "/user/form";
		}

		String flashMessage = (user.getId() != null) ? "Usuari modificat correctament" : "Usuari creat correctament";

		userService.save(user);
		status.setComplete();
		flash.addFlashAttribute("success", flashMessage);

		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			userService.delete(id);
			flash.addFlashAttribute("success", "Usuari eliminat correctament");
		}
		return "redirect:/user/list";

	}

}
