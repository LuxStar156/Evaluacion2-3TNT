package cl.ipvg.ev2tnt.Clases;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Vehiculo {
    private String id;
    private String rut;
    private String contrasena;
    private String nombre;
    private String apellido;
    private Boolean estado;
    private String matricula;
    private String marca;
    private String modelo;
    private String linea;
    private Double latitud;
    private Double longitud;
    private String direccion;

    public Vehiculo(){
      //contructor para DataSnapshot(Leer datos)
    }

    public Vehiculo(String rut,Double latitud, Double longitud){
        this.rut= rut;
        this.latitud = latitud;
        this.longitud = longitud;

    }

    public Vehiculo(String id, String rut, String contrasena,String nombre, String apellido, Boolean estado,String matricula,
                    String marca, String modelo, String linea, Double latitud, Double longitud, String direccion) {
        this.id = id;
        this.rut = rut;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estado = estado;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.linea = linea;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", this.id);
        result.put("latitud", this.latitud);
        result.put("longitud", this.longitud);

        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
