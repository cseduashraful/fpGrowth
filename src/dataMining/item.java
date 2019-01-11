package dataMining;

public class item {
	public int name;
	public int freq;
	public node firsOccurence;
	item(){
		this.name = -1;
		this.freq = 0;
		this.firsOccurence = null;
	}
	
	public item(int name, int frequency){
		this.name = name;
		this.freq = frequency;
		this.firsOccurence = null;
	}
}
