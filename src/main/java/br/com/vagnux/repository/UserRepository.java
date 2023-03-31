package br.com.vagnux.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vagnux.interfaces.UserAccess;
import br.com.vagnux.interfaces.UserRules;
import br.com.vagnux.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {

	@Query(value = "SELECT * FROM user_model  where user_name = :username", nativeQuery = true)
	Optional<UserModel> findUserName(@Param("username") String username);
	
	
	
	@Query(value = "select user_model.id as userId,  microservice from user_model \n"
			+ "join user_model_profile on user_model_profile.user_model_id = user_model.id\n"
			+ "join profile_model on profile_model.id = user_model_profile.profile_id\n"
			+ "join profile_model_rules on profile_model_rules.profile_model_id = profile_model.id\n"
			+ "join rule_model on rule_model.id = profile_model_rules.rules_id\n"
			+ "where microservice = :service and user_model.user_name = :username limit 1", nativeQuery = true)
	Optional<UserAccess> hasAccess(@Param("service") String service, @Param("username") String username  );
	
	
	@Query(value = "select distinct rule_model.rule as rulename from user_model \n"
			+ "join user_model_profile on user_model_profile.user_model_id = user_model.id\n"
			+ "join profile_model on profile_model.id = user_model_profile.profile_id\n"
			+ "join profile_model_rules on profile_model_rules.profile_model_id = profile_model.id\n"
			+ "join rule_model on rule_model.id = profile_model_rules.rules_id\n"
			+ "where microservice = :service and user_model.user_name = :username ", nativeQuery = true)
	List<UserRules> aclList(@Param("service") String service, @Param("username") String username  );
}


