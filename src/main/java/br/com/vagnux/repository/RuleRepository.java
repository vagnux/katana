package br.com.vagnux.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vagnux.model.RuleModel;

@Repository
public interface RuleRepository extends JpaRepository<RuleModel, String> {

	@Query(value = "SELECT rule_model.* FROM rule_model  where microservice = :service and is_Public = 1 limit 1", nativeQuery = true)
	Optional<RuleModel> findPublic(@Param("service") String service);

}
