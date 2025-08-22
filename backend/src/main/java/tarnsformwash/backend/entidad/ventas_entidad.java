package tarnsformwash.backend.entidad;



import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "servicio_otorgado")
public class ventas_entidad {

    @Id
    @Column(name = "idservicio_otorgado", length = 7)
    private String idservicioOtorgado;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false, length = 20)
    private String documento;

    @Column(nullable = false)
    private Float costo;

    @Column(nullable = false, length = 100)
    private String observacion;

    @Column(name = "idservicio", nullable = false, length = 5)
    private String idservicio;

    @Column(name = "placa", nullable = false, length = 18)
    private String placa;

    @Column(name = "dni_persona", nullable = false, length = 8)
    private String dniPersona;

    public String getIdservicioOtorgado() {
        return idservicioOtorgado;
    }

    public void setIdservicioOtorgado(String idservicioOtorgado) {
        this.idservicioOtorgado = idservicioOtorgado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getIdservicio() {
        return idservicio;
    }

    public void setIdservicio(String idservicio) {
        this.idservicio = idservicio;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDniPersona() {
        return dniPersona;
    }

    public void setDniPersona(String dniPersona) {
        this.dniPersona = dniPersona;
    }
}
