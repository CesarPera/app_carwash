package tarnsformwash.backend.negocio;

import org.springframework.beans.factory.annotation.Autowired;

public class ventas_negocio {
    @Autowired
    private ventas_repositorio ventas_repositorio;

    public Venta agregarVenta(Venta venta) {
        if (venta.getCosto() != null && venta.getCosto() < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo");
        }
        if (venta.getDni() != null && venta.getDni().length() != 8) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos");
        }
        return ventas_repositorio.save(venta);
    }

    public Venta editarVenta(Long id, Venta nuevaVenta) {
        return ventas_repositorio.findById(id).map(venta -> {
            venta.setFecha(nuevaVenta.getFecha());
            venta.setDocumento(nuevaVenta.getDocumento());
            venta.setCosto(nuevaVenta.getCosto());
            venta.setIdServicio(nuevaVenta.getIdServicio());
            venta.setPlaca(nuevaVenta.getPlaca());
            venta.setDni(nuevaVenta.getDni());
            venta.setObservacion(nuevaVenta.getObservacion());
            return ventaRepository.save(venta);
        }).orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public void eliminarVenta(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("No existe una venta con ID " + id);
        }
        ventas_repositorio.deleteById(id);
    }

    // 📋 Listar
    public List<Venta> listarVentas() {
        return ventas_repositorio.findAll();
    }
}
}
