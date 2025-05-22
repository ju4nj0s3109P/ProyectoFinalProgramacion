package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AdministradorController {

    @FXML
    private Label lblNombreAdmin;

    @FXML
    private Button btnRegistrarPaciente;

    @FXML
    private Button btnRegistrarMedico;

    @FXML
    private Button btnReporteCita;

    @FXML
    private Button btnReporteOcupacion;

    @FXML
    private TextArea txtResultadoReporte;

    private Administrador administrador;
    private Hospital hospital;
    private LoginController loginController;

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }


    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
        lblNombreAdmin.setText("Bienvenido Administrador: " + administrador.getNombre());
    }

    @FXML
    public void registroPaciente() {
        abrirVistaRegistro();
    }

    @FXML
    public void registroMedico() {
        abrirVistaRegistro();
    }

    private void abrirVistaRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/view/RegistroPersona.fxml"));
            Scene scene = new Scene(loader.load());

            RegistroPersonaController controller = loader.getController();
            controller.setHospital(administrador.getHospital());
            controller.setAdministrador(administrador);

            if (loginController != null) {
                controller.setLoginController(loginController);
            }

            Stage stage = new Stage();
            stage.setTitle("Registrar Persona");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al abrir la ventana de registro.");
        }
    }

    @FXML
    public void reporteCitas() {
        StringBuilder resultado = new StringBuilder("Reporte de Citas:\n");

        for (Paciente paciente : administrador.getPacientes()) {
            for (Cita cita : paciente.getCitas()) {
                resultado.append("Paciente: ").append(paciente.getNombre())
                        .append(" | Fecha: ").append(cita.getFecha())
                        .append(" | Hora: ").append(cita.getHora())
                        .append(" | Estado: ").append(cita.getEstado())
                        .append("\n");
            }
        }

        txtResultadoReporte.setText(resultado.toString());
    }

    @FXML
    public void reporteOcupacion() {
        StringBuilder resultado = new StringBuilder("Ocupación de Salas:\n");

        for (Sala sala : administrador.getSalas()) {
            resultado.append("Sala #").append(sala.numero())
                    .append(" - ").append(sala.disponible() ? "Disponible" : "Ocupada")
                    .append("\n");
        }

        txtResultadoReporte.setText(resultado.toString());
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
