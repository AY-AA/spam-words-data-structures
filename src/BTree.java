import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BTree {
    private BTreeNode root;
    private int _t;
    private String[] _friends;
    private int _treeSize;
    private String _fileName;
    private FileReader _fileReader;
    private BufferedReader _bufferedReader;


    public BTree(String t) {
        this._t = Integer.parseInt(t);
        root.setLeaf(true);
    }

    public void insert(String newValue) {
        //need to complete method
    }

    private void splitChiled(BTreeNode x, int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(_t, true);
        z.setLeaf(y.is_leaf());
        z.set_n(_t - 1);
        for (int j = 0; j < _t - 1; j++) {
            z.get_keys()[j] = y.get_keys()[j + _t];
        }
        if(!y.is_leaf()){ //needs to complete method
            for (int k = 0; k < _t; k++) {

            }
        }
    }
    // I thought about creating a method in BTreeNode that determine which child to go to, left or right (by receiving i as input)

    public BTreeNode search(String key) {
        if (root == null) {
            return root;
        }
        return search(key, root);
    }

    private BTreeNode search(String key, BTreeNode x) {
        int i = 0;
        while (i < x.get_n() && key.compareTo(x.get_keys()[i]) > 0) { //if key is bigger than current
            i++;
        }
        if (i <= x.get_n() && x.get_keys()[i].equals(key)) { //if found
            return x;
        }
        if (x.is_leaf()) { //if not found
            return null;
        }
        if (x.getLeftChild() != null && (x.get_keys()[i].compareTo(key) > 0)) {
            return search(key, x.getLeftChild());
        } else {
            return search(key, x.getRightChild());
        }
    }

    private void getFriendsFromFile() {
        if (_friends.length == 0) {
            _treeSize = 0;
            System.out.println(_fileName + "will generate an empty tree ");
            return;
        }
        String currentPairOfFriends;
        int counter = 0;
        try {
            while ((currentPairOfFriends = _bufferedReader.readLine()) != null) {
                counter++;
                _friends[counter] = currentPairOfFriends;
            }
            _bufferedReader.close();
        } catch (IOException ex) {
            System.out.println("error: unable to read file '" + _fileName + "'");
        }
    }

    private void initiatingFileHandler() {
        try {
            _fileReader = new FileReader(_fileName);
            _bufferedReader = new BufferedReader(_fileReader);
        } catch (IOException ex) {
            System.out.println("error: unable to open file '" + _fileName + "'");
            _treeSize = 0;
        }
    }

    private void getTreeSize() {
        String currentPairOfFriends;
        int counter = 0;
        try {
            while ((currentPairOfFriends = _bufferedReader.readLine()) != null) {
                counter++;
            }
        } catch (IOException ex) {
            System.out.println("error: unable to read file '" + _fileName + "'");
        }
        if (counter > 0) {
            _treeSize = counter;
        }
    }

    private void buildBTree() {
        for (int i = 0; i < _friends.length; i++) {
            insert(_friends[i]);
        }
    }

    public void createFullTree(String path) {
        _fileName = path;
        initiatingFileHandler();
        getTreeSize();
        _friends = new String[_treeSize];
        getFriendsFromFile();
        buildBTree();
    }

    public boolean areFriends(String sender, String receiver) {
//		search(sender + " & " + receiver );
//		search(receiver + " & " + sender );
//		if one of them found return true
        return false;
    }


}
