package com.app.calingaertextend;

public class Executor {
        
    Memoria memoria = new Memoria(12);
    Registradores registradores = new Registradores();

    public Executor (Memoria memoria, Registradores registradores) {
        this.memoria = memoria;
        this.registradores = registradores;
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
        
        Instrucoes.executar(opcode, op1, op2, registradores, memoria);

    }
}
