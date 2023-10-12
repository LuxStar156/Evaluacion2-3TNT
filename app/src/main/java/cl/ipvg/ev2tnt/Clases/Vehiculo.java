package cl.ipvg.ev2tnt.Clases;

public class Vehiculo {
    private String nombre;
    private String matricula;
    private String marca;
    private int kmt; //Kilometraje

    public Vehiculo(String nombre, String matricula, String marca, int kmt) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.marca = marca;
        this.kmt = kmt;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setId(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getKmt() {
        return kmt;
    }

    public void setKmt(int kmt) {
        this.kmt = kmt;
    }
}
