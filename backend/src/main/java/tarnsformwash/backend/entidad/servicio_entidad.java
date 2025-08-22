package tarnsformwash.backend.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicio")
public class servicio_entidad {
    @Id
    private String idservicio;

    private String descripcion;
    private float costo;
    private String observacion;

    // 🔹 Getters y Setters
    public String getIdservicio() {
        return idservicio;
    }

    public void setIdservicio(String idservicio) {
        this.idservicio = idservicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public String toString() {
        return "servicio_entidad{" +
                "idservicio='" + idservicio + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costo=" + costo +
                ", observacion='" + observacion + '\'' +
                '}';
    }
}