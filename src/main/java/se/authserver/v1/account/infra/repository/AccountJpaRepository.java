package se.authserver.v1.account.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.authserver.v1.account.domain.model.Account;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

}
