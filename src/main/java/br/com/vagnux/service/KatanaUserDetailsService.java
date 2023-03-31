package br.com.vagnux.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import br.com.vagnux.model.UserModel;
import br.com.vagnux.repository.UserRepository;

@Component
public class KatanaUserDetailsService implements UserDetails, UserDetailsService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserRepository userRepository;

	private UserModel user = null;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ANYONE"));
		
		return list;
	}

	@Override
	public String getPassword() {

		if (this.user != null) {
			return user.getPassword();
		}
		return null;
	}

	@Override
	public String getUsername() {
		if (this.user != null) {
			return user.getUserName();
		}
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		if (this.user != null) {
			return user.getIsAccountNonExpired();
		}
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		if (this.user != null) {
			return user.getIsAccountNonLocked();
		}
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		if (this.user != null) {
			return user.getIsCredentialsNonExpired();
		}
		return false;
	}

	@Override
	public boolean isEnabled() {
		if (this.user != null) {
			return user.getIsEnabled();
		}
		return false;
	}

	public UserDetails loadUserByUsername(String username) {

		Optional<UserModel> userfinded;
		UserDetails result;

		userfinded = userRepository.findUserName(username);

		if (userfinded.isPresent()) {
			this.user = userfinded.get();
			return this;
		}

		return null;
	}

}
