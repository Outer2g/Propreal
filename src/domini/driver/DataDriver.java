package domini.driver;

import java.util.Scanner;

import domini.Data;

public class DataDriver {
	private static Data data;
	public static Scanner inputs= new Scanner(System.in);
	private static void imprimeixCasos(){
		System.out.println("Escriu el numero de la prova desitjada:");
		System.out.println("-1: finalitza el programa");
		System.out.println("1: modifica Data");
		System.out.println("2:consulta Data");
	}
	private static void modificaData(){
		System.out.println("Introdueix la nova data en el format: dia mes any");
		int dia=inputs.nextInt();
		int mes=inputs.nextInt();
		int any=inputs.nextInt();
		try{
			data= new Data(dia,mes,any);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	private static void consultaData(){
		System.out.println("Data: ");
		System.out.println(data.getDia()+ " "+ data.getMes()+" "+data.getAny());
	}
	public static void main(String[] args) throws Exception 	 {
		try{
			data = new Data(15,3,2015);
		}
		catch(Exception e){	
		}
		imprimeixCasos();
		int n=0;
		while(n!=-1){
			n=inputs.nextInt();
			switch(n){
			case 1:
				modificaData();
				break;
			case 2:
				consultaData();
				break;
			}
		}
		
	}
}
