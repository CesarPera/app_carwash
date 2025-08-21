package tarnsformwash.backend.entidad;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class usuario_entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuario;

    @Column(nullable = false, length = 15)
    private String nombre;

    @Column(nullable = false, length = 10)
    private String clave;


    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}