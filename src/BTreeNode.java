
public class BTreeNode {
    private int _n;            //number of keys in node
    private boolean _leaf;  //true if node is a leaf
    private int _t;            //minimum degree
    private String[] _keys;
    private BTreeNode rightChild;
    private BTreeNode leftChild;

    public BTreeNode(int t, boolean leaf) {
        _t = t;
        _leaf = leaf;
        leftChild = null;
        rightChild = null;
    }

    //-------- getters and setters
    public boolean is_leaf() {
        return _leaf;
    }

    public void setLeaf(boolean leaf) {
        this._leaf = leaf;
    }

    public int get_n() {
        return _n;
    }

    public void set_n(int n) {
        this._n = n;
    }

    public String[] get_keys() {
        return this._keys;
    }

    public BTreeNode getRightChild(){
        return rightChild;
    }

    public BTreeNode getLeftChild(){
        return leftChild;
    }

}
