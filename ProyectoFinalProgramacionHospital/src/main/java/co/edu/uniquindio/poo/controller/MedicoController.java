package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class MedicoController {

    @FXML
    private Label lblNombreMedico;

    @FXML
    private Button btnHistorialPaciente;

    @FXML
    private Button btnRegistrarDiagnostico;

    @FXML
    private Button btnRegistrarTratamiento;

    @FXML
    private ListView<Cita> listacitas;

    @FXML
    private TextArea txtHistorial;

    private Medico medico;

    public void setMedico(Medico medico) {
        this.medico = medico;
        lblNombreMedico.setText("Bienvenido doctor: " + medico.getNombre());


        actualizarListaCitas();
    }

    private void actualizarListaCitas() {
        listacitas.getItems().clear();


        List<Cita> citas = medico.getCitasAsignadas();
        listacitas.getItems().addAll(citas);
    }

    @FXML
    public void historialPaciente() {
        Cita citaSeleccionada = listacitas.getSelectionModel().getSelectedItem();

        if (citaSeleccionada == null) {
            mostrarMensaje("Selecciona una cita para ver el historial del paciente.");
            return;
        }

        Paciente paciente = citaSeleccionada.getPaciente(); // necesitas asociar el paciente a la cita
        StringBuilder resultado = new StringBuilder();

        resultado.append("Historial de ").append(paciente.getNombre()).append(":\n\n");

        resultado.append("Diagnósticos:\n");
        for (Diagnostico d : paciente.getHistorialMedico().diagnosticos()) {
            resultado.append("- ").append(d.descripcion()).append(" (").append(d.fecha()).append(")\n");
        }

        resultado.append("\nTratamientos:\n");
        for (Tratamiento t : paciente.getHistorialMedico().tratamientos()) {
            resultado.append("- ").append(t.instrucciones()).append(" (")
                    .append(t.diaInicio()).append(" a ").append(t.diaFin()).append(")\n");
        }

        txtHistorial.setText(resultado.toString());
    }

    @FXML
    public void registroDiagnostico() {
        Cita citaSeleccionada = listacitas.getSelectionModel().getSelectedItem();

        if (citaSeleccionada == null) {
            mostrarMensaje("Selecciona una cita para registrar el diagnóstico.");
            return;
        }

        Paciente paciente = citaSeleccionada.getPaciente();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo Diagnóstico");
        dialog.setHeaderText("Registrar diagnóstico para " + paciente.getNombre());
        dialog.setContentText("Descripción:");

        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(descripcion -> {

            medico.registroDiagnostico(paciente, descripcion, LocalDate.now());
            mostrarMensaje("Diagnóstico registrado.");
        });
    }

    @FXML
    public void registroTratamiento() {
        Cita citaSeleccionada = listacitas.getSelectionModel().getSelectedItem();

        if (citaSeleccionada == null) {
            mostrarMensaje("Selecciona una cita para registrar el tratamiento.");
            return;
        }

        Paciente paciente = citaSeleccionada.getPaciente();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo Tratamiento");
        dialog.setHeaderText("Registrar tratamiento para " + paciente.getNombre());
        dialog.setContentText("Instrucciones:");

        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(instrucciones -> {
            medico.registroTratamiento(paciente, instrucciones,LocalDate.now(), LocalDate.now().plusDays(7));
            mostrarMensaje("Tratamiento registrado.");
        });
    }

    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
