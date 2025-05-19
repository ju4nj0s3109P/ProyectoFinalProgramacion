package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PacienteTest {

    private Paciente paciente;

    @BeforeEach
    public void setUp() {
        paciente = new Paciente("123", "Laura", "3124567890", 30);
    }

    @Test
    public void testSolicitudCita() {
        Cita cita = new Cita("c1", EstadoCita.PENDIENTE, LocalDate.now(), LocalTime.of(10, 0));
        paciente.solicitudCita(cita);

        assertEquals(1, paciente.getCitas().size());
        assertEquals(cita, paciente.getCitas().get(0));
    }

    @Test
    public void testCancelacionCitaExitosa() {
        Cita cita = new Cita("c2", EstadoCita.PENDIENTE, LocalDate.now(), LocalTime.of(11, 0));
        paciente.solicitudCita(cita);

        boolean cancelada = paciente.cancelacionCita("c2");

        assertTrue(cancelada);
        assertEquals(EstadoCita.CANCELADA, cita.getEstado());
    }

    @Test
    public void testCancelacionCitaFallida() {
        boolean cancelada = paciente.cancelacionCita("inexistente");
        assertFalse(cancelada);
    }

    @Test
    public void testConsultaHistorialVacio() {
        String historial = paciente.consultaHistorial();

        assertTrue(historial.contains("No hay diagnósticos registrados"));
        assertTrue(historial.contains("No hay tratamientos registrados"));
    }

    @Test
    public void testNotificacion() {
        // Este test verifica que no arroja excepción
        assertDoesNotThrow(() -> paciente.notificar("Tienes una nueva cita"));
    }

    @Test
    public void testActualizacionDatos() {
        paciente.actualizacionDatos("Maria", "3001112233", 28);

        assertEquals("Maria", paciente.getNombre());
        assertEquals("3001112233", paciente.getTelefono());
        assertEquals(28, paciente.getEdad());
    }
}
