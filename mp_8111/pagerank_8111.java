// Nishanth Gona cs435 8111 mp

//package CS435;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.text.DecimalFormat;
public class pagerank_8111 {

	
	//Adjacency List has an array for each element and then each index has a linked list
	//LinkedList<Integer> = new adjListArray[];
	
	public static void main(String[] args) {
		DecimalFormat numberFormat = new DecimalFormat("#.000000");
		// TODO Auto-generated method stub
		double d = 0.85;
		//PR(A) = (1-d)/n+d(PR(T1)/C(T1)+...PR(Tm)/C(Tm))
		
		
		int numvertex;
		String FILENAME = args[2];
        String t = "";

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
	         
	         String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				t+=sCurrentLine+"\n";
	         }
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		String[] arrOfStr = t.split("\n");
		String[] arrOfStr2 = arrOfStr[0].split(" ");
		numvertex = Integer.parseInt(arrOfStr2[0]);
		
		int iterations = Integer.parseInt(args[0]);
		int initialvalue = Integer.parseInt(args[1]);
		
		if(numvertex > 10) {
			iterations = 0;
			initialvalue = -1;
		}

		//How do I make a linked list arrray in java???
		LinkedList<Integer> adjlist[] = new LinkedList[numvertex];
		for(int n = 0; n<adjlist.length;n++) {
			adjlist[n] = new LinkedList<Integer>();
		}
		
		LinkedList<Integer> adjlist2[] = new LinkedList[numvertex];
		for(int n = 0; n<adjlist2.length;n++) {
			adjlist2[n] = new LinkedList<Integer>();
		}
		//Here go through all lines and add to array, changed up order of how i add to hashtable by having each key contain which vertexes point to it
		for(int g = 1; g<=Integer.parseInt(arrOfStr2[1]); g++){
			String [ ]arrOfStrtmp = arrOfStr[g].split(" ");
			int index = Integer.parseInt(arrOfStrtmp[0]);
			adjlist[index].add(Integer.parseInt(arrOfStrtmp[1]));
			
			//for second hashtable
			int idx = Integer.parseInt(arrOfStrtmp[1]);
			adjlist2[idx].add(Integer.parseInt(arrOfStrtmp[0]));
		}
		
		LinkedList<Integer> vals = new LinkedList<Integer>();
		
		double p [] = new double [numvertex];
		double ptemp [] = new double [numvertex];
		
		
		//Now do work of caculating page rank based on number of iterations
		
		if(initialvalue == 0) { 
			for(int m = 0; m<p.length; m++) {
				p[m] = 0.0;
			}
		}
		
		else if(initialvalue == 1) {
			for(int j = 0; j<p.length; j++) {
				p[j] = 1.0;
			}
		}
		
		else if(initialvalue == -1) {
			for(int z = 0; z<p.length; z++) {
				p[z] = 1.0/numvertex;
			}
		}
		
		else if(initialvalue == -2) {
			for(int k = 0; k<p.length; k++) {
				p[k] = 1.0/(Math.sqrt(numvertex));
			}
		}
		
		

		if(iterations <= 0) { //Integer.parseInt(args[0])
			double errorrate = 0.0;
			boolean go = false;
			boolean shouldprint = false;
			

			if(iterations == 0) {
				errorrate = Math.pow(10,-4);
				go = true;
				shouldprint= true;
			}
			else if(iterations <= -2 && iterations >= -6 ) {
				errorrate = Math.pow(10, iterations);
				go = true;
				shouldprint = true;
			}

			int cnt = 1;
			if(numvertex<=10 && shouldprint) {
			System.out.print("Base: "+0+" :");
			for(int j=0;j<p.length;j++) {
				System.out.print("P[ "+j+"]="+numberFormat.format(p[j])+ " ");
			}
			System.out.print("\n");
			}
			/*
			else {
				System.out.println("Base: "+0+" :");
				for(int j=0;j<p.length;j++) {
					System.out.println("P[ "+j+"] = "+numberFormat.format(p[j]));
				}
				System.out.print("\n");
			}
			*/
			while(go) {
		
				ptemp = Arrays.copyOf(p, p.length);
				for(int n = 0; n<numvertex; n++) {
					//iterate and count 
					double val = (1-d)/numvertex;
					for(int k = 0; k<adjlist2[n].size(); k++) {
						int idx = adjlist2[n].get(k);
						val+=d*(ptemp[idx]/adjlist[idx].size());
					}
					 
					p[n] = val; //where val is the double value get after our pagerank algorithm
				}
				if(numvertex<=10 && shouldprint) {
				System.out.print("Iter: "+cnt+ " :");
				for(int j=0;j<p.length;j++) {
					System.out.print("P[ "+j+"]="+numberFormat.format(p[j])+ " ");
				}
				System.out.print("\n");
				}
				/*
				else {
					System.out.println("Iter: "+cnt+ " :");
					for(int j=0;j<p.length;j++) {
						System.out.println("P[ "+j+"] = "+numberFormat.format(p[j]));
					}
					System.out.print("\n");

				}
				*/
				boolean q = true;
				for(int k = 0;k<numvertex;k++) {
					if(Math.abs(p[k]-ptemp[k])>=errorrate) {
						q=false;
					}
				}
				if(q==true) {
					go=false;
				}
				cnt++;
			}
			if(numvertex>10 && shouldprint){
				System.out.println("Iter: "+(cnt-1)+ " :");
					for(int j=0;j<p.length;j++) {
						System.out.println("P[ "+j+"] = "+numberFormat.format(p[j]));
					}
					System.out.print("\n");
			}
		}
		else {
			System.out.print("Base: "+0+" :");
			for(int j=0;j<p.length;j++) {
				System.out.print("P[ "+j+"]="+numberFormat.format(p[j])+ " ");
			}
			System.out.print("\n");
			for(int z = 1; z<=iterations;z++) {
				ptemp = Arrays.copyOf(p, p.length);
				for(int n = 0; n<numvertex; n++) {
					//iterate and count 
					double val = (1-d)/numvertex;
					for(int k = 0; k<adjlist2[n].size(); k++) {
						int idx = adjlist2[n].get(k);
						val+=d*(ptemp[idx]/adjlist[idx].size());
					}
					 
					p[n] = val; //where val is the double value get after our pagerank algorithm
				}
				System.out.print("Iter: "+z+" :");
				for(int j=0;j<p.length;j++) {
					System.out.print("P[ "+j+"]="+numberFormat.format(p[j])+ " ");
				}
				System.out.print("\n");
				//PR(A) = (1-d)/n+d(PR(T1)/C(T1)+...PR(Tm)/C(Tm))
				
			}
			
			
		}
		
	}
}


