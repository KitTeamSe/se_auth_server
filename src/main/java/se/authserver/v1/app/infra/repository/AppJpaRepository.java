package se.authserver.v1.app.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.authserver.v1.app.domain.model.App;

public interface AppJpaRepository extends JpaRepository<App, Long> {

}
