package ch.fhnw.claudemartin.algd2;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * Dies ist ein <a href="http://de.wikipedia.org/wiki/AVL-Baum">AVL-Baum</a>. Gemacht für AlgD2 bei Veitschegger.
 * </p>
 * 
 * <p>
 * Es geht in erster Linie darum, dass es ein echter, balancierter AVL-Baum ist. Das wird erreicht indem folgende
 * Operationen implementiert sind:
 * </p>
 * <a href="http://upload.wikimedia.org/wikipedia/commons/c/c4/Tree_Rebalancing.gif" ><img
 * src="http://upload.wikimedia.org/wikipedia/commons/c/c4/Tree_Rebalancing.gif" width="225px" height="158px"
 * alt="balancierung von AVL-Baum" /></a>
 * 
 * @author Claude Martin
 * 
 * @param <T>
 *          Typ der Elemente, die vom Baum gemapped werden werden. Schlüssel sind aber immer <code>int</code>.
 */
public final class AVLTree<T> {

  private Node<T> root = null;
  private int size = 0;

  public AVLTree() {
    root = null;
  }

  public AVLTree(int key, T value) {
    insert(key, value);
    assert size == 1;
  }

  Node<T> getRoot() {
    return root;
  }

  public boolean isEmpty() {
    assert (root == null) == (size == 0);
    return root == null;
  }

  public int size() {
    return size;
  }

  public void insert(int key, T value) {
    final Node<T> n = new Node<T>(key, value, null);
    insert(this.root, n);
  }

  public void insert(Node<T> r, Node<T> n) {
    if (r == null) {
      assert size == 0;
      size = 1;
      this.root = n;
    } else {
      if (n.key < r.key) {
        if (r.left == null) {
          r.left = n;
          n.parent = r;
          size++;
          balance(r);
        } else {
          insert(r.left, n);
        }
      } else if (n.key > r.key) {
        if (r.right == null) {
          r.right = n;
          n.parent = r;
          size++;
          balance(r);
        } else {
          insert(r.right, n);
        }
      } else {// node.key==root.key
        // Schon vorhanden -> Wert ersetzen:
        r.value = n.value;
      }
    }
  }

  public void remove(int k) {
    remove(k, root);
  }

  private void remove(int key, Node<T> t) {
    if (t == null) {
      return;// das ist kein Baum...
    } else {
      if (t.key > key) {
        remove(key, t.left);
      } else if (t.key < key) {
        remove(key, t.right);
      } else if (t.key == key) {
        remove(t);
      }
    }
  }

  private void remove(Node<T> t) {
    size--;
    Node<T> r;
    if (t.left == null || t.right == null) {
      if (t.parent == null) {
        assert this.root == t;
        this.root = null;
        return;
      }
      r = t;
    } else {
      r = successor(t);
      t.key = r.key;
    }

    Node<T> p;
    if (r.left != null) {
      p = r.left;
    } else {
      p = r.right;
    }

    if (p != null) {
      p.parent = r.parent;
    }

    if (r.parent == null) {
      this.root = p;
    } else {
      if (r == r.parent.left) {
        r.parent.left = p;
      } else {
        r.parent.right = p;
      }
      balance(r.parent);
    }
  }

  private void balance(Node<T> node) {
    int balance = node.balance();
    if (balance <= -2) {
      if (height(node.left.left) >= height(node.left.right)) {
        node = rotateRight(node);
      } else {
        node = rotateLeftRight(node);
      }
    } else if (balance >= 2) {
      if (height(node.right.right) >= height(node.right.left)) {
        node = rotateLeft(node);
      } else {
        node = rotateRightLeft(node);
      }
    }
    if (node.parent != null)
      balance(node.parent);
    else
      this.root = node;
  }

  private Node<T> successor(Node<T> predec) {
    if (predec.right != null) {
      Node<T> r = predec.right;
      while (r.left != null)
        r = r.left;
      return r;
    } else {
      Node<T> p = predec.parent;
      while (p != null && predec == p.right) {
        predec = p;
        p = predec.parent;
      }
      return p;
    }
  }

  private static int height(Node<?> t) {
    return t == null ? 0 : t.height();
  }

  private static <X> Node<X> first(Node<X> t) {
    if (t == null)
      return t;
    while (t.left != null)
      t = t.left;
    return t;
  }

  public T first() {
    final Node<T> first = first(root);
    return first == null ? null : first.value;
  }

  private <X> Node<X> last(Node<X> t) {
    if (t == null)
      return t;
    while (t.right != null)
      t = t.right;
    return t;
  }

