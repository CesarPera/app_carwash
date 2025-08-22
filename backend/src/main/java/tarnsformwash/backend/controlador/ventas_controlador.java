package tarnsformwash.backend.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tarnsformwash.backend.entidad.ventas_entidad;
import tarnsformwash.backend.negocio.ventas_negocio;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping ("/ventas")
@CrossOrigin(origins = "*")
public class ventas_controlador {
    @Autowired
    private ventas_negocio ventas_negocio;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping
    public List<ventas_entidad> listar() {
        return ventas_negocio.listarTodos();
    }

    @PostMapping
    public ventas_entidad crear(@RequestBody Map<String, String> body) throws Exception {
        ventas_entidad entidad = new ventas_entidad();
        entidad.setIdservicioOtorgado(body.get("idservicio_otorgado"));
        entidad.setFecha(sdf.parse(body.get("fecha")));
        entidad.setDocumento(body.get("documento"));
        entidad.setCosto(Float.parseFloat(body.get("costo")));
        entidad.setObservacion(body.get("observacion"));
        entidad.setIdservicio(body.get("idservicio"));
        entidad.setPlaca(body.get("placa"));
        entidad.setDniPersona(body.get("dni_persona"));
        return ventas_negocio.guardar(entidad);
    }

    @PutMapping
    public ventas_entidad actualizar(@RequestBody Map<String, String> body) throws Exception {
        String id = body.get("idservicio_otorgado");
        ventas_entidad entidad = ventas_negocio.buscarPorId(id);

        if (entidad != null) {
            entidad.setFecha(sdf.parse(body.get("fecha")));
            entidad.setDocumento(body.get("documento"));
            entidad.setCosto(Float.parseFloat(body.get("costo")));
            entidad.setObservacion(body.get("observacion"));
            entidad.setIdservicio(body.get("idservicio"));
            entidad.setPlaca(body.get("placa"));
            entidad.setDniPersona(body.get("dni_persona"));
            return ventas_negocio.guardar(entidad);
        }
        return null;
    }

    @DeleteMapping
    public String eliminar(@RequestBody Map<String, String> body) {
        String id = body.get("idservicio_otorgado");
        boolean eliminado = ventas_negocio.eliminar(id);
        if (eliminado) {
            return "Servicio otorgado con ID " + id + " eliminado correctamente.";
        } else {
            return "Servicio otorgado con ID " + id + " no encontrado.";
        }
    }

}
