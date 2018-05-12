
public class BTreeNode 
{

	private int _n;			//number of keys in node
	private boolean _leaf;  //true if node is a leaf
	private int _t;			//minimum degree
	
	
	public BTreeNode(int t, boolean leaf) 
	{
		_t = t;
		_leaf = leaf;
	}

}
