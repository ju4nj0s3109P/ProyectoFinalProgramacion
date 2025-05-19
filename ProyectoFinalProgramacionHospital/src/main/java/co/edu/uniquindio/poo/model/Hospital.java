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

    public boolean verificarAcceso(String id, String nombre) {
        for (Persona p : getTodasLasPersonas()) {
            if (p.getId().equals(id) && p.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
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
}
