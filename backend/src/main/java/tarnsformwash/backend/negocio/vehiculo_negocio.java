package tarnsformwash.backend.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tarnsformwash.backend.entidad.vehiculo_entidad;
import tarnsformwash.backend.repositorio.vehiculo_repositorio;

import java.util.List;

@Service
public class vehiculo_negocio {

    @Autowired
    private vehiculo_repositorio vehiculo_repositorio;

    public List<vehiculo_entidad> listarTodos() {
        return vehiculo_repositorio.findAll();
    }

    public vehiculo_entidad guardar(vehiculo_entidad vehiculo_entidad) {
        return vehiculo_repositorio.save(vehiculo_entidad);
    }

    public vehiculo_entidad buscarPorPlaca(String placa) {
        return vehiculo_repositorio.findByPlaca(placa);
    }

    public boolean eliminar(String placa) {
        if (vehiculo_repositorio.existsById(placa)) {
            vehiculo_repositorio.deleteById(placa);
            return true;
        }
        return false;
    }

}
