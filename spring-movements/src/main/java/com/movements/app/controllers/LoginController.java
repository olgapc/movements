package com.movements.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(Model model, Principal principal, RedirectAttributes flash) {
		//sesión iniciada anteriormente
		if(principal != null) {
			flash.addFlashAttribute("info", "Ja has iniciat sessió");
			return "redirect:/";
		}
		return "login";
	}
}
