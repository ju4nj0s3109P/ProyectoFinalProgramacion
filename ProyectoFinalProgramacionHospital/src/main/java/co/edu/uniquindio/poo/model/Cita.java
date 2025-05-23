package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {

    private String idCita;
    private EstadoCita estado;
    private LocalDate fecha;
    private LocalTime hora;
    private Paciente paciente;

    public Cita(String idCita, EstadoCita estado, LocalDate fecha, LocalTime hora, Paciente paciente) {
        this.idCita = idCita;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
    }


    // Getters y Setters
    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "idCita='" + idCita + '\'' +
                ", estado=" + estado +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", paciente=" + paciente +
                '}';
    }
}