  public T last() {
    final Node<T> last = last(root);
    return last == null ? null : last.value;
  }

  private Node<T> find(int key, Node<T> t) {
    // HINWEIS: Dies könnte auch per Rekursion gemacht werden!
    while (t != null)
      if (key < t.key)
        t = t.left;
      else if (key > t.key)
        t = t.right;
      else
        return t;
    return null;
  }

  public T find(int key) {
    if (root == null)
      return null;
    Node<T> node = find(key, root);
    return node == null ? null : node.value;
  }

  public void print() {
    print(root);
  }

  private void print(Node<T> t) {
    if (t != null) {
      print(t.left);
      System.out.println(t.key + "=" + t.value + ";");
      print(t.right);
    }
  }

  /** RIGHT ROTATION */
  private static <X> Node<X> rotateRight(final Node<X> r) {
    // root will be right child of pivot
    // pivot will be the new root

    final Node<X> pivot = r.left;
    final Node<X> left = pivot.left;

    // Rotation:
    // Make pivot the new root:
    if (r.parent != null) {
      if (r.parent.left == r)
        r.parent.left = pivot;
      else
        r.parent.right = pivot;
    }
    pivot.parent = r.parent;

    // Append /B\ to (5):
    r.left = pivot.right;
    if (r.left != null)
      r.left.parent = r;

    // Make (5) the right of (3)
    pivot.right = r;
    r.parent = pivot;

    assert pivot.right == r;
    assert pivot.left == left;
    assert left == null || pivot == left.parent;
    assert r.parent == pivot;
    return pivot;
  }

  /** LEFT ROTATION */
  private static <X> Node<X> rotateLeft(final Node<X> r) {
    // root will be left child of pivot
    // pivot will be the new root

    final Node<X> pivot = r.right;
    final Node<X> right = pivot.right;

    // Rotation:
    // Make pivot the new root:
    if (r.parent != null) {
      if (r.parent.left == r)
        r.parent.left = pivot;
      else
        r.parent.right = pivot;
    }
    pivot.parent = r.parent;

    // Append /B\ to (3):
    r.right = pivot.left;
    if (r.right != null)
      r.right.parent = r;

    // Make (3) the left of (5):
    pivot.left = r;
    r.parent = pivot;

    assert pivot.left == r;
    assert pivot.right == right;
    assert right == null || right.parent == pivot;
    assert r.parent == pivot;
    return pivot;
  }

  /** LEFT RIGHT CASE */
  private static <X> Node<X> rotateLeftRight(Node<X> node) {
    node.left = rotateLeft(node.left);
    return rotateRight(node);
  }

  /** RIGHT LEFT CASE */
  private static <X> Node<X> rotateRightLeft(Node<X> node) {
    node.right = rotateRight(node.right);
    return rotateLeft(node);
  }

  public List<Integer> toKeyList() {
    List<Integer> list = new LinkedList<>();
    klist(root, list);
    return list;
  }

  private void klist(Node<T> node, List<Integer> list) {
    if (node == null)
      return;
    klist(node.left, list);
    list.add(node.key);
    klist(node.right, list);
  }

  public List<T> toList() {
    List<T> list = new LinkedList<>();
    list(root, list);
    return list;
  }

  @SuppressWarnings("unchecked")
  public T[] toArray(Class<? super T> c) {
    final List<T> list = toList();
    return (T[]) list.toArray((T[]) Array.newInstance(c, list.size()));
  }

  @Override
  public String toString() {
    return Arrays.toString(toArray(Object.class));
  }

  private void list(Node<T> node, List<T> list) {
    if (node == null)
      return;
    list(node.left, list);
    list.add(node.value);
    list(node.right, list);
  }

  public Map<Integer, T> toMap() {
    Map<Integer, T> map = new TreeMap<>();
    map(root, map);
    return map;
  }

  private void map(Node<T> node, Map<Integer, T> list) {
    if (node == null)
      return;
    map(node.left, list);
    list.put(node.key, node.value);
    map(node.right, list);
  }

  static final class Node<T> {
    int key = 0;
    T value = null;
    Node<T> left = null;
    Node<T> right = null;
    Node<T> parent = null;

    Node(int key, T value, Node<T> parent) {
      this.key = key;
      this.value = value;
      this.parent = parent;
    }

    @Override
    public String toString() {
      return key + "=" + value;
    }

    int height() {
      return height(this, 0);
    }

    int balance() {
      return height(right, 0) - height(left, 0);
    }

    private static int height(Node<?> node, int height) {
      if (node == null || height > 2)
        return height;
      return Math.max(height(node.left, height + 1), height(node.right, height + 1));
    }
  }
}
