package tarnsformwash.backend.entidad;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "personal")
public class personal_entidad {

    @Id
    @Column(name = "dni_persona", length = 8)
    private String dniPersona;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 80, nullable = false)
    private String direccion;

    @Column(length = 9, nullable = false)
    private String movil;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_ingreso", nullable = false)
    private Date fechaIngreso;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(nullable = false)
    private Float sueldo;

    public String getDniPersona() {
        return dniPersona;
    }

    public void setDniPersona(String dniPersona) {
        this.dniPersona = dniPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getSueldo() {
        return sueldo;
    }

    public void setSueldo(Float sueldo) {
        this.sueldo = sueldo;
    }
}

