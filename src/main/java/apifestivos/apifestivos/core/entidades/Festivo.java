package apifestivos.apifestivos.core.entidades;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table(name="festivo")
public class Festivo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_festivo")
    @GenericGenerator(name = "secuencia_festivo", strategy = "increment")
    @Column(name="id") // selecciona la columna
    private int id;

    @Column(name="nombre", length = 100, unique = true)
    private String nombre;

    @Column(name="dia")
    private int dia;

    @Column(name = "mes")
    private int mes;

    @Column(name = "diaspascua")
    private int diasPascua;

    @Column(name = "idtipo")
    private int idTipo;

    public Festivo() {
    }

    public Festivo(String nombre, int dia, int mes, int diasPascua, int idTipo) {
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.diasPascua = diasPascua;
        this.idTipo = idTipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDiasPascua() {
        return diasPascua;
    }

    public void setDiasPascua(int diasPascua) {
        this.diasPascua = diasPascua;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }
}
