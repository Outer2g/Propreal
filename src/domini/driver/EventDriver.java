
	package domini.driver;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import domini.Data;
import domini.Diputat;
import domini.Event;
import domini.NodeLouvain;

	public class EventDriver {
		private static Data data;
		public static Event evento;
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
			evento=new Event(p,data,"nom1");
		}
		
		public static void main(String[] args){
			int n=0;
			Scanner inputs=new Scanner(System.in);
			try{
				data=new Data(17,04,2015);
			evento=new Event(1,data,"nom1");
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				System.exit(0);
			}
			usage();
			HashMap <Diputat,Integer> p= new HashMap<Diputat,Integer>();
			Diputat d=new Diputat("7");
			p.put(d, 4);
			d= new Diputat("8");
			p.put(d, 9);
			d=new Diputat("1");
			p.put(d, 4);
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
				}
			}
		}

	}



