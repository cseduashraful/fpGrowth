package dataMining;

import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	
	/**
	  max = 8000 for pumsb_star	 
	  max  = 80 for chess
	  max = 130 for mushroom
	  max = 42000 for kosarak
	  
	  
	 * 
	 * 
	 * **/
	
	public static int max = -1;
	public static int lim = 99999;
	public static int suppportThreshold = 6200;
	public static int freq[] = new int[lim];
	public static int totalTransaction;
	public static ArrayList<item>its;
	public static node root;
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename =  "kosarak.dat";
		functions.calculateFrequencyN(filename);
		its = new ArrayList<item>();
		root = new node();
		functions.printFrequency(lim,Main.its);///for chesss 80
		System.out.println("total transaction: "+totalTransaction+"\n max item: "+Main.max);
		System.out.println("After sorting: ");
		functions.printSortedList(Main.its);
		functions.generateFPtree(filename,Main.freq , Main.root , Main.its);		
		//functions.printFPtree(Main.root);
		functions.mineFptree(Main.its);
		System.out.println("total transaction: "+totalTransaction+"\n max item: "+Main.max);
		
	}

}
