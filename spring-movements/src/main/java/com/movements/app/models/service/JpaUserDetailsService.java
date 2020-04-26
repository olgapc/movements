package com.movements.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movements.app.models.dao.IUserDao;
import com.movements.app.models.entity.UserRole;
import com.movements.app.models.entity.AppUser;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired
	private IUserDao userDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser user = userDao.findByUsername(username);
		
		if(user == null) {
			logger.error("Error login: no existeix l'usuari '" + username + "'");
			throw new UsernameNotFoundException("Username " + username + " no existeix en el sistema!");	
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(UserRole roles: user.getRoles()) {
			logger.info("Role: ".concat(roles.getRole().getRole()));
			authorities.add(new SimpleGrantedAuthority(roles.getRole().getRole()));
		}
		
		
		if(authorities.isEmpty()) {
			logger.error("'Error login: no existeix l'usuari '" + username + "'no té rol/s assignat/s'");
			throw new UsernameNotFoundException("Error login: usuari '" + username + "' no existeix en el sistema!");	
		}
		
		//TODO User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
		return new User(username, user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}

}
