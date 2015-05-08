package domini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EventVotacioDriver {
	private static EventVotacio evento;
	private static Scanner inputs= new Scanner(System.in);
	private static void imprimeixUsage(){
		System.out.println("Llista de comandes:");
		System.out.println("1: afegir diputat i la seva votació");
		System.out.println("2: consultar votacions");
	}
	private static void afegeix(){
		System.out.println("introdueix diputat");
		Integer diputat=new Integer(inputs.nextInt());
		System.out.println("introdueixi la votacio per el diputat "+diputat);
		int votacio=inputs.nextInt();
		evento.introduirDades(diputat, votacio);
		System.out.println("afegit correctament");
	}
	private static void consulta(){
		ArrayList<ArrayList<Integer>> vots=evento.consultaVotacions();
		System.out.print("Votants Si: ");
		int i=0;
		for(i=0;i<vots.get(0).size();++i)System.out.print(vots.get(0).get(i)+" ");
		System.out.println();
		System.out.print("Votants No: ");
		for(i=0;i<vots.get(1).size();++i)System.out.print(vots.get(1).get(i)+" ");
		System.out.println();
		System.out.print("Votants Blanc: ");
		for(i=0;i<vots.get(2).size();++i)System.out.print(vots.get(2).get(i)+" ");
		System.out.println();
		System.out.print("No presents: ");
		for(i=0;i<vots.get(3).size();++i)System.out.print(vots.get(3).get(i)+" ");
		System.out.println();
		
	}
	public static void main(String[] args) {
		try{
			Data data = new Data(15,3,2015);
			evento= new EventVotacio(data,"Votacio1");
		}
		catch(Exception e){	
		}
		int n=0;
		imprimeixUsage();
		while(n!=-1){
			n=inputs.nextInt();
			switch(n){
			case 1:
				afegeix();
				break;
			case 2:
				consulta();
				break;
				
			}
		}
	}

}
