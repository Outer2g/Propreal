package domini.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import domini.Data;
import domini.Event;
import domini.EventVotacio;
import domini.Parlament;

public class EventVotacioDriver {
	private static Data data;
	public static EventVotacio evento;
	public static Parlament parlament;
	public static Scanner inputs= new Scanner(System.in);
	public static void imprimeDiputados(){
		int n=evento.consultarAssociats().size();
		System.out.print("diputats: ");
		for(int i=0;i<n;++i){
			System.out.print(evento.consultarAssociats().get(i)+" ");
		}
		System.out.println();
	}
	private static void tryAssociarDiputats(){
		System.out.print("Especifiqui el numero de diputats a associar:");
		int n=inputs.nextInt();
		Vector<Integer> v = new Vector<Integer>(n);
		System.out.print("Especifiqui els diputats: ");
		for(int i=0;i<n;++i){
			Integer p= new Integer(inputs.nextInt());
			v.addElement(p);
		}
		if(n>0)evento.associarDiputats(v);
			System.out.print("Diputats Associats: ");
			for(int i=0;i<n;++i) System.out.print(v.get(i)+" ");
	}
	private static void tryEliminarDiputats(){
		System.out.print("Especifiqui el numero de dipuats a desassociar: ");
		int n= inputs.nextInt();
		Vector<Integer> v=new Vector<Integer>(n);
		if(n>0)System.out.print("Especifiqui els diputats: ");
		for(int i=0;i<n;++i){
			v.addElement(inputs.nextInt());
		}
			evento.eliminarDiputats(v);
			System.out.println("Diputats eliminats");
		
	}
	private static void usage(){
		System.out.println("Escriu el numero de la prova desitjada: ");
		System.out.println("-1: finalitza el programa");
		System.out.println("0: associar diputats");
		System.out.println("1: desassociar diputats");
		System.out.println("2: cambiar tipus event per fer proves");
		System.out.println("3: Consultar tipus d'event");
		System.out.println("4: Consultar nom del event");
		System.out.println("5: Modificar data");
		System.out.println("6:  Consultar data");
		System.out.println("7: imprimeix diputats associats");
		System.out.println("8: afegir diputat i la seva votació");
		System.out.println("9: consultar votacions");
	}
	public static void imprimirException(String s){
		System.out.println(s);
	}
	private static void tryConsultaTipusEvent(){
		System.out.print("Tipus event: ");
		System.out.println(evento.consultarTipus());
	}
	private static void tryConsultarNom(){
		System.out.print("Nom event: ");
		System.out.println(evento.consultarNomEvent());
	}
	private static void tryModificarData(){
		System.out.print("Introdueixi la data en el format : dia mes any");
		int dia=inputs.nextInt();
		int mes=inputs.nextInt();
		int any=inputs.nextInt();
		try {
			data= new Data(dia,mes,any);
			evento.modificarData(data);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private static void tryConsultarData(){
		System.out.print("Data del event ");
		Data d=evento.consultarData();
		System.out.println((d.getDia())+" "+d.getMes()+" "+(d.getAny()));
	}
	private static void cambiaTipus(){
		System.out.println("Introdueixi el tipus d'event:");
		System.out.println("0: Reunio");
		System.out.println("1: activitat esportiva");
		System.out.println("3: dinar");
		System.out.println("4:conferencia");
		int p=inputs.nextInt();
		evento=new EventVotacio(p,data,"nom1");
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
		usage();
		while (n!=-1){
			n=inputs.nextInt();
			switch (n){
			case 0:
				tryAssociarDiputats();
				break;
			case 7:
				imprimeDiputados();
				break;
			case 1:
				tryEliminarDiputats();
				break;
			case 2:
				cambiaTipus();
				break;
			case 3:
				tryConsultaTipusEvent();
				break;
			case 4:
				tryConsultarNom();
				break;
			case 5:
				tryModificarData();
				break;
			case 6:
				tryConsultarData();
				break;
			case 8:
				afegeix();
				break;
			case 9:
				consulta();
				break;
			}
		}
	}

}
