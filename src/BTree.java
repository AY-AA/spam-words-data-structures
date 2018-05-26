import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BTree {
    private Queue bfsQueue;
    private BTreeNode root;
    private int _t;
    private String[] _friends;
    private int _treeSize;
    private String _fileName;
    private FileReader _fileReader;
    private BufferedReader _bufferedReader;
    private String bTreeAsString;


    public BTree(String t) {
        this._t = Integer.parseInt(t);
    }

    public void createFullTree(String path) {
        _fileName = path;
        initiatingFileHandler();
        root = new BTreeNode(_t);
        getTreeSize();
        _friends = new String[_treeSize];
        initiatingFileHandler();
        getFriendsFromFile();
        insertFriends();
        bfsQueue = new Queue(_treeSize);
        bTreeAsString = "";
    }

    public boolean search(String key) {
        if (root == null) {
            return false;
        }
        return search(key, root);
    }

    private boolean search(String key, BTreeNode x) {
        int i = 0;
        while (i < x.get_n() && key.compareTo(x.getKeyInPlace(i)) > 0) { //if key is bigger than current
            i++;
        }
        if (i < x.get_n() && key.compareTo(x.getKeyInPlace(i)) == 0) { //if found
            return true;
        }
        if (x.is_leaf()) { //if not found
            return false;
        } else {
            return search(key, x.getChild(i));
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
                _friends[counter] = currentPairOfFriends;
                counter++;
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

    private void splitChild(BTreeNode x, int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(_t);
        z.setLeaf(y.is_leaf());
        z.set_n(_t - 1);
        for (int j = 0; j < _t - 1; j++) {
            z.setKeyInPlace(j, y.getKeyInPlace((j + _t)));
            y.setKeyInPlace(j + _t, null); //just for conveniently
        }
        if (!y.is_leaf()) {
            for (int k = 0; k < _t; k++) {
                z.setChildInPlace(k, y.getChild((k + _t)));
                y.setChildInPlace(k + _t, null); //just for conveniently
            }
        }
        y.set_n(_t - 1);
        for (int j = x.get_n(); j >= i; j--) {
            x.setChildInPlace(j + 1, x.getChild(j));
        }
        x.setChildInPlace(i + 1, z);
        for (int j = x.get_n() - 1; j >= i; j--) {
            x.setKeyInPlace(j + 1, x.getKeyInPlace(j));
        }
        x.setKeyInPlace(i, y.getKeyInPlace(_t - 1));
        y.setKeyInPlace(_t - 1, null);
        x.set_n(x.get_n() + 1);
    }

    private void insert(String k) {
        BTreeNode r = root;
        /*
        if node is full, create a new node to be the new root and set it as father.
         */
        if (r.get_n() == 2 * _t - 1) {
            BTreeNode s = new BTreeNode(_t);
            s.setLeaf(false);
            s.setChildInPlace(0, r);
            root = s;
            splitChild(s, 0, r);
            r = s;
        }
        insertNonFull(r, k);
    }

    private void insertNonFull(BTreeNode x, String key) {
        int i = x.get_n() - 1;
        if (x.is_leaf()) { // if node is leaf, just find the place to add and add
            while (i >= 0 && key.compareTo(x.getKeyInPlace(i)) < 0) {
                x.setKeyInPlace(i + 1, x.getKeyInPlace(i));
                i--;
            }
            x.setKeyInPlace(i + 1, key);
            x.set_n(x.get_n() + 1);
        } else {
            /*if node is not a leaf recursively continue and find the right node.
             *split in case needed
             */
            while (i >= 0 && key.compareTo(x.getKeyInPlace(i)) < 0) {
                i--;
            }
            i++;
            if (x.getChild(i).get_n() == 2 * _t - 1) { // if node is full
                splitChild(x, i, x.getChild(i));
                if (key.compareTo(x.getKeyInPlace(i)) > 0) {
                    i++;
                }
            }
            insertNonFull(x.getChild(i), key); // recursive call
        }
    }

    private void insertFriends() {
        for (int i = 0; i < _friends.length; i++) {
            insert(_friends[i]);
        }
    }

    public boolean areFriends(String sender, String receiver) {
        return search(sender + " & " + receiver) || search(receiver + " & " + sender);
    }

    private String BFS() {
        if (root != null) {
            bfsQueue.enqueue(root);
        }
        while (!(bfsQueue.isEmpty())) {
            BTreeNode x = bfsQueue.dequeue();
            for (int i = 0; i < x.getKeys().length; i++) {
                bTreeAsString += x.getKeyInPlace(i);
            }
            for (int i = 0; i < x.getChildren().length; i++) {
                if (x.getChild(i) != null) {
                    bfsQueue.enqueue(x.getChild(i));
                }
            }
        }
        return null;
    }

}
