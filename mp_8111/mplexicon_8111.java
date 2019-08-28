// Nishanth Gona cs435 8111 mp

//package CS435;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class mplexicon_8111 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
		lexicon i1 = new lexicon();
		HashBatch(i1, args[0]);
		

	}
	
	
	//Note when we create T and A can choose arbitrary value that will never be chosen and then determine fullness and emptiness from there
	public static void HashCreate(lexicon L, int m) {
		L.T=new int[m];
		for(int i = 0;i<L.T.length;i++) {
			L.T[i] = -1;
		}
		L.A=new char[15*m]; //I don't know if this line is correct
		for(int i = 0;i<L.A.length;i++) {
			L.A[i] = ' ';
		}
		
		L.m=m;
		L.a = 0;
		L.initm = m;
		L.am = 15*m;
		
	}
	
	public static void HashEmpty(lexicon L) {//don't know exactly what i am doing with this method
		boolean emptyT = true;
		boolean emptyA = true;
		
		for(int i = 0; i<L.T.length;i++) {
			if(L.T[i]!=-1) {
				emptyT=false;
				break;
			}
		}
		
		for(int i = 0; i<L.A.length;i++) {
			if(L.A[i]!=' ') {
				emptyA=false;
				break;
			}
		}
	}
		/*
		if(emptyT && L.m>L.initm)
			HashCreate(L, L.m/2);
	
	}
	*/
	public static void HashFull(lexicon L) {
		boolean fullT = true;
		boolean fullA = true;
		
		//determining fullness
		for(int i = 0; i<L.T.length;i++) {
			if(L.T[i]==-1) {
				fullT=false;
				break;
			}
		}
		
		for(int i = 0; i<L.A.length;i++) {
			if(L.A[i]==' ') {
				fullA=false;
				break;
			}
		}
		
	
		//this is where we help our insert function
		 if(fullT) {
			
			L.m*=2;
			L.T = new int[L.m];
			for(int i = 0; i<L.T.length; i++) {
				L.T[i] = -1;
			}
			int kval=0;
			int tracker = 0;//where our indexes start
			for(int j = 0; j<L.A.length; j++) {
				if(L.A[j] == '\0'){
					
					int i=0;

					while(i<L.m) {
						int hprime = kval%L.m;
						int h = (hprime+(int)Math.pow(i,2))%L.m;
						if(L.T[h] == -1) {
							L.T[h] = tracker;
							tracker=j+1;
							break;
						}
						else i++;
						
				}
					kval=0;
				}
				else if(L.A[j] == ' ') {
					break;
				}
				else{
					//do ascii
					kval += (int)L.A[j];
					
				}
				}
					
			}
			
		
		if(fullA){
			char [] temp=new char[L.A.length];
			for(int i = 0; i<L.A.length;i++) {
				temp[i] = L.A[i];
			}
			L.am*=2;
			L.A = new char[L.am];//how do i deal with this
			for(int i = 0;i<L.A.length;i++) {
				L.A[i] =' ';
			}
			
			for(int i = 0; i<temp.length; i++) {
				L.A[i] = temp[i];
			}
			
		}
		

	}
	
	public static void HashPrint(lexicon L) {
		System.out.print("	T				A: ");
		for(int i = 0;i<L.A.length;i++) {
			if(L.A[i] == '\0') {
				System.out.print("\\");
			}
			else if(L.A[i]==' ') {
				System.out.print("\n");
				break;
			}
			else
				System.out.print(L.A[i]);		
		}
		System.out.println("");
		for(int i = 0; i<L.T.length;i++) {
			if(L.T[i]==-1) {
				System.out.println(i+":");
			}
			else
				System.out.println(i+":"+L.T[i]);
		}
		
	}
	
	public static void HashInsert(lexicon L, String  w) {
		//maybe have a variable that references where we are in A
		int k = 0;
		for(int j = 0;j<w.length();j++) {
			k += (int)w.charAt(j);
		}
		
		int i=0;
		//boolean seen = false;
		while(i<L.m) {
			int hprime = k%L.m;
			int h = (hprime+(int)Math.pow(i,2))%L.m;
			if(L.T[h] == -1) {
				L.T[h] = L.a;
				break;
			}
			/*how do i make this faster
			else if(L.A[L.T[h]] == w.charAt(0) ) {
				String val = "";
				for(int j = 0;j<w.length();j++) {
					val+=L.A[L.T[h]+j];
				}
				if(val.equals(w)) { 
					seen = true;
				}
				}
				*/
			else
				i++;
			
	}
		//if(!seen) {
			if(i>=L.m) {
				HashFull(L);
				HashInsert(L,w);
			}
			else {
				for(int j = 0;j<w.length()+1;j++) {
					
					if(L.a+w.length()+1>L.A.length) {
						HashFull(L);
					}
					if(j==w.length()) {
						L.A[j+L.a] = '\0';
					}
					else {
						L.A[j+L.a] = w.charAt(j);
					}
				}
				L.a+=w.length()+1;
			}
		//}
		
	}
	
	public static void HashDelete(lexicon L, String w) {
		int k = 0;
		for(int j = 0;j<w.length();j++) {
			k += (int)w.charAt(j);
			
		}
		
		int i=0;

		while(i<L.m) {// add conditon stating we have not found in T or A
			int hprime = k%L.m;
			int h = (hprime+(int)Math.pow(i,2))%L.m;
			if(L.T[h] == -1) {
				i++;
				continue;
			}
			if(L.A[L.T[h]] == w.charAt(0) ) {
				String val = "";
				for(int j = 0;j<w.length();j++) {
					val+=L.A[L.T[h]+j];
				}
				if(val.equals(w)) {
				System.out.println(w + " deleted from slot "+h);
				int tempindx = L.T[h];
				for(int j = 0; j<w.length();  j++) {
					L.A[tempindx] = '*';
					tempindx++;
				}
				
				L.T[h] = -1;
				//HashEmpty(L);
				break;
			}
			}
			else i++;
			
	}
		
	}
	
	public static void HashSearch(lexicon L, String w) {
		int k = 0;
		for(int j = 0;j<w.length();j++) {
			k += (int)w.charAt(j);
			
		}
		
		int i=0;

		while(i<L.m) {// add conditon stating we have not found in T or A
			int hprime = k%L.m;
			int h = (hprime+(int)Math.pow(i,2))%L.m;
			if(L.T[h] == -1) {
				i++;
				continue;
			}
			if(L.A[L.T[h]] == w.charAt(0) ) {
				String val = "";
				for(int j = 0;j<w.length();j++) {
					val+=L.A[L.T[h]+j];
				}
				if(val.equals(w)) {
				System.out.println(w + " found at slot " + h);
				break;
				}
			}
			else i++;
			
	}
		if(i>=L.m) {
			System.out.println(w + " not found");

		}
		
	}
	
	public static void HashBatch(lexicon L, String filename) {
	
	//This stuff looks like it needs to go in HashBatch
		String FILENAME = filename;
        String t = "";
        int m;
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
	         
	         String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				t+=sCurrentLine+"\n";
	         }
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		
        String[] arrOfStr = t.split("\n");// this gives us each line, and now we can parse it how we like
        //then go through a for loop and do the listed operations and call required functions
        m = Integer.parseInt(arrOfStr[0].substring(3,arrOfStr[0].length()));
        for(String i: arrOfStr) {
        	String func = i.substring(0,2);
        	
        	
        	if(func.equals("13")) {
        		//call print
        		HashPrint(L);
        	}
        	
        	//do everything else...
        	else if(func.equals("10")) {
        		//call insert
        		String secpart = i.substring(3,i.length());//this is the second half of our string

        		HashInsert(L, secpart);
        	}
        	else if (func.equals("11")) {
        		//call deletion
        		String secpart = i.substring(3,i.length());//this is the second half of our string
        		
        		HashDelete(L, secpart);
        	}
        	else if (func.equals("12")) {
        		//call Search
        		String secpart = i.substring(3,i.length());//this is the second half of our string
        		HashSearch(L, secpart);
        	}
        	
        	else if (func.equals("14")) {
        		//call Create
        		HashCreate(L,m);
        	}
        	
        	else if (func.equals("15")) {
        		
				continue;
        		//comment don't do anything
        	}
        		
        }
		
	}
	
	
	
	
	//What is the number of keys in a hash table, might be the actual number of strings we are looking for so maybe traverse beforehand and get this number
	
	//Choose n*8, where 7 is the avg word size and 1 is the size of null byte for array A
	
	//make a big while/for loop that goes through all of our operations that we have to do


}

 class lexicon {
	int [] T;
	char [] A;
	int m;
	int a;
	int initm;
	int am;
	}
