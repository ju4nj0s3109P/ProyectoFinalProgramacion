package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdministradorTest {

    private Administrador admin;
    private Medico medico;
    private Paciente paciente;

    @BeforeEach
    public void setUp() {
        admin = new Administrador("a1", "Admin", "3101234567");
        medico = new Medico("m1", "Dr. Luna", "3201112233", "med123", "General");
        paciente = new Paciente("p1", "Carlos", "3009876543", 40);
    }

    @Test
    public void testRegistroPaciente() {
        admin.registroPaciente(paciente);
        assertTrue(admin.getPacientes().contains(paciente));
    }

    @Test
    public void testRegistroMedico() {
        admin.registroMedico(medico);
        assertTrue(admin.getMedicos().contains(medico));
    }

    @Test
    public void testActualizacionPaciente() {

        admin.registroPaciente(paciente);
        System.out.println("ID del paciente registrado: " + paciente.getId());
         boolean resultado = admin.actualizacionPaciente("p1", "Pedro", "3001234567", 35);
        assertTrue(admin.getPacientes().contains(paciente));
        assertTrue(resultado);
        assertEquals("Pedro", paciente.getNombre());
        assertEquals(35, paciente.getEdad());
    }

    @Test
    public void testActualizacionMedico() {
        admin.registroMedico(medico);
        boolean actualizado = admin.actualizacionMedico("m1", "Dr. Marta", "3000000000", "Internista");

        assertTrue(actualizado);
        assertEquals("Dr. Marta", medico.getNombre());
        assertEquals("Internista", medico.getEspecialidad());
    }

    @Test
    public void testEliminacionPersona() {
        admin.registroPaciente(paciente);
        admin.registroMedico(medico);

        boolean eliminadoP = admin.eliminacionPersona("p1");
        boolean eliminadoM = admin.eliminacionPersona("m1");

        assertTrue(eliminadoP);
        assertTrue(eliminadoM);
        assertFalse(admin.getPacientes().contains(paciente));
        assertFalse(admin.getMedicos().contains(medico));
    }

    @Test
    public void testGestionSala() {
        Sala sala = new Sala(101, true);
        admin.gestionSala(sala);
        assertTrue(admin.getSalas().contains(sala));
    }

    @Test
    public void testGestionHorario() {
        Horario horario = new Horario(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));
        admin.registroMedico(medico);

        admin.gestionHorario(medico, horario);
        assertTrue(medico.getHorarios().contains(horario));
        assertTrue(admin.getHorarios().contains(horario));
    }

    @Test
    public void testDisponibilidadMedico() {
        Horario horario = new Horario(LocalDate.now().getDayOfWeek(), LocalTime.of(8, 0), LocalTime.of(12, 0));
        medico.adminHorario(horario);
        admin.registroMedico(medico);

        assertTrue(admin.disponibilidadMedico(medico, LocalDate.now()));
        assertFalse(admin.disponibilidadMedico(medico, LocalDate.now().plusDays(2)));
    }

    @Test
    public void testAsignacionPacientes() {
        admin.registroPaciente(paciente);
        admin.registroMedico(medico);

        Horario horario = new Horario(LocalDate.now().getDayOfWeek(), LocalTime.of(8, 0), LocalTime.of(12, 0));
        medico.adminHorario(horario);

        Cita cita = new Cita("c1", EstadoCita.PENDIENTE, LocalDate.now(), LocalTime.of(10, 0));
        paciente.solicitudCita(cita);

        admin.asignacionPacientes();

        assertTrue(medico.getCitasAsignadas().contains(cita));
    }

    @Test
    public void testReporteCitas() {
        admin.registroPaciente(paciente);
        Cita cita= new Cita("c5", EstadoCita.PENDIENTE, LocalDate.now(), LocalTime.of(9, 0));
        paciente.solicitudCita(cita);

        String reporte = admin.reporteCitas();
        assertTrue(reporte.contains("Paciente: Carlos"));
        assertTrue(reporte.contains("c5"));
    }

    @Test
    public void testReporteOcupacion() {
        Sala salaLibre = new Sala(201, true);
        Sala salaOcupada = new Sala(202, false);
        admin.gestionSala(salaLibre);
        admin.gestionSala(salaOcupada);

        String reporte = admin.reporteOcupacion();

        assertTrue(reporte.contains("Sala 201: Disponible"));
        assertTrue(reporte.contains("Sala 202: Ocupada"));
    }
}
