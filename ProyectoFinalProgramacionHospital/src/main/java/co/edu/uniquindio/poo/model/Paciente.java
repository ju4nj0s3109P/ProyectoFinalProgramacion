package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Paciente extends Persona implements Notificable {

    private int edad;
    private List<Cita> citas;
    private HistorialMedico historialMedico;

    public Paciente(String id, String nombre, String telefono, int edad) {
        super(id, nombre, telefono);
        this.edad = edad;
        this.citas = new ArrayList<>();
        this.historialMedico = new HistorialMedico(new ArrayList<>(), new ArrayList<>()); // Medico se asigna luego
    }

    public void registroDatos(String id, String nombre, String telefono, int edad) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        this.edad = edad;
    }

    public void actualizacionDatos(String nombre,String telefono, int edad) {
        setNombre(nombre);
        setTelefono(telefono);
        this.edad = edad;
    }

    public void solicitudCita(Cita cita) {
        citas.add(cita);
        notificar("Cita solicitada para el " + cita.getFecha() + " a las " + cita.getHora()+ "idCita: "+citas.size());
    }



    public boolean cancelacionCita(String idCita) {
        for (Cita cita : citas) {
            if (cita.getIdCita().equals(idCita) && cita.getEstado() == EstadoCita.PENDIENTE) {
                cita.setEstado(EstadoCita.CANCELADA);
                notificar("Cita con ID " + idCita + " ha sido cancelada.");
                return true;
            }
        }
        return false;
    }


    public String consultaHistorial() {
        String resultado = "Historial Médico del paciente " + getNombre() + ":\n\n";

        resultado += "Diagnósticos:\n";
        if (historialMedico.diagnosticos().isEmpty()) {
            resultado += " - No hay diagnósticos registrados.\n";
        } else {
            for (Diagnostico d : historialMedico.diagnosticos()) {
                resultado += " - " + d.fecha() + ": " + d.descripcion() + "\n";
            }
        }

        resultado += "\nTratamientos:\n";
        if (historialMedico.tratamientos().isEmpty()) {
            resultado += " - No hay tratamientos registrados.\n";
        } else {
            for (Tratamiento t : historialMedico.tratamientos()) {
                resultado += " - Del " + t.diaInicio() + " al " + t.diaFin() + ": " + t.instrucciones() + "\n";
            }
        }

        return resultado;
    }


    // Getter y Setter

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    @Override
    public void notificar(String mensaje) {
        System.out.println("Notificación para " + getNombre() + ": " + mensaje);
    }
    @Override
    public String mostrarRol() {
        return "Paciente";
    }

}
