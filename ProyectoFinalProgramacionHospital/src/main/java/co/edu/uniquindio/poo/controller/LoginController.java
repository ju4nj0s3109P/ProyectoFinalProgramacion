package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnRegistrarse;

    private Hospital hospital;
    private Administrador administrador;
    private LoginController loginController;

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @FXML
    public void verificarAcceso() {
        String nombre = txtNombre.getText();
        String id = txtId.getText();

        if (nombre.isEmpty() || id.isEmpty()) {
            mostrarAlerta("Campos vacíos"+"\n" + "Por favor, completa todos los campos.");
            return;
        }

        Persona persona = hospital.verificarAcceso(id, nombre);

        if (persona == null) {
            mostrarAlerta("Acceso denegado."+"\n" + " Credenciales incorrectas.");
        } else {
            navegarSegunTipo(persona);
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void abrirRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/view/RegistroPersona.fxml"));
            Scene scene = new Scene(loader.load());

            RegistroPersonaController controller = loader.getController();
            controller.setHospital(hospital);
            controller.setAdministrador(administrador);
            controller.setLoginController(this); // para que nos devuelva la persona registrada

            Stage stage = new Stage();
            stage.setTitle("Registro de Persona");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void personaRegistrada(Persona persona) {
        if (persona != null) {
            navegarSegunTipo(persona);
        }
    }
    public void navegarSegunTipo(Persona persona) {
        try {
            FXMLLoader loader;
            Scene scene;

            if (persona instanceof Paciente paciente) {
                loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/view/Paciente.fxml"));
                scene = new Scene(loader.load());

                PacienteController controller = loader.getController();
                controller.setPaciente(paciente);

            } else if (persona instanceof Medico medico) {
                loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/view/Medico.fxml"));
                scene = new Scene(loader.load());

                MedicoController controller = loader.getController();
                controller.setMedico(medico);

            } else if (persona instanceof Administrador admin) {
                loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/view/Administrador.fxml"));
                scene = new Scene(loader.load());

                AdministradorController controller = loader.getController();
                controller.setAdministrador(admin);
                controller.setHospital(hospital);
                controller.setLoginController(this);

            } else {
                mostrarAlerta("Error"+"\n"+ "Tipo de usuario no reconocido.");
                return;
            }


            Stage stage = (Stage) txtId.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error"+"\n"+ "No se pudo cargar la interfaz.");
        }
    }
}