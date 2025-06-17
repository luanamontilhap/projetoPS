package com.app.calingaertextend;

public class ModosEnderecamento {

    public static int resolveOperando (int opcode, int operando, Memoria memoria, boolean permiteIndireto,boolean permiteImediato) throws AcessoIndevidoAMemoriaCheckedException {

        if ((opcode & 0b10000000) != 0) { // Imediato
            if (!permiteImediato) {
                System.out.println("Modo IMEDIATO nao suportado por essa instrucao");
            } 
            return operando;
        }

        if ((opcode & 0b00100000) != 0) { // Indireto
            if (!permiteIndireto) {
                System.out.println("Modo INDIRETO nao suportado por essa instrucao");
            }
            int endereco = memoria.getPosicaoMemoria(operando);
            return memoria.getPosicaoMemoria(endereco);
        }

        // Direto
        return memoria.getPosicaoMemoria(operando);
    }
}
