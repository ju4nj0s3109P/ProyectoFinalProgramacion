package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.controller.LoginController;
import co.edu.uniquindio.poo.model.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Crear listas de prueba
            List<Paciente> pacientes = new ArrayList<>();
            List<Medico> medicos = new ArrayList<>();
            List<Administrador> administradores = new ArrayList<>();

            Hospital hospital = new Hospital("Hospital Central", medicos, pacientes, administradores);

            pacientes.add(new Paciente("p1", "Carlos", "311", 30));
            medicos.add(new Medico("m1", "Dra. Lina", "312", "med001", "Pediatría"));
            Administrador admin = new Administrador("a1", "Admin", "313", hospital);
            administradores.add(admin);

            Paciente carlos = pacientes.get(0);
            Medico medico = medicos.get(0);


            Cita cita = new Cita("cita001", EstadoCita.PENDIENTE, LocalDate.now(), LocalTime.of(10, 0),carlos);
            carlos.getCitas().add(cita);
            medico.getCitasAsignadas().add(cita);

            List<Diagnostico> diagnosticos = new ArrayList<>();
            List<Tratamiento> tratamientos = new ArrayList<>();


            Diagnostico diagnostico1 = new Diagnostico("Gripe común", LocalDate.now().minusDays(2));
            Tratamiento tratamiento1 = new Tratamiento("Tomar acetaminofén cada 8 horas", LocalDate.now(), LocalDate.now().plusDays(5));
            diagnosticos.add(diagnostico1);
            tratamientos.add(tratamiento1);

            HistorialMedico historial = new HistorialMedico(new ArrayList<>(diagnosticos), new ArrayList<>(tratamientos));



            carlos.setHistorialMedico(historial);



            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/view/Login.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

            // Pasar hospital al controlador
            LoginController controller = loader.getController();
            controller.setHospital(hospital);
            controller.setAdministrador(admin);

            stage.setScene(scene);
            stage.setTitle("Login - Sistema Hospitalario");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace(); // ⬅️ Esto imprimirá el error exacto en consola
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
