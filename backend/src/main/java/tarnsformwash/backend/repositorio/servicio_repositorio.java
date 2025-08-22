package tarnsformwash.backend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tarnsformwash.backend.entidad.servicio_entidad;

@Repository
public interface servicio_repositorio extends JpaRepository<servicio_entidad, String> {
}
