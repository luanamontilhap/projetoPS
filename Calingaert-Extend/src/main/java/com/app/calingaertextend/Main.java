package com.app.calingaertextend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("View.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calingaert Extend");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Memoria memoria = new Memoria(12); // CUIDAR POIS EU SO POSSO TER UMA MEMORIA!!!!!!!
        Registradores registradores = new Registradores();
        Executor executor = new Executor(memoria, registradores);
        executor.carregarPrograma();
        try {
        executor.executarPasso();
        } catch (AcessoIndevidoAMemoriaCheckedException e) {
            e.printStackTrace();
        }
        launch();
    }
}