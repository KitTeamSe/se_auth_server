package se.authserver.v1.token.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.authserver.v1.token.domain.model.Token;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {

}
