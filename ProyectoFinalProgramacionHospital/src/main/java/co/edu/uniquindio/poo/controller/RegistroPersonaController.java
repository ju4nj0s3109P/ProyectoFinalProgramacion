package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistroPersonaController {

    @FXML
    private ComboBox<String> cmbTipoPersona;

    @FXML
    private TextField txtId, txtNombre, txtTelefono, txtEdad, txtEspecialidad;

    @FXML
    private Label lblEdad, lblEspecialidad;

    @FXML
    private Button btnRegistrar;

    private Administrador administrador;
    private Hospital hospital;
    private LoginController loginController;

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @FXML
    public void initialize() {
        cmbTipoPersona.getItems().addAll("Paciente", "Médico", "Administrador");
        cmbTipoPersona.setOnAction(e -> actualizarCampos());
        actualizarCampos();
    }

    private void actualizarCampos() {
        String tipo = cmbTipoPersona.getValue();

        boolean esPaciente = "Paciente".equals(tipo);
        boolean esMedico = "Médico".equals(tipo);

        lblEdad.setVisible(esPaciente);
        txtEdad.setVisible(esPaciente);

        lblEspecialidad.setVisible(esMedico);
        txtEspecialidad.setVisible(esMedico);
    }


    @FXML
    public void registrar() {
        String tipo = cmbTipoPersona.getValue();
        String id = txtId.getText();
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();

        if (tipo == null || id.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta("Todos los campos obligatorios deben estar llenos.");
            return;
        }

        Persona persona = null;

        switch (tipo) {
            case "Paciente":
                try {
                    int edad = Integer.parseInt(txtEdad.getText());
                    Paciente paciente = new Paciente(id, nombre, telefono, edad);
                    administrador.registroPaciente(paciente);
                    persona = paciente;
                } catch (NumberFormatException e) {
                    mostrarAlerta("La edad debe ser un número válido.");
                    return;
                }
                break;
            case "Médico":
                String especialidad = txtEspecialidad.getText();
                if (especialidad.isEmpty()) {
                    mostrarAlerta("Debes ingresar una especialidad.");
                    return;
                }
                Medico medico = new Medico(id, nombre, telefono, id, especialidad);
                administrador.registroMedico(medico);
                persona = medico;
                break;
            case "Administrador":
                Administrador admin = new Administrador(id, nombre, telefono,hospital);
                administrador.registroAdministrador(admin);
                persona = admin;
                break;
        }

        mostrarAlerta("Registro exitoso.");
        loginController.personaRegistrada(persona);
        cerrarVentana();
    }


    public void personaRegistrada(Persona persona) {
        if (persona != null) {
            loginController.navegarSegunTipo(persona);
        }
    }




    private void cerrarVentana() {
        Stage stage = (Stage) btnRegistrar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
