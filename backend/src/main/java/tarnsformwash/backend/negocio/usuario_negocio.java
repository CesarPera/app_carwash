package tarnsformwash.backend.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tarnsformwash.backend.entidad.usuario_entidad;
import tarnsformwash.backend.repositorio.usuario_repositorio;

@Service
public class usuario_negocio {

    @Autowired
    private usuario_repositorio usuario_repositorio;

    public usuario_entidad login(String nombre, String clave) {
        return usuario_repositorio.findByNombreAndClave(nombre, clave);
    }
}
