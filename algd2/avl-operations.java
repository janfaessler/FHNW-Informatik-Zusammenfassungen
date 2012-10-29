  public T insert(int key, T value) {
    final Node<T> n = new Node<T>(key, value, null);
    return insert(this.root, n);
  }
  public T insert(Node<T> r, Node<T> n) {
    if (r == null) { //als root einfügen:
      size = 1;
      this.root = n;
    } else { // Stelle finden:
      if (n.key < r.key) {
        if (r.left == null) { // links anfügen:
          r.left = n;
          n.parent = r;
          size++;
          balance(r);
        } else return insert(r.left, n); //weiter links suchen
      } else if (n.key > r.key) {
        if (r.right == null) { // rechts anfügen:
          r.right = n;
          n.parent = r;
          size++;
          balance(r);
        } else return insert(r.right, n); // weiter rechts suchen
      } else {  // node.key==root.key  Schon vorhanden -> Wert ersetzen
        T oldValue = r.value;
        r.value = n.value;
        return oldValue;
      }
    }
    return null;
  }
  public T remove(int k) { return remove(k, root); }
  private T remove(int key, Node<T> t) {
    if (t == null) return null;// das ist kein Baum...
    else {
      if (t.key > key)      return remove(key, t.left);
      else if (t.key < key) return remove(key, t.right);
      else                  return remove(t);

    }
  }
  private T remove(Node<T> t) {
    T oldValue = null;
    size--;
    Node<T> r;
    if (t.left == null || t.right == null) { // Einzige Node (= root) entfernen:
      if (t.parent == null) {
        this.root = null;
        return oldValue;
      }
      r = t;
    } else { // Mit "Vorfahre" ersetzen:
      r = successor(t);
      t.key = r.key;
      oldValue = t.value;
      t.value = r.value;
    }
    Node<T> p; // Angehängte Elemente verschieben:
    if (r.left != null) p = r.left;
    else                p = r.right;
    if (p != null) p.parent = r.parent;

    if (r.parent == null) this.root = p;
    else {
      if (r == r.parent.left) r.parent.left = p;
      else                    r.parent.right = p;
      balance(r.parent);
    }
    return oldValue;
  }
  private void balance(Node<T> node) {
    int balance = node.balance();
    if (balance <= -2) {
      if (height(node.left.left) >= height(node.left.right)) node = rotateRight(node);
      else node = rotateLeftRight(node);
    } else if (balance >= 2) {
      if (height(node.right.right) >= height(node.right.left)) node = rotateLeft(node);
      else node = rotateRightLeft(node);
    }
    if (node.parent != null) balance(node.parent);
    else                     this.root = node;
  }
  private static <X> Node<X> rotateRight(final Node<X> r) {
    final Node<X> pivot = r.left;
    final Node<X> left = pivot.left;
    if (r.parent != null) { // Make pivot the new root:
      if (r.parent.left == r) r.parent.left = pivot;
      else                    r.parent.right = pivot;
    }
    pivot.parent = r.parent;
    // Append /B\ to (5):
    r.left = pivot.right;
    if (r.left != null) r.left.parent = r;
    // Make (5) the right of (3)
    pivot.right = r;
    r.parent = pivot;
    return pivot;
  }
  private static <X> Node<X> rotateLeft(final Node<X> r) {
    final Node<X> pivot = r.right;
    final Node<X> right = pivot.right;
    if (r.parent != null) { // Make pivot the new root:
      if (r.parent.left == r) r.parent.left = pivot;
      else                    r.parent.right = pivot;
    }
    pivot.parent = r.parent;
    // Append /B\ to (3):
    r.right = pivot.left;
    if (r.right != null) r.right.parent = r;
    // Make (3) the left of (5):
    pivot.left = r;
    r.parent = pivot;
    return pivot;
  }
  private static <X> Node<X> rotateLeftRight(Node<X> node) {
    node.left = rotateLeft(node.left);
    return rotateRight(node);
  }
  private static <X> Node<X> rotateRightLeft(Node<X> node) {
    node.right = rotateRight(node.right);
    return rotateLeft(node);
  }