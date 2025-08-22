package tarnsformwash.backend.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tarnsformwash.backend.entidad.servicio_entidad;
import tarnsformwash.backend.repositorio.servicio_repositorio;

import java.util.List;
import java.util.Optional;

@Service
public class servicio_negocio {
    @Autowired
    private servicio_repositorio repo;

    // Listar todos
    public List<servicio_entidad> listarServicios() {
        return repo.findAll();
    }

    // Buscar por ID (String)
    public Optional<servicio_entidad> buscarPorId(String id) {
        return repo.findById(id);
    }

    // Guardar o actualizar
    public servicio_entidad guardarServicio(servicio_entidad servicio) {
        return repo.save(servicio);
    }

    // Eliminar por ID (String)
    public void eliminarServicio(String id) {
        repo.deleteById(id);
    }
}