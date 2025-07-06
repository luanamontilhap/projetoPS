package com.app.calingaertextend.montador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SegundaPassagem {

    /**
     * Executa a segunda passagem do montador e salva o resultado num ficheiro.
     * @param caminhoArquivoEntrada O caminho para o ficheiro de código-fonte.
     * @param caminhoArquivoSaida O caminho para o ficheiro de saída onde o código objeto será salvo.
     * @param tabelaSimbolos A tabela de símbolos preenchida na primeira passagem.
     * @param tabelaInstrucao A tabela de instruções.
     */
    public void segundapassagem(String caminhoArquivoEntrada, String caminhoArquivoSaida,
                                TabelaDeSimbolos tabelaSimbolos,
                                TabelaInstrucao tabelaInstrucao) {

        int linhaAtual = 0;
        boolean emDefinicaoDeMacro = false;

        // Usa try-with-resources para garantir que o leitor e o escritor sejam fechados automaticamente.
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoEntrada));
             BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivoSaida))) {

            String linha;

            System.out.println("\n--- A gerar código objeto para o ficheiro: " + caminhoArquivoSaida + " ---");

            while ((linha = br.readLine()) != null) {
                linhaAtual++;
                linha = linha.trim();

                // 1. Ignora comentários, linhas em branco e DEFINIÇÕES de macro
                if (linha.isEmpty() || linha.startsWith("*")) {
                    continue;
                }
                if (linha.toUpperCase().startsWith("MACRO")) {
                    emDefinicaoDeMacro = true;
                    continue;
                }
                if (linha.toUpperCase().startsWith("MEND")) {
                    emDefinicaoDeMacro = false;
                    continue;
                }
                if (emDefinicaoDeMacro) {
                    continue;
                }

                // 2. Divide a linha em partes, tratando múltiplos espaços
                String[] partes = linha.split("\\s+");
                String rotulo = null;
                String instrucao = null;
                List<String> operandos = new ArrayList<>();

                // 3. Lógica de Análise (Parsing) Melhorada
                int indiceInstrucao = -1;
                if (tabelaInstrucao.contains(partes[0].toUpperCase())) {
                    // Formato: INSTRUCAO [OPERANDOS...]
                    indiceInstrucao = 0;
                } else if (partes.length > 1 && tabelaInstrucao.contains(partes[1].toUpperCase())) {
                    // Formato: ROTULO INSTRUCAO [OPERANDOS...]
                    rotulo = partes[0];
                    indiceInstrucao = 1;
                }

                if (indiceInstrucao != -1) {
                    instrucao = partes[indiceInstrucao].toUpperCase();
                    // Adiciona todos os operandos que vêm depois da instrução
                    for (int i = indiceInstrucao + 1; i < partes.length; i++) {
                        operandos.add(partes[i]);
                    }
                } else {
                    // Se não encontrou instrução, pode ser uma linha inválida
                    continue;
                }

                // 4. Ignora diretivas que não geram código nesta passagem
                if (instrucao.equals("START") || instrucao.equals("END")) {
                    continue;
                }

                // 5. Gera o Código Objeto e escreve no ficheiro
                if (instrucao.equals("SPACE")) {
                    // Para SPACE, gera uma palavra de memória com valor 0.
                    writer.write("00");
                    writer.newLine();
                } else if (instrucao.equals("CONST")) {
                    // Para CONST, gera o valor da constante.
                    if (!operandos.isEmpty()) {
                        writer.write(String.format("%02d", Integer.parseInt(operandos.get(0))));
                        writer.newLine();
                    }
                } else {
                    // Para instruções executáveis
                    int opcode = tabelaInstrucao.getOpcode(instrucao);
                    if (opcode == -1) {
                        // Ignora chamadas de macro que não são expandidas
                        continue;
                    }

                    StringBuilder codigoObjeto = new StringBuilder();
                    codigoObjeto.append(String.format("%02d", opcode)); // Adiciona o opcode

                    // Adiciona os endereços dos operandos
                    for (String operando : operandos) {
                        Simbolos simbolo = tabelaSimbolos.buscarSimbolo(operando);
                        if (simbolo != null) {
                            codigoObjeto.append(String.format(" %02d", simbolo.getEndereco()));
                        } else {
                            // Imprime erro na consola, mas marca no ficheiro também
                            System.out.println("Erro: símbolo '" + operando + "' indefinido na linha " + linhaAtual);
                            codigoObjeto.append(" ??");
                        }
                    }
                    writer.write(codigoObjeto.toString());
                    writer.newLine();
                }
            } // Fim do while

            System.out.println("--- Geração de código concluída com sucesso! ---");

        } catch (IOException e) {
            System.err.println("Erro ao ler ou escrever no ficheiro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado na linha " + linhaAtual + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
