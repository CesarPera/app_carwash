package tarnsformwash.backend.entidad;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculo")
public class vehiculo_entidad {

    @Id
    @Column(length = 18)
    private String placa;

    @Column(length = 18, nullable = false)
    private String descripcion;

    @Column(length = 18, nullable = false)
    private String fabricante;

    @Column(length = 18, nullable = false)
    private String modelo;

    @Column(length = 18, nullable = false)
    private String cliente;

    @Column(length = 8, nullable = false)
    private String dnicliente;

    @Column(length = 20, nullable = false)
    private String brevete;

    @Column(length = 100)
    private String observacion;

    // ===== Getters y Setters =====
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDnicliente() {
        return dnicliente;
    }

    public void setDnicliente(String dnicliente) {
        this.dnicliente = dnicliente;
    }

    public String getBrevete() {
        return brevete;
    }

    public void setBrevete(String brevete) {
        this.brevete = brevete;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}

