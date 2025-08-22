package tarnsformwash.backend.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping ("/ventas")
public class ventas_controlador {
    @Autowired
    private ventas_negocio ventas_negocio;

    @PostMapping ("/agregar")
    public Venta agregarVenta(@RequestBody  Venta venta) {
        return ventas_negocio.agregarVenta(venta);
    }

}
