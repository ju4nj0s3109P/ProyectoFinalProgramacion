package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class PacienteController {

    @FXML
    private Label lblNombrePaciente;

    @FXML
    private Button btnSolicitarCita;

    @FXML
    private Button btnCancelarCita;

    @FXML
    private Button btnVerHistorial;

    @FXML
    private TextArea txtMostrarHistorial;

    private Paciente paciente;

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        lblNombrePaciente.setText("Bienvenido: " + paciente.getNombre());
    }

    @FXML
    public void solicitudCita() {
        try {
            // Crea una cita con valores de ejemplo (en versión final puede abrir un formulario)
            String idCita = String.valueOf(paciente.getCitas().size());
            LocalDate fecha = LocalDate.now().plusDays(1);
            LocalTime hora = LocalTime.of(10, 0);
            Cita cita = new Cita(idCita, EstadoCita.PENDIENTE, fecha, hora,paciente);

            paciente.solicitudCita(cita);
            mostrarMensaje("Cita solicitada para el " + fecha + " a las " + hora +" ID Cita " + idCita);
        } catch (Exception e) {
            mostrarMensaje("No se pudo solicitar la cita.");
        }
    }

    @FXML
    public void cancelacionCita() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cancelar Cita");
        dialog.setHeaderText("Ingrese el ID de la cita a cancelar:");
        dialog.setContentText("ID de la cita:");

        dialog.showAndWait().ifPresent(idCita -> {
            boolean cancelada = paciente.cancelacionCita(idCita);
            if (cancelada) {
                mostrarMensaje("Cita cancelada con éxito.");
            } else {
                mostrarMensaje("No se encontró una cita con ese ID.");
            }
        });
    }

    @FXML
    public void consultaHistorial() {
        StringBuilder resultado = new StringBuilder();

        resultado.append("Diagnósticos:\n");
        for (Diagnostico d : paciente.getHistorialMedico().diagnosticos()) {
            resultado.append("- ").append(d.descripcion()).append(" (").append(d.fecha()).append(")\n");
        }

        resultado.append("\nTratamientos:\n");
        for (Tratamiento t : paciente.getHistorialMedico().tratamientos()) {
            resultado.append("- ").append(t.instrucciones()).append(" (")
                    .append(t.diaInicio()).append(" a ").append(t.diaFin()).append(")\n");
        }

        txtMostrarHistorial.setText(resultado.toString());
    }

    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
