package com.movements.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movements.app.models.entity.Company;
import com.movements.app.models.service.ICompanyService;

@Controller
@SessionAttributes("company")
public class CompanyController {

	@Autowired
	private ICompanyService companyService;

	@GetMapping(value = "/company/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		Company company = companyService.findOne(id);
		if (company == null) {
			flash.addFlashAttribute("error", "L'empresa no existeix a la BdD");
			return "redirect:/company/list";
		}
		model.put("company", company);
		model.put("title", "Empresa: " + company.getName());
		return "/company/view";
	}

	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("title", "Llistat d'empreses");
		model.addAttribute("companies", companyService.findAll());
		return "/company/list";
	}

	@GetMapping(value = "/company/form")
	public String create(Map<String, Object> model) {
		Company company = new Company();
		model.put("company", company);
		model.put("title", "Formulari d'Empresa");
		return "/company/form";
	}

	@RequestMapping(value = "/company/form/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Company company = null;
		System.out.println("form/id edit company");
		if (id > 0) {
			company = companyService.findOne(id);
			System.out.println("company/id edit: "+ company + " " +  company.getId());
			if (company == null) {
				flash.addFlashAttribute("error", "L'identificador de l'empresa no existeix a la BdD");
				return "redirect:/company/list";
			}
		} else {
			flash.addFlashAttribute("error", "L'identificador de l'empresa no pot ser zero");
			return "redirect:/company/list";
		}
		model.put("company", company);
		model.put("title", "Formulari d'Empresa");
		return "/company/form";
	}

	@RequestMapping(value = "/company/form", method = RequestMethod.POST)
	public String save(@Valid Company company, BindingResult result, Model model,
			@RequestParam("file") MultipartFile logo, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Formulari d'Empresa");
			return "/company/form";
		}
		if (!logo.isEmpty()) {
			Path resourcesDirectory = Paths.get("src//main//resources//static/uploads");
			String rootPath = resourcesDirectory.toFile().getAbsolutePath();
			try {
				byte[] bytes = logo.getBytes();
				Path completePath = Paths.get(rootPath + "//" + logo.getOriginalFilename());
				Files.write(completePath, bytes);
				flash.addFlashAttribute("info", "Has pujat correctament: '" + logo.getOriginalFilename() + "'");
				company.setLogo(logo.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("company/form save: " + company + " " + company.getId());
		String flashMessage = (company.getId() != null) ? "Empresa modificada correctament"
				: "Empresa creada correctament";

		companyService.save(company);
		status.setComplete();
		flash.addFlashAttribute("success", flashMessage);
		return "redirect:/company/list";
	}

	@RequestMapping(value = "/company/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			companyService.delete(id);
			flash.addFlashAttribute("success", "Empresa eliminada correctament");
		}
		return "redirect:/company/list";

	}

}
