package com.app.calingaertextend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.stream.Collectors;

public class ViewController {

    @FXML
    private Button buttonAction;

    @FXML
    private TextArea codigo;

    @FXML
    private Label PC;

    @FXML
    private Label SP;

    @FXML
    private Label ACC;

    @FXML
    private Label MOP;

    @FXML
    private Label RI;

    @FXML
    private Label RE;

    @FXML
    private Label R0;

    @FXML
    private Label R1;

    @FXML
    private Button buttonAtualizarRegistradores;

    @FXML
    public void passo(){
        Random random = new Random();

        PC.setText(String.valueOf(random.nextInt(10)));
        SP.setText(String.valueOf(random.nextInt(10)));
        ACC.setText(String.valueOf(random.nextInt(10)));
        MOP.setText(String.valueOf(random.nextInt(10)));
        RI.setText(String.valueOf(random.nextInt(10)));
        RE.setText(String.valueOf(random.nextInt(10)));
        R0.setText(String.valueOf(random.nextInt(10)));
        R1.setText(String.valueOf(random.nextInt(10)));
    }

    @FXML
    public void onBtClick(){
        try {
            InputStream inputStream = getClass().getResourceAsStream("/arquivos/exemplo.txt");
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String conteudo = reader.lines().collect(Collectors.joining("\n"));
                codigo.setText(conteudo);
            } else {
                codigo.setText("Arquivo não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            codigo.setText("Erro ao carregar o arquivo.");
        }
    }
}

