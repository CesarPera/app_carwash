package tarnsformwash.backend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tarnsformwash.backend.entidad.vehiculo_entidad;

@Repository
public interface vehiculo_repositorio extends JpaRepository<vehiculo_entidad, String> {
    vehiculo_entidad findByPlaca(String placa);
}
