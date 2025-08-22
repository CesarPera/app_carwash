package tarnsformwash.backend.controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tarnsformwash.backend.entidad.vehiculo_entidad;
import tarnsformwash.backend.negocio.vehiculo_negocio;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehiculos")
public class vehiculo_controlador {

    @Autowired
    private vehiculo_negocio vehiculo_negocio;

    @GetMapping
    public List<vehiculo_entidad> listar() {
        return vehiculo_negocio.listarTodos();
    }

    @PostMapping
    public vehiculo_entidad crear(@RequestBody Map<String, String> body) {
        vehiculo_entidad vehiculo = new vehiculo_entidad();
        vehiculo.setPlaca(body.get("placa"));
        vehiculo.setDescripcion(body.get("descripcion"));
        vehiculo.setFabricante(body.get("fabricante"));
        vehiculo.setModelo(body.get("modelo"));
        vehiculo.setCliente(body.get("cliente"));
        vehiculo.setDnicliente(body.get("dnicliente"));
        vehiculo.setBrevete(body.get("brevete"));
        vehiculo.setObservacion(body.get("observacion"));

        return vehiculo_negocio.guardar(vehiculo);
    }

    @PutMapping
    public vehiculo_entidad actualizar(@RequestBody Map<String, String> body) {
        String placa = body.get("placa");
        vehiculo_entidad vehiculo = vehiculo_negocio.buscarPorPlaca(placa);

        if (vehiculo != null) {
            vehiculo.setDescripcion(body.get("descripcion"));
            vehiculo.setFabricante(body.get("fabricante"));
            vehiculo.setModelo(body.get("modelo"));
            vehiculo.setCliente(body.get("cliente"));
            vehiculo.setDnicliente(body.get("dnicliente"));
            vehiculo.setBrevete(body.get("brevete"));
            vehiculo.setObservacion(body.get("observacion"));
            return vehiculo_negocio.guardar(vehiculo);
        }
        return null; // o lanzar excepción personalizada
    }

    @DeleteMapping
    public String eliminar(@RequestBody Map<String, String> body) {
        String placa = body.get("placa");
        boolean eliminado = vehiculo_negocio.eliminar(placa);
        if (eliminado) {
            return "Vehículo con placa " + placa + " eliminado correctamente.";
        } else {
            return "Vehículo con placa " + placa + " no encontrado.";
        }
    }
}
