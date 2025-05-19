package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Medico extends Persona implements Notificable {

    private String idMedico;
    private String especialidad;
    private List<Horario> horarios;
    private List<Cita> citasAsignadas;

    public Medico(String id, String nombre, String telefono, String idMedico, String especialidad) {
        super(id, nombre, telefono);
        this.idMedico = idMedico;
        this.especialidad = especialidad;
        this.horarios = new ArrayList<>();
        this.citasAsignadas = new ArrayList<>();
    }

    // Métodos requeridos por el Administrador

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void asignarCita(Cita cita) {
        citasAsignadas.add(cita);
        notificar("Nueva cita asignada para el " + cita.getFecha() + " a las " + cita.getHora());
    }

    public boolean estaDisponibleEnFecha(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        for (Horario h : horarios) {
            if (h.dia().equals(dia)) {
                return true;
            }
        }
        return false;
    }

    // Métodos específicos del médico

    public String historialPaciente(Paciente paciente) {
        return paciente.consultaHistorial();
    }

    public void registroDiagnostico(Paciente paciente, String descripcion, LocalDate fecha) {
        paciente.getHistorialMedico().diagnosticos().add(new Diagnostico(descripcion, fecha));
        notificar("Diagnóstico registrado para el paciente " + paciente.getNombre());
    }

    public void registroTratamiento(Paciente paciente, String instrucciones, LocalDate inicio, LocalDate fin) {
        paciente.getHistorialMedico().tratamientos().add(new Tratamiento(instrucciones, inicio, fin));
        notificar("Tratamiento registrado para el paciente " + paciente.getNombre());
    }

    public void adminHorario(Horario nuevoHorario) {
        horarios.add(nuevoHorario);
        System.out.println("Horario agregado: " + nuevoHorario.dia() + " de " + nuevoHorario.horaInicio() + " a " + nuevoHorario.horaFin());
    }
    public List<Cita> verCitasPorFecha(LocalDate fecha) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita c : citasAsignadas) {
            if (c.getFecha().equals(fecha)) {
                resultado.add(c);
            }
        }
        return resultado;
    }



    @Override
    public void notificar(String mensaje) {
        System.out.println("Notificación para el Dr. " + getNombre() + ": " + mensaje);
    }

    // Getters específicos
    public String getIdMedico() {
        return idMedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public List<Cita> getCitasAsignadas() {
        return citasAsignadas;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public void setCitasAsignadas(List<Cita> citasAsignadas) {
        this.citasAsignadas = citasAsignadas;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String mostrarRol() {
        return "Medico";
    }
}
