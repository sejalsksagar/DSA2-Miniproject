package miniProject;

//node for graph
public class AccGNode
{
	Account A;
	AccGNode link;

	AccGNode(Account A)
	{
		this.A = A;
		link = null;
	}
}