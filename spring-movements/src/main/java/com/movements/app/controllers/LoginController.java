package com.movements.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		
		//sesión iniciada anteriormente
		if(principal != null) {
			model.addAttribute("error", "Ja has iniciat sessió");
			//flash.addFlashAttribute("info", "Ja has iniciat sessió");
			//return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Error: nom d'usuari i/o contrasenya incorrectes");
			
		}
		
		if(logout != null) {
			model.addAttribute("success", "Sessió tancada amb èxit");
			
		}
		return "login";
	}
}
