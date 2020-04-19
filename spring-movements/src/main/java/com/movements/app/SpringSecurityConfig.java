
package com.movements.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.movements.app.auth.handler.LoginSuccessHandler;
import com.movements.app.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true) // secure with Annotation @Secured("ROLE_USER") in Controllers
//@EnableGlobalMethodSecurity(prePostEnabled=true) //secure with Annotation @PreAuthorize("hasRole('ROLE_USER')")
//or both @EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JpaUserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private LoginSuccessHandler successHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/task/list").permitAll()
				/*
				 * .antMatchers("/employee/view/**").hasAnyRole("USER")
				 * .antMatchers("/uploads/**").hasAnyRole("USER")
				 * .antMatchers("/company/**").hasAnyRole("USER")
				 * .antMatchers("/information/**").hasAnyRole("USER")
				 * .antMatchers("/employee/form/**").hasAnyRole("ADMIN")
				 * .antMatchers("/employee/delete/**").hasAnyRole("ADMIN")
				 * .antMatchers("/task/**").hasAnyRole("ADMIN")
				 */
				.anyRequest().authenticated()
				.and()
				.formLogin().successHandler(successHandler).loginPage("/login")
				.permitAll().and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/error_403");

	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

		builder.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		
		
		//PasswordEncoder encoder = this.passwordEncoder;

		// encrypt the password.
		//User.builder().passwordEncoder(password -> encoder.encode(password));
		//UserBuilder users = User.builder().passwordEncoder(encoder::encode);

		//builder.inMemoryAuthentication().withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
			//	.withUser(users.username("olga").password("12345").roles("USER"));

	}

}
