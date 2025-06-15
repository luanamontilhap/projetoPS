class Instruções {
    public static void executar (int opcode, int op1, int op2, Registradores registrador, Memoria memoria){

        switch (opcode){

            case 0x00:
                //BR muda o valor do PC para o endereço que foi informado, tipo PC = op1
            break;

            case 0x01:
                //BRPOS muda o valor do PC caso for maior que zero (ACC > 0)

            break;

            case 0x02:
                //ADD, aqui a gente soma os operandos (ACC = ACC + memoria [op1])
                //registrador.ACC += memoria.ler (op1)
                //ACC = ACC + memoria[op1];

            break;

            case 0x03:
                //LOAD, a gente carrega o operando no ACC (ACC = op1)
            
            break;
            case 0x04:
                //BRZERO muda o valor do PC caso ACC == 0

            break;

            case 0x05:
                //BRNEG muda o PC caso ACC < 0

            break;

            case 0x06:
                //SUB, aqui a gente subtrai os operandos (ACC = ACC - memoria[op1])

            break;

            case 0x07:
                //STORE, guarda o ACC em um endereço (OP1 = ACC)

            break;

            case 0x08:
                //WRITE, aqui a gente escreve na saída (output = Op1)
            break;

            case 0x10:
                //DIVIDE, só divide msm (ACC = ACC/Op1)

            break;

            case 0x11:
                //STOP (para a execução)
                //System.exit(0)

            break;

            case 0x12:
                //READ só lê o a entrada (op1 = input)

            break;

            case 0x13:
                //COPY copia de uma op pra outra (op1 = op2)

            break;

            case 0x14:
                //MULT multiplica os dois valores (ACC = ACC*Op1)

            break;
            
            case 0x15:
                //CALL chama uma subrotina 
                //(SP = PC
                    //SP = SP + 1
                    //PC = ACC (NN SEI)
                
            break;

            case 0x16:
                //RET (sai d subrotina e volta pro código)
                // S = SP -1 
                // PC = SP

            break;

            case 0x17:
                //PUSH coloca um valor na pilha e ajeita o ponteiro
                // SP = registrador
                // SP = SP + 1
            break;

            case 0x18:
                //POP tira um valor na pilha e coloca no registrador
                // SP = SP -1 
                // registrador = SP

            break;
        }
    }
    
}