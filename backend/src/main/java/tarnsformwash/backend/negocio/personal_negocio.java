package tarnsformwash.backend.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tarnsformwash.backend.entidad.personal_entidad;
import tarnsformwash.backend.entidad.vehiculo_entidad;
import tarnsformwash.backend.repositorio.personal_repositorio;
import tarnsformwash.backend.repositorio.vehiculo_repositorio;

import java.util.List;
import java.util.Optional;

@Service
public class personal_negocio {

    @Autowired
    private personal_repositorio personal_repositorio;

    public List<personal_entidad> listarTodos() {
        return personal_repositorio.findAll();
    }

    public Optional<personal_entidad> buscarPorId(String dni) {
        return personal_repositorio.findById(dni);
    }

    public personal_entidad guardar(personal_entidad personal_entidad) {
        return personal_repositorio.save(personal_entidad);
    }

    public personal_entidad actualizar(String dni, personal_entidad personal_entidad) {
        if (personal_repositorio.existsById(dni)) {
            personal_entidad.setDniPersona(dni);
            return personal_repositorio.save(personal_entidad);
        }
        return null;
    }

    public void eliminar(String dni) {
        personal_repositorio.deleteById(dni);
    }

}
