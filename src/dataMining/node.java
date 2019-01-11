package dataMining;

import java.util.ArrayList;

public class node {
	int name;
	int count;
	ArrayList<node>childs;
	node parent;
	node headerNext;
	node(){
		childs = new ArrayList<node>();
		parent = null;
		headerNext = null;
		name = -1;
		count = 0;
	}
}
