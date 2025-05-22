package co.edu.uniquindio.poo.model;

import java.util.List;

public class Hospital {

    private String nombre;
    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Administrador> administradores;


    public Hospital(String nombre, List<Medico> medicos, List<Paciente> pacientes, List<Administrador> administradores) {
        this.nombre = nombre;
        this.medicos = medicos;
        this.pacientes = pacientes;
        this.administradores = administradores;
    }

    public Persona verificarAcceso(String id, String nombre) {
        for (Paciente p : pacientes)
            if (p.getId().equals(id) && p.getNombre().equalsIgnoreCase(nombre))
                return p;

        for (Medico m : medicos)
            if (m.getId().equals(id) && m.getNombre().equalsIgnoreCase(nombre))
                return m;

        for (Administrador a : administradores)
            if (a.getId().equals(id) && a.getNombre().equalsIgnoreCase(nombre))
                return a;

        return null;
    }


    private List<Persona> getTodasLasPersonas() {
        List<Persona> todas = new java.util.ArrayList<>();
        todas.addAll(medicos);
        todas.addAll(pacientes);
        todas.addAll(administradores);
        return todas;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }
}
