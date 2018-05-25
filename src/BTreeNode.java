
public class BTreeNode {
    private int _n;            //number of keys in node
    private boolean _leaf;  //true if node is a leaf
    private int _t;            //minimum degree
    private String[] _keys;
    private BTreeNode[] _children;

    public BTreeNode(int t) {
        _n = 0;
        _t = t;
        _leaf = true;
        _children = new BTreeNode[2*_t];
        _keys = new String[2*t - 1];
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

    public String getKeyInPlace(int index) {
        if (index < _keys.length) {
            return _keys[index];
        }
        return "";
    }

    public BTreeNode getChild(int index){
        if(index < _children.length){
            return _children[index];
        }
        return null;
    }

    public void setChildInPlace(int index, BTreeNode x){
        if(index < _children.length){
            _children[index] = x;
        }
    }

    public void setKeyInPlace(int index, String str){
        if(index < _keys.length){
            _keys[index] = str;
        }
    }

}
