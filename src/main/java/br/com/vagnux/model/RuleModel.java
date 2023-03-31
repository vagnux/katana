package br.com.vagnux.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class RuleModel {

	@Id
	private String id;
	private String rule;
	private Boolean isEnable = true;
	private Boolean isPublic = false;
	private String microservice;
	private String httpPort;

	public String getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(String httpPort) {
		this.httpPort = httpPort;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getMicroservice() {
		return microservice;
	}

	public void setMicroservice(String microservice) {
		this.microservice = microservice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public RuleModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(httpPort, id, isEnable, isPublic, microservice, rule);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleModel other = (RuleModel) obj;
		return Objects.equals(httpPort, other.httpPort) && Objects.equals(id, other.id)
				&& Objects.equals(isEnable, other.isEnable) && Objects.equals(isPublic, other.isPublic)
				&& Objects.equals(microservice, other.microservice) && Objects.equals(rule, other.rule);
	}

}
