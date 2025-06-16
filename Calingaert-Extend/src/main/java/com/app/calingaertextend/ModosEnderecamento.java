package com.app.calingaertextend;

public class ModosEnderecamento {

    public static int resolveOperando (int opcode, int operando, Memoria memoria) throws AcessoIndevidoAMemoriaCheckedException {

        if ((opcode & 0b10000000) != 0) {
            return operando;
        }

        if ((opcode & 0b00100000) != 0) { // Indireto
            int endereco = memoria.getPosicaoMemoria(operando);
            return endereco;
        }

        // Direto
        return memoria.getPosicaoMemoria(operando);
    }
}
