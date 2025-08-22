package tarnsformwash.backend.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tarnsformwash.backend.entidad.personal_entidad;
import tarnsformwash.backend.entidad.vehiculo_entidad;
import tarnsformwash.backend.negocio.personal_negocio;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/personal")
public class personal_controlador {

    @Autowired
    private personal_negocio personal_negocio;

    @GetMapping
    public List<personal_entidad> listar() {
        return personal_negocio.listarTodos();
    }

    @PostMapping
    public personal_entidad crear(@RequestBody Map<String, String> body) throws Exception{
        personal_entidad personal = new personal_entidad();
        personal.setDniPersona(body.get("dniPersona"));
        personal.setNombre(body.get("nombre"));
        personal.setDireccion(body.get("direccion"));
        personal.setMovil(body.get("movil"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        personal.setFechaIngreso(sdf.parse(body.get("fechaIngreso")));

        personal.setEmail(body.get("email"));
        personal.setSueldo(Float.parseFloat(body.get("sueldo")));

        return personal_negocio.guardar(personal);
    }

    @PutMapping
    public personal_entidad actualizar(@RequestBody Map<String, String> body) throws Exception {
        String dni = body.get("dniPersona");
        Optional<personal_entidad> existente = personal_negocio.buscarPorId(dni);

        if (existente.isPresent()) {
            personal_entidad personal = existente.get();

            personal.setNombre(body.get("nombre"));
            personal.setDireccion(body.get("direccion"));
            personal.setMovil(body.get("movil"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            personal.setFechaIngreso(sdf.parse(body.get("fechaIngreso")));

            personal.setEmail(body.get("email"));
            personal.setSueldo(Float.parseFloat(body.get("sueldo")));

            return personal_negocio.guardar(personal);
        } else {
            return null; // o lanzar excepción personalizada
        }
    }

    @DeleteMapping
    public String eliminar(@RequestBody Map<String, String> body) {
        String dni = body.get("dniPersona");
        Optional<personal_entidad> existente = personal_negocio.buscarPorId(dni);

        if (existente.isPresent()) {
            personal_negocio.eliminar(dni);
            return "Personal con DNI " + dni + " eliminado correctamente.";
        } else {
            return "Personal con DNI " + dni + " no encontrado.";
        }
    }

}
