package tarnsformwash.backend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tarnsformwash.backend.entidad.personal_entidad;

@Repository
public interface personal_repositorio extends JpaRepository<personal_entidad, String> {

}
