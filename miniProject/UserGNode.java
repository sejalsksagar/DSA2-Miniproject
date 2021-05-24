package miniProject;

//node for graph
public class UserGNode
{
	User U;
	UserGNode link;

	UserGNode(User U)
	{
		this.U = U;
		link = null;
	}
}