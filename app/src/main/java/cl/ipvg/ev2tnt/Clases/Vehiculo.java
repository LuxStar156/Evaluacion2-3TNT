package cl.ipvg.ev2tnt.Clases;

public class Vehiculo {
    private String nombre;
    private String apellido;
    private String matricula;
    private String marca;
    private String linea;
    private String latitud;
    private String longitud;
    private String direccion;

    public Vehiculo(){
      //contructor para DataSnapshot
    }

    public Vehiculo(String nombre, String apellido, String matricula, String marca, String linea, String latitud, String longitud, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
        this.marca = marca;
        this.linea = linea;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
