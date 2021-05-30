package MiniProject;

public class AccTNode {
	int h;
	Account A;
	AccTNode left;
	AccTNode right;
	AccTNode parent; // parent node
	AccTNode succ;   // successor node
	
	
	
	AccTNode(Account A){
		h = 0;
		this.A = A;
		left = null;
		right = null;
	}
	AccTNode(){
		
	}
}
