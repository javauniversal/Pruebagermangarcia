package co.zonaapp.pruebagermangarcia.Model;

/**
 * Created by hp_gergarga on 21/08/2016.
 */
public class EntEstandar {

    private int id;

    private String descripcion;

    public EntEstandar(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
