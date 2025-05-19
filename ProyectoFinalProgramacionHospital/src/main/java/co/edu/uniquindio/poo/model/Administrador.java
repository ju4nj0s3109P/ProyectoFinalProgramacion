package co.edu.uniquindio.poo.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Administrador extends Persona {

    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Horario> horarios;
    private List<Sala> salas;

    public Administrador(String id, String nombre, String telefono) {
        super(id, nombre, telefono);
        this.medicos = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.horarios = new ArrayList<>();
        this.salas = new ArrayList<>();
    }

    // Registrar paciente
    public void registroPaciente(Paciente paciente) {
        pacientes.add(paciente);
        System.out.println("Paciente registrado: " + paciente.getNombre());
    }

    // Registrar médico
    public void registroMedico(Medico medico) {
        medicos.add(medico);
        System.out.println("Médico registrado: " + medico.getNombre());
    }

    // Actualizar paciente
        public boolean actualizacionPaciente(String id, String nuevoNombre, String nuevoTelefono, int nuevaEdad) {
            for (Paciente p : pacientes) {
                if (p.getId().equals(id)) {
                    p.setNombre(nuevoNombre);
                    p.setTelefono(nuevoTelefono);
                    p.setEdad(nuevaEdad);
                    return true;

                }

            }
            return false;
        }

    // Actualizar médico
    public boolean actualizacionMedico(String id, String nuevoNombre, String nuevoTelefono, String nuevaEspecialidad) {
        for (Medico m : medicos) {
            if (m.getId().equals(id)) {
                m.setNombre(nuevoNombre);
                m.setTelefono(nuevoTelefono);
                m.setEspecialidad(nuevaEspecialidad);
                return true;
            }
        }
        return false;
    }


    // Eliminación de Persona
    public boolean eliminacionPersona(String id) {
        return medicos.removeIf(m -> m.getId().equals(id)) ||
                pacientes.removeIf(p -> p.getId().equals(id));
    }

    // Gestión de salas (agregar o actualizar)
    public void gestionSala(Sala sala) {
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).numero() == sala.numero()) {
                salas.set(i, sala); // reemplazar
                return;
            }
        }
        salas.add(sala);
    }

    // Gestión de horario para un médico
    public void gestionHorario(Medico medico, Horario horario) {
        medico.getHorarios().add(horario);
        horarios.add(horario);
    }

    // Verificar disponibilidad de médico en una fecha
    public boolean disponibilidadMedico(Medico medico, LocalDate fecha) {
        return medico.estaDisponibleEnFecha(fecha);
    }

    // Asignación simple de pacientes a médicos según disponibilidad
    public void asignacionPacientes() {
        for (Paciente p : pacientes) {
            for (Cita c : p.getCitas()) {
                if (c.getEstado() == EstadoCita.PENDIENTE) {
                    for (Medico m : medicos) {
                        if (disponibilidadMedico(m, c.getFecha())) {
                            m.asignarCita(c); // suponiendo que existe
                            break;
                        }
                    }
                }
            }
        }
    }

    // Reporte de todas las citas
    public String reporteCitas() {
        String resultado = "Reporte de Citas:\n";
        for (Paciente p : pacientes) {
            for (Cita c : p.getCitas()) {
                resultado += "Paciente: " + p.getNombre() + "|idCita: "+ c.getIdCita()+ " | Fecha: " + c.getFecha() + " | Estado: " + c.getEstado() + "\n";
            }
        }
        return resultado;
    }

    // Reporte de ocupación de salas
    public String reporteOcupacion() {
        String resultado = "Reporte de Ocupación de Salas:\n";
        for (Sala s : salas) {
            resultado += "Sala " + s.numero() + ": " + (s.disponible() ? "Disponible" : "Ocupada") + "\n";
        }
        return resultado;
    }


    private List<Persona> getTodasLasPersonas() {
        List<Persona> todas = new ArrayList<>();
        todas.addAll(medicos);
        todas.addAll(pacientes);
        return todas;
    }

    // Getters de las listas (opcional para pruebas o GUI)
    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public List<Sala> getSalas() {
        return salas;
    }


    @Override
    public String mostrarRol() {
        return "Administrador";
    }
}

