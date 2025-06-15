public class Main{
	public static void main(String[] args){
		Memoria mem = new Memoria(12);
		Registradores regs = new Registradores();
		System.out.println("Tamanho da memoria: " + mem.getTamanho());
		System.out.println("Valor de PC: " + regs.getPC());
		regs.setPC(5);
		System.out.println("Valor de PC: " + regs.getPC());
	}
}
