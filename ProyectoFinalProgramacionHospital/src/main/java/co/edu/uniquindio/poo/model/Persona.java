package co.edu.uniquindio.poo.model;

public abstract class Persona {
    private String id;
    private String nombre;
    private String telefono;

    public Persona(String id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getTelefono() {
        return telefono;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public abstract String mostrarRol();


    @Override
    public String toString() {
        return "Nombre: " + nombre + ", ID: " + id + ", Teléfono: " + telefono;
    }
}
