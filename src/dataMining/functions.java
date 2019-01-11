package dataMining;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class functions {

	public static void calculateFrequencyN(String filename) {
		// TODO Auto-generated method stub
		try{
			FileInputStream fstream = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
			  //System.out.println (strLine);
			  processString(strLine);
			  Main.totalTransaction++;
			}
			br.close();
		}catch(Exception e){
			
		}
	}
	public static void calculateFrequencyT(ArrayList<Transaction>condPatBase , int fr[]) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < condPatBase.size() ; i++)
		{
			Transaction t = condPatBase.get(i) ;
			for(int j = 0 ; j < t.item.size() ; j++)
			{
				int  p = t.item.get(j) ;
				fr[p] += t.count ;
			}
		}
	}
	
	static void processString(String str){
		StringTokenizer st = new StringTokenizer(str, " ");
        while (st.hasMoreTokens()){
        	 int item = Integer.parseInt(st.nextToken());
        	 if(item>Main.max) Main.max = item;
        	 Main.freq[item]++;
        	 
        }    	 
	}
	
	
	
	
	
	
	public static void printFrequency(int mx , ArrayList<item>mits){
		for(int i=0;i<mx;i++){
			if(Main.freq[i]>Main.suppportThreshold){
				//System.out.println("item:"+i+" frequency:"+ Main.freq[i]);
				item it = new item(i, Main.freq[i]);
				sortedInsert(it , mits);
				
			}
		}
	}
	public static void printFrequencyT(int mx , ArrayList<item>mits , int fr[]){
		for(int i=0;i<mx;i++){
			if(fr[i]>Main.suppportThreshold){
				//System.out.println("item:"+i+" frequency:"+ Main.freq[i]);
				item it = new item(i, fr[i]);
				sortedInsert(it , mits);
				
			}
		}
	}

   public static void sortedInsert(item it, ArrayList<item>mits){
	   for(int i = 0;i < mits.size();i++){
		   item k = mits.get(i);
		   if(it.freq>k.freq) {
			   mits.add(i,it);
			   return;
		   }
	   }
	   mits.add(it);

   }

public static void printSortedList(ArrayList<item>mits) {
	// TODO Auto-generated method stub
	for(int i = 0;i < mits.size();i++){
		item k = mits.get(i);
		System.out.println("sequence: "+i+"  item: "+k.name+"  frequency: "+k.freq);
	}
}


public static void generateFPtreeT(ArrayList<Transaction>cpb,int lFreq[] , node root , ArrayList<item>mits) {
	// TODO Auto-generated method stub
	
	for(int i = 0 ; i < cpb.size() ; i++ )
	{
		String strLine = "" ;
		Transaction t = cpb.get(i) ;
		for(int j = 0 ; j < t.item.size() ; j++)
		{
			
			strLine += t.item.get(j)+" " ;
		}
		//System.out.println("st : "+strLine+" co : "+t.count) ;
		addpathTofpTree(strLine,lFreq , root , mits,t.count);
	}
	 
}


public static void generateFPtree(String filename,int lFreq[] , node root , ArrayList<item>mits) {
	// TODO Auto-generated method stub
	try{
		FileInputStream fstream = new FileInputStream(filename);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
		  //System.out.println (strLine);
		  addpathTofpTree(strLine,lFreq , root , mits,1);
		}
		br.close();
	}catch(Exception e){
		
	}
}

private static void addpathTofpTree(String str, int lFreq[], node root , ArrayList<item>mits , int incr) {
	// TODO Auto-generated method stub
	//System.out.println("in addpath:"+str);
	
	StringTokenizer st = new StringTokenizer(str, " ");
	ArrayList<Integer>transaction= new ArrayList<Integer>();
    while (st.hasMoreTokens()){
    	 int item = Integer.parseInt(st.nextToken());
    	 //System.out.print(" "+item);
    	 ///if(Main.freq[item]>Main.suppportThreshold){
    	if(lFreq[item]>Main.suppportThreshold){
    		// System.out.println(" Entered"+item);
    		 boolean inserted = false;
    		 for(int i = 0; i<transaction.size();i++){
    			 int k = transaction.get(i);
    			 if(lFreq[item]>lFreq[k]){
    				 transaction.add(i,item);   				 
    				 inserted = true;
    				 break;
    			 }
    		 }
    		 if(!inserted) transaction.add(item);		 
    	 }
    	 
    }
    
    /*for(int i = 0; i< transaction.size();i++){
    	int k = transaction.get(i);
    	System.out.print(" "+k);
    } 
    System.out.println();*/
    
    if(transaction.size()>0)addpathTofpTreeRecursive(root,transaction,0,mits , incr);
    
}

private static void addpathTofpTreeRecursive(node parent,
		ArrayList<Integer> transaction, int position , ArrayList<item>mits , int incr) {
	// TODO Auto-generated method stub
	int curValue = transaction.get(position);
	//System.out.println(curValue+" ") ;
	node nextNode = null;
	boolean found = false;
	try{
		for(int i = 0;i < parent.childs.size();i++){
			node ch = parent.childs.get(i);
			if(ch.name==curValue){
				ch.count += incr;
				nextNode = ch;
				//System.out.println("dukse2");
				
			}
		}
	}catch(Exception e){
		
		e.printStackTrace();
	}
	if(nextNode==null){
		//System.out.println("dukse1");
		nextNode = new node();
		nextNode.name = curValue;
		nextNode.count = incr;
		nextNode.parent = parent;
		parent.childs.add(nextNode);
		item ptr = null;
		for(int i = 0;i < mits.size();i++){
			item k = mits.get(i);
			if(k.name == nextNode.name) ptr = k;
		}
		if(ptr.firsOccurence==null) ptr.firsOccurence = nextNode;
		else addToheaderTable(ptr.firsOccurence,nextNode);
		
		
	}
	if(position<transaction.size()-1) addpathTofpTreeRecursive(nextNode, transaction, position+1 , mits, incr);
	
}

