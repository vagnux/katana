package br.com.vagnux.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.vagnux.interfaces.UserAccess;
import br.com.vagnux.interfaces.UserRules;
import br.com.vagnux.model.RuleModel;
import br.com.vagnux.model.UserModel;
import br.com.vagnux.repository.RuleRepository;
import br.com.vagnux.repository.UserRepository;

@Component
public class AccessService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RuleRepository ruleRepository;

	@SuppressWarnings("static-access")
	public boolean hasAccess(String service, UserDetails userDetails) {

		boolean response = false;
		if (userDetails.isAccountNonLocked() == false) {
			return false;
		}
		if (userDetails.isEnabled() == false) {
			return false;
		}

		List<RuleModel> servicePublic = ruleRepository.findPublic(service);

		if (servicePublic.size() > 0) {
			if (servicePublic.get(0).getIsPublic() == true) {
				return true;
			}
		}

		List<UserAccess> userAccess = userRepository.hasAccess(service, userDetails.getUsername());

		if (userAccess.size() > 0) {
			if (userAccess.get(1).getMicroservice().toString().equals(service.toString())) {
				return true;
			}
		}

		return response;
	}

	public List<String> aclList(String service, String username) {

		List<UserRules> acl = userRepository.aclList(service, username);
		List<String> result = new ArrayList<String>();
		for (UserRules access : acl) {
			result.add(access.getRulename());
		}

		return result;

	}

	public String getUid(String username) {

		Optional<UserModel> user = userRepository.findUserName(username);
		if (user.isPresent()) {
			return user.get().getId().toString();
		}
		return null;
	}

	public String getServicePort(String service, UserDetails userDetails) {
		List<UserAccess> userAccess = userRepository.hasAccess(service, userDetails.getUsername());
		String port = "8080";
		if (userAccess.size() > 0) {
			UserAccess micro = userAccess.get(0);
			if (userAccess.get(0).getMicroservice().toString().equals(service.toString())) {
				port = userAccess.get(0).getPort();
			}
		}
		return port;
	}
}
