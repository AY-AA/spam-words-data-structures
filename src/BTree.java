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
    }

    private void splitChild(BTreeNode x, int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(_t);
        z.setLeaf(y.is_leaf());
        z.set_n(_t - 1);
        for (int j = 0; j <= _t - 1; j++) {
            z.setKeyInPlace(j, y.getKeyInPlace((j + _t)));
        }
        if (!y.is_leaf()) {
            for (int k = 0; k <= _t; k++) {
                z.setChildInPlace(k, y.getChild((k + _t)));
            }
        }
        y.set_n(_t - 1);
        for (int j = (x.get_n() + 1); j > i + 1; j--) {
            x.setChildInPlace(j + 1, x.getChild(j));
        }
        x.setChildInPlace(i + 1, z);
        for (int j = x.get_n(); j > i; j--) {
            x.setKeyInPlace(j + 1, x.getKeyInPlace(j));
        }
        x.setKeyInPlace(i, y.getKeyInPlace(_t));
        x.set_n(x.get_n() + 1);
    }

    private boolean search(String key) {
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
                // build the btree and insert
                insert(currentPairOfFriends);
                counter++;
            }
        } catch (IOException ex) {
            System.out.println("error: unable to read file '" + _fileName + "'");
        }
        if (counter > 0) {
            _treeSize = counter;
        }
    }

    private void insert(String k) {
        BTreeNode r = root;
        if (r.get_n() == 2 * _t - 1) {
            BTreeNode s = new BTreeNode(_t);
            s.setLeaf(false);
            s.set_n(0);
            s.setChildInPlace(1, r);
            splitChild(s, 1, r);
            r = s;
        }
        insertNonFull(r, k);
    }

    private void insertNonFull(BTreeNode x, String key) {
        int i = x.get_n();
        if (x.is_leaf()) {
            while (i >= 1 && key.compareTo(x.getKeyInPlace(i)) > 0) {
                x.setKeyInPlace(i + 1, x.getKeyInPlace(i));
                i--;
            }
            x.setKeyInPlace(i, key);
            x.set_n(x.get_n() + 1);
        } else {
            while (i >= 1 && key.compareTo(x.getKeyInPlace(i)) > 0) {
                i--;
            }
            i++;
            if (x.getChild(i).get_n() == 2 * _t - 1) {
                splitChild(x, i, x.getChild(i));
                if (key.compareTo(x.getKeyInPlace(i)) > 0) {
                    i++;
                }
            }
            insertNonFull(x.getChild(i), key);
        }
    }

    public void createFullTree(String path) {
        _fileName = path;
        initiatingFileHandler();
        getTreeSize();
        _friends = new String[_treeSize];
        getFriendsFromFile();
        insertFriends();
    }

    private void insertFriends(){
        for (int i = 0; i < _friends.length; i++) {
            insert(_friends[i]);
        }
    }

    public boolean areFriends(String sender, String receiver) {
//		search(sender + " & " + receiver );
//		search(receiver + " & " + sender );
//		if one of them found return true
        return false;
    }


}
