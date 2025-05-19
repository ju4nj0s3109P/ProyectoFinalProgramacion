package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicoTest {

    private Medico medico;
    private Paciente paciente;

    @BeforeEach
    public void setUp() {
        medico = new Medico("m1", "Dr. Ruiz", "3112223344", "med001", "Pediatría");
        paciente = new Paciente("p1", "Andrea", "3100001111", 27);
    }

    @Test
    public void testRegistroDiagnostico() {
        medico.registroDiagnostico(paciente, "Resfriado común", LocalDate.now());

        List<Diagnostico> diagnosticos = paciente.getHistorialMedico().diagnosticos();
        assertEquals(1, diagnosticos.size());
        assertEquals("Resfriado común", diagnosticos.get(0).descripcion());
    }

    @Test
    public void testRegistroTratamiento() {
        medico.registroTratamiento(paciente, "Jarabe cada 8h", LocalDate.now(), LocalDate.now().plusDays(5));

        List<Tratamiento> tratamientos = paciente.getHistorialMedico().tratamientos();
        assertEquals(1, tratamientos.size());
        assertEquals("Jarabe cada 8h", tratamientos.get(0).instrucciones());
    }

    @Test
    public void testHistorialPaciente() {
        String historial = medico.historialPaciente(paciente);
        assertTrue(historial.contains("Diagnósticos"));
        assertTrue(historial.contains("Tratamientos"));
    }

    @Test
    public void testAdminHorario() {
        Horario horario = new Horario(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));
        medico.adminHorario(horario);

        assertTrue(medico.getHorarios().contains(horario));
    }

    @Test
    public void testAsignarCita() {
        Cita cita = new Cita("c10", EstadoCita.PENDIENTE, LocalDate.now(), LocalTime.of(10, 0));
        medico.asignarCita(cita);

        assertTrue(medico.getCitasAsignadas().contains(cita));
    }

    @Test
    public void testVerCitasPorFecha() {
        Cita citaHoy = new Cita("c20", EstadoCita.PENDIENTE, LocalDate.now(), LocalTime.of(9, 0));
        Cita citaOtroDia = new Cita("c21", EstadoCita.PENDIENTE, LocalDate.now().plusDays(1), LocalTime.of(10, 0));

        medico.asignarCita(citaHoy);
        medico.asignarCita(citaOtroDia);

        List<Cita> citasHoy = medico.verCitasPorFecha(LocalDate.now());
        assertEquals(1, citasHoy.size());
        assertEquals("c20", citasHoy.get(0).getIdCita());
    }

    @Test
    public void testEstaDisponibleEnFecha() {
        Horario horario = new Horario(LocalDate.now().getDayOfWeek(), LocalTime.of(8, 0), LocalTime.of(12, 0));
        medico.adminHorario(horario);

        assertTrue(medico.estaDisponibleEnFecha(LocalDate.now()));
        assertFalse(medico.estaDisponibleEnFecha(LocalDate.now().plusDays(3))); // Día no agendado
    }

    @Test
    public void testNotificar() {
        assertDoesNotThrow(() -> medico.notificar("Reunión a las 3pm"));
    }
}
