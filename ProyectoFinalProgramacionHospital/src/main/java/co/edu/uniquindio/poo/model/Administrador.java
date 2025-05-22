package co.edu.uniquindio.poo.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Administrador extends Persona {

    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Horario> horarios;
    private List<Sala> salas;
    private List<Administrador> administradores;
    private Hospital hospital;

    public Administrador(String id, String nombre, String telefono, Hospital hospital) {
        super(id, nombre, telefono);
        this.medicos = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.horarios = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.hospital = hospital;
    }



    public void registroPaciente(Paciente paciente) {
        pacientes.add(paciente);

    }
    public void registroAdministrador(Administrador administrador) {
        administradores.add(administrador);

    }


    public void registroMedico(Medico medico) {
        medicos.add(medico);

    }



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



    public boolean eliminacionPersona(String id) {
        return medicos.removeIf(m -> m.getId().equals(id)) ||
                pacientes.removeIf(p -> p.getId().equals(id));
    }


    public void gestionSala(Sala sala) {
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).numero() == sala.numero()) {
                salas.set(i, sala);
                return;
            }
        }
        salas.add(sala);
    }


    public void gestionHorario(Medico medico, Horario horario) {
        medico.getHorarios().add(horario);
        horarios.add(horario);
    }


    public boolean disponibilidadMedico(Medico medico, LocalDate fecha) {
        return medico.estaDisponibleEnFecha(fecha);
    }


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


    public String reporteCitas() {
        String resultado = "Reporte de Citas:\n";
        for (Paciente p : pacientes) {
            for (Cita c : p.getCitas()) {
                resultado += "Paciente: " + p.getNombre() + "|idCita: "+ c.getIdCita()+ " | Fecha: " + c.getFecha() + " | Estado: " + c.getEstado() + "\n";
            }
        }
        return resultado;
    }


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

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    @Override
    public String mostrarRol() {
        return "Administrador";
    }
}

