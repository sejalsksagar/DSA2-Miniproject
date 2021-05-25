package miniProject;

public class AccTNode {
	int h;
	Account A;
	AccTNode left;
	AccTNode right;
	
	AccTNode(Account A){
		h = 0;
		this.A = A;
		left = null;
		right = null;
	}
}
