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

	@Query("SELECT u.id AS userid, r.microservice as microservice, r.httpPort AS port \n" + "FROM UserModel u \n"
			+ "JOIN u.profile p \n" + "JOIN p.Rules r \n" + "WHERE r.microservice = :service \n"
			+ "AND u.userName = :username \n" + "ORDER BY u.id ASC")
	List<UserAccess> hasAccess(@Param("service") String service, @Param("username") String username);

	@Query("SELECT DISTINCT pr.rule AS rulename \n" + "FROM UserModel u \n" + "JOIN u.profile p \n"
			+ "JOIN p.Rules pr \n" + "WHERE pr.microservice = :service \n" + "AND u.userName = :username")
	List<UserRules> aclList(@Param("service") String service, @Param("username") String username);

}
