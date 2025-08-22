package tarnsformwash.backend.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tarnsformwash.backend.entidad.ventas_entidad;
import tarnsformwash.backend.repositorio.ventas_repositorio;

import java.util.List;
import java.util.Optional;

@Service
public class ventas_negocio {
    @Autowired
    private ventas_repositorio ventas_repositorio;

    public List<ventas_entidad> listarTodos() {
        return ventas_repositorio.findAll();
    }

    public ventas_entidad guardar(ventas_entidad ventas_entidad) {
        return ventas_repositorio.save(ventas_entidad);
    }

    public ventas_entidad buscarPorId(String id) {
        Optional<ventas_entidad> result = ventas_repositorio.findById(id);
        return result.orElse(null);
    }

    public boolean eliminar(String id) {
        if (ventas_repositorio.existsById(id)) {
            ventas_repositorio.deleteById(id);
            return true;
        }
        return false;
    }
}