private static void addToheaderTable(node lastoccur,node nextNode) {
	// TODO Auto-generated method stub
	if(lastoccur.headerNext==null) lastoccur.headerNext = nextNode;
	else addToheaderTable(lastoccur.headerNext, nextNode);
}

public static void printFPtree(node parent , node root) {
	// TODO Auto-generated method stub
	if(parent==root) System.out.println("root");
	else System.out.println("n:("+parent.name+":"+parent.count+")");
	System.out.print("childs: ");
	for(int i = 0;i<parent.childs.size();i++){
		node ch = parent.childs.get(i);
		System.out.print("("+ch.name+":"+ch.count+")");
	}
	System.out.println();
	for(int i = 0;i<parent.childs.size();i++){
		node ch = parent.childs.get(i);
		printFPtree(ch, root);
	}

	
}

public static void mineFptree(ArrayList<item>mits) {
	// TODO Auto-generated method stub
	recursiveMine(mits,"");
}

public static void extractPrefixPath(node cur, ArrayList<Transaction>condPatBase){
	//System.out.println("In extract, cur p: "+cur.parent.name+" cur node: "+cur.name);
	
	node temp = cur.parent;
	//System.out.println(temp.name);
	Transaction t = new Transaction();
	t.count = cur.count;
	while(temp.name!=-1){
		int it = temp.name;
		//System.out.println("ERRROR POINT: it = "+it+"\nArraylist size: "+t.item.size());
		t.item.add(0,it);
		//System.out.println("Added: "+it);
		temp = temp.parent;
	}
	//System.out.println("con Size: "+condPatBase.size());
	if(t.item.size()!=0) condPatBase.add(t);
	if(cur.headerNext!=null) extractPrefixPath(cur.headerNext, condPatBase);
	//if(cur.headerNext.name!=-1) extractPrefixPath(cur.headerNext, condPatBase);
	
}


public static boolean append(String fileName, String msg){
	
	try
	{
	    FileWriter fw = new FileWriter(fileName,true); //the true will append the new data
	    fw.write(msg);//appends the string to the file
	    //fw.write("\n".getBytes());
	    fw.write(System.getProperty( "line.separator" ));
	    fw.close();
	    return true;
	}
	catch(IOException ioe)
	{
	    System.err.println("IOException: " + ioe.getMessage());
	    return false;
	}
	
}






private static void recursiveMine(ArrayList<item> its, String prev) {
	
	//System.out.println("previous St "+prev+" \ntab size: "+its.size());
	
	
	//System.out.println("***********************OUTPUT***************");
	if(prev != "")
	for(int j = 0 ; j < its.size() ; j++)
	{
		item p = its.get(j) ;
		
		//System.out.println(p.name+" "+prev+":"+p.freq) ;
		String res ="{" + p.name+","+prev+"}:"+p.freq;
		append("patterns.dat",res);
	}
	//System.out.println("***********************OUTPUT END***************");
	//if(its.size()==1) return;
	
	// TODO Auto-generated method stub
	for(int i = its.size()-1;i>=0;i--){
		
	    ArrayList<Transaction>condPatBase = new ArrayList<Transaction>();
		node it = its.get(i).firsOccurence;	
		System.out.println("sequence:"+i+" item: "+it.name+" freq: "+it.count);
		node tit = it;
		while(tit!=null){
			Transaction t = getTransactionFromTree(tit.parent);
			t.count = tit.count;
			printTransaction(t);
			if(t.item.size()>0) condPatBase.add(t);
			tit = tit.headerNext;
		}
		
		/*while(it!=null){
			Transaction t = getTra
		}*/
		
		
	
		
		
		
		
		//extractPrefixPath(it, condPatBase);
		
		int fr[] = new int[Main.lim] ;
		functions.calculateFrequencyT(condPatBase , fr);
		

		ArrayList<item>lits ;
		node lroot = new node() ;
		lits = new ArrayList<item>();
		functions.printFrequencyT(Main.lim, lits , fr);
		functions.generateFPtreeT(condPatBase,fr, lroot , lits);	
			
		for(int j = 0 ; j < lits.size() ; j++)
		{
			item p = lits.get(j) ;
			//System.out.println(p.name+":"+p.freq) ;
		}
		
		condPatBase.clear();		
		functions.recursiveMine(lits,it.name+","+prev);
		
		
		
	}
	
	
	
	
    
}
private static void printTransaction(Transaction t) {
	// TODO Auto-generated method stub
	System.out.println("Transaction: ");
	for(int i =0;i <t.item.size();i++){
		System.out.print(t.item.get(i)+" ");
	}
	System.out.println("Freq: "+t.count);
	
}
private static Transaction getTransactionFromTree(node cur) {
	Transaction t = new Transaction();
	while(cur.name!=-1){
		t.item.add(cur.name);
		cur = cur.parent;
	}
	return t;
}

	
}
