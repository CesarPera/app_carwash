package tarnsformwash.backend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tarnsformwash.backend.entidad.usuario_entidad;

@Repository
public interface usuario_repositorio extends JpaRepository<usuario_entidad, Integer> {
    usuario_entidad findByNombreAndClave(String nombre, String clave);
}