package com.app.calingaertextend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Executor {
        
    private boolean executando;
    private Memoria memoria;
    private Registradores registradores;

    public Executor (Memoria memoria, Registradores registradores) {
        this.memoria = memoria;
        this.registradores = registradores;
    }

    public void carregarPrograma() {
    try (InputStream inputStream = getClass().getResourceAsStream("/arquivos/exemplo.txt")) {
        if (inputStream == null) {
            System.out.println("Arquivo não encontrado no classpath.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String linha;
            int endereco = 0;

            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                String[] numeros = linha.split("\\s+"); // separa por espaços
                for (String numStr : numeros) {
                    int valor = Integer.parseInt(numStr);
                    memoria.setPosicaoMemoria(endereco, valor);
                    endereco++;
                }
            }
            System.out.println("Programa carregado na memória.");
            memoria.imprimirMemoria(); // Debug
        }
    } catch (IOException | AcessoIndevidoAMemoriaCheckedException e) {
        e.printStackTrace();
    }
}

    public void executarPasso () throws AcessoIndevidoAMemoriaCheckedException {
        int pc = registradores.getPC();
        int opcode = memoria.getPosicaoMemoria(pc);
        int op1 = memoria.getPosicaoMemoria(pc+1);
        int op2 = 0;

        registradores.setRI(opcode);
        registradores.setRE(op1);
        
        if (opcode == 0x13) { // Copy precisa de 2 operandos
            op2 = memoria.getPosicaoMemoria(pc + 2); 
        }
        System.out.println("DEBUG ----------- ANTES DE CHAMAR O EXECUTAR");
        System.out.println("OP1: " + op1);
        System.out.println("OPCODE: "+ opcode);
        System.out.println("OP2:" + op2);
        Instrucoes.executar(opcode, op1, op2, registradores, memoria);
        System.out.println("DEBUG ----------- DEPOIS DO EXECUTAR");
        System.out.println("OP1: " + op1);
        System.out.println("OPCODE: "+ opcode);
        System.out.println("OP2:" + op2);
    }

}
