package br.com.vagnux.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vagnux.interfaces.UserAccess;
import br.com.vagnux.interfaces.UserRules;
import br.com.vagnux.model.ProfileModel;
import br.com.vagnux.model.UserModel;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileModel, String> {

}
