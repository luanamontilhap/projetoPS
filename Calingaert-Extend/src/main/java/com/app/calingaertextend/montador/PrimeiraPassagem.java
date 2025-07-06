package com.app.calingaertextend.montador;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrimeiraPassagem {


    public void primeirapassagem(String caminhoArquivo,
                                 TabelaDeSimbolos tabelaSimbolos,
                                 TabelaInstrucao tabelaInstrucao) {

        int locctr = 0;
        int linhaAtual = 0;
        boolean emDefinicaoDeMacro = false;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            System.out.println("\n--- A iniciar Primeira Passagem (com cálculo de tamanho) ---");

            while ((linha = br.readLine()) != null) {
                linhaAtual++;
                linha = linha.trim();

                // 1. Ignora linhas vazias ou de comentário.
                if (linha.isEmpty() || linha.startsWith("*")) {
                    continue;
                }

                // DEBUG: Imprime a linha que está a ser processada.
                System.out.println("\n[Linha " + linhaAtual + "]: '" + linha + "' | locctr atual = " + locctr);

                // 2. Lógica para pular o conteúdo das definições de macro.
                if (linha.toUpperCase().startsWith("MACRO")) {
                    emDefinicaoDeMacro = true;
                    System.out.println("  -> Detetado início de MACRO. A ignorar conteúdo.");
                    continue;
                }
                if (linha.toUpperCase().startsWith("MEND")) {
                    emDefinicaoDeMacro = false;
                    System.out.println("  -> Detetado fim de MEND.");
                    continue;
                }
                if (emDefinicaoDeMacro) {
                    continue;
                }

                // 3. Divide a linha em partes (tokens) para análise.
                String[] partes = linha.split("\\s+");
                String rotulo = null;
                String instrucaoOuDiretiva = null;
                
                // 4. Lógica para identificar se a primeira palavra é um rótulo.
                if (!tabelaInstrucao.contains(partes[0].toUpperCase())) {
                    rotulo = partes[0];
                    if (partes.length > 1) {
                        instrucaoOuDiretiva = partes[1].toUpperCase();
                    }
                } else {
                    instrucaoOuDiretiva = partes[0].toUpperCase();
                }

                // Bloco de segurança para corrigir má identificação de START/END.
                if (rotulo != null && (rotulo.equalsIgnoreCase("START") || rotulo.equalsIgnoreCase("END"))) {
                    instrucaoOuDiretiva = rotulo.toUpperCase();
                    rotulo = null;
                }

                // 5. Trata as diretivas de controle START e END.
                if (instrucaoOuDiretiva != null) {
                    if (instrucaoOuDiretiva.equals("START")) {
                        System.out.println("  -> Processando diretiva START.");
                        if (partes.length > 1) {
                           try {
                               locctr = Integer.parseInt(rotulo == null ? partes[1] : partes[2]);
                           } catch (NumberFormatException nfe) {
                               locctr = 0;
                           }
                        }
                        if (rotulo != null) {
                            tabelaSimbolos.adicionarSimbolo(rotulo, locctr, "PROGRAMA", "DEFINIDO");
                        }
                        continue;
                    } else if (instrucaoOuDiretiva.equals("END")) {
                        System.out.println("  -> Processando diretiva END. Fim da primeira passagem.");
                        break; 
                    }
                }

                // 6. Adiciona o rótulo (se existir) na Tabela de Símbolos com o endereço ATUAL.
                if (rotulo != null) {
                    if (!tabelaSimbolos.contemSimbolo(rotulo)) {
                        System.out.println("  -> ADICIONANDO SÍMBOLO: (" + rotulo + ", " + locctr + ")");
                        tabelaSimbolos.adicionarSimbolo(rotulo, locctr, "LABEL", "DEFINIDO");
                    } else {
                        System.err.println("Erro na linha " + linhaAtual + ": Símbolo duplicado '" + rotulo + "'.");
                    }
                }

                // 7.  LÓGICA DE INCREMENTO FINAL
                // Incrementa o locctr de acordo com o tamanho real da instrução ou diretiva.
                if (instrucaoOuDiretiva != null) {
                    int tamanho = tabelaInstrucao.getInstructionSize(instrucaoOuDiretiva);
                    if (tamanho > 0) {
                        System.out.println("  -> Instrução/Diretiva '" + instrucaoOuDiretiva + "' (tamanho " + tamanho + "). Incrementando locctr.");
                        locctr += tamanho;
                    }
                }

            } // Fim do while

        } catch (IOException e) {
            System.err.println("Erro ao ler o ficheiro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro de formato numérico na linha " + linhaAtual + ".");
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado na linha " + linhaAtual + ": " + e.getMessage());
            e.printStackTrace();
        }

        // Impressão final da Tabela de Símbolos para verificação.
        System.out.println("\n--- Tabela de Símbolos (Resultado da Primeira Passagem) ---");
        tabelaSimbolos.imprimirTabela();
        System.out.println("---------------------------------------------------------");
    }
}
