package br.com.vagnux.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ProfileModel {

	@Id
	private String id;

	private String name;

	@OneToMany
	private Set<RuleModel> Rules;

	private Boolean isEnable = true;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<RuleModel> getRules() {
		return Rules;
	}

	public void setRules(Set<RuleModel> rules) {
		Rules = rules;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public ProfileModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
