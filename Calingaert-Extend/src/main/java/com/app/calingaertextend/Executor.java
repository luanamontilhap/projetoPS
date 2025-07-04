package com.app.calingaertextend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Executor {
        
    private boolean executando;
    private Memoria memoria;
    private Registradores registradores;
    private Pilha pilha;
    private ViewController controller;
    private String label;
    private String opcode;
    private String op1;
    private String op2;
    private String comentario;

    public Executor (Memoria memoria, Registradores registradores, Pilha pilha) {
        this.memoria = memoria;
        this.registradores = registradores;
        this.pilha = pilha;
        this.executando = true;
        this.label = null;
        this.opcode = null;
        this.op1 = null;
        this.op2 = null;
        this.comentario = null;
    }

    public void geraArquivo() { 
    try (InputStream inputStream = getClass().getResourceAsStream("/arquivos/exemplo.txt")) {
        if (inputStream == null) {
            System.out.println("Arquivo não encontrado.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(",");
                
                label = campos[0];
                opcode = campos[1];
                op1 = campos[2];
                op2 = campos[3];
                comentario = campos[4];

                if (!label.isBlank()) setLabel(label);
                if (!opcode.isBlank()) setOpcode(opcode);
                if (!op1.isBlank()) setOp1(op1);
                if (!op2.isBlank()) setOp2(op2);
                if (!comentario.isBlank()) setComentario(comentario);
                
                // Debug
                System.out.println("Label: " + label);
                System.out.println("Opcode: " + opcode);
                System.out.println("Op1: " + op1);
                System.out.println("Op2: " + op2);
                System.out.println("Comentário: " + comentario);
            }
            System.out.println("Programa carregado na memória.");
        }
    } catch (IOException e) {
        e.printStackTrace();
        }
    }

    // Provavel que precise ser modificada
    public void executarPasso () throws AcessoIndevidoAMemoriaCheckedException {
        while (executando == true) {
        int pc = registradores.getPC();
        int opcode = memoria.getPosicaoMemoria(pc);
        int op1 = memoria.getPosicaoMemoria(pc+1);
        int op2 = 0;
        registradores.setRI(opcode);
        registradores.setRE(op1);
        
        if (opcode == 13) { 
            op2 = memoria.getPosicaoMemoria(pc + 2); 
        }
        Instrucoes.executar(opcode, op1, op2, registradores, memoria, this, pilha);
        }
        controller.atualizarTabela(registradores);
        controller.atualizarTabelaMemoria(memoria.getMemoria());
    }
        
    public void pararExecucao () {
        this.executando = false;
    }

    // Getters e Setters

    public void setController(ViewController controller) {
        this.controller = controller;
    }

    public String getComentario() {
        return comentario;
    }

    public String getLabel() {
        return label;
    }

    public Memoria getMemoria() {
        return memoria;
    }

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setMemoria(Memoria memoria) {
        this.memoria = memoria;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}
