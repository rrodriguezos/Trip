package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer>{
	//Find administrator by user account
	@Query("select a from Administrator a where a.userAccount.id=?1")
	Administrator findByUserAccountId(int userAccountId);
}
