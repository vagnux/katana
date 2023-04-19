package br.com.vagnux.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vagnux.model.RuleModel;

@Repository
public interface RuleRepository extends JpaRepository<RuleModel, String> {

	@Query("SELECT r FROM RuleModel r where r.microservice = :service and r.isPublic = 1 GROUP BY r.microservice ")
	List<RuleModel> findPublic(@Param("service") String service);

}
