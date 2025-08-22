package tarnsformwash.backend.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tarnsformwash.backend.entidad.servicio_entidad;
import tarnsformwash.backend.negocio.servicio_negocio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicios")
@CrossOrigin(origins = "*")
public class servicio_controlador {
    @Autowired
    private servicio_negocio negocio;

    // Listar todos
    @GetMapping
    public List<servicio_entidad> listar() {
        return negocio.listarServicios();
    }

    // Buscar por id desde body
    @PostMapping("/buscar")
    public Optional<servicio_entidad> buscar(@RequestBody servicio_entidad servicio) {
        return negocio.buscarPorId(servicio.getIdservicio());
    }

    // Crear servicio
    @PostMapping
    public servicio_entidad crear(@RequestBody servicio_entidad servicio) {
        return negocio.guardarServicio(servicio);
    }

    // Actualizar servicio desde body
    @PutMapping("/actualizar")
    public servicio_entidad actualizar(@RequestBody servicio_entidad servicio) {
        return negocio.guardarServicio(servicio);
    }

    // Eliminar servicio desde body
    @DeleteMapping("/eliminar")
    public String eliminar(@RequestBody servicio_entidad servicio) {
        negocio.eliminarServicio(servicio.getIdservicio());
        return "✅ Servicio eliminado con éxito";
    }
}