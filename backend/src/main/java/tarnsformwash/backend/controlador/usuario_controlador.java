package tarnsformwash.backend.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tarnsformwash.backend.entidad.usuario_entidad;
import tarnsformwash.backend.negocio.usuario_negocio;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class usuario_controlador {

    @Autowired
    private usuario_negocio usuarioNegocio;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String nombre = body.get("nombre");
        String clave = body.get("clave");

        Map<String, Object> response = new HashMap<>();
        usuario_entidad u = usuarioNegocio.login(nombre, clave);

        if (u != null) {
            response.put("success", true);
            response.put("idusuario", u.getIdusuario());
            response.put("nombre", u.getNombre());
        } else {
            response.put("success", false);
        }

        return response;
    }
}
