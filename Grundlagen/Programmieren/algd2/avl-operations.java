public T insert(int key, T value) {
    final Node<T> n = new Node<T>(key, value, null);
    return insert(this.root, n);
}
public T insert(Node<T> r, Node<T> n) {
    if (r == null) { size = 1; this.root = n; } 
    else {
      if (n.key < r.key) {
        if (r.left == null) { // links
          r.left = n; n.parent = r; size++; balance(r);
        } else return insert(r.left, n);
      } else if (n.key > r.key) {
        if (r.right == null) { // rechts
          r.right = n; n.parent = r; size++; balance(r);
        } else return insert(r.right, n);
      } else {
        T oldValue = r.value; r.value = n.value; return oldValue;
      }
    } return null;
}
public T remove(int k) { return remove(k, root); }
private T remove(int key, Node<T> t) {
    if (t == null) return null;
    else {
      if (t.key > key)      return remove(key, t.left);
      else if (t.key < key) return remove(key, t.right);
      else                  return remove(t);
    }
}
private T remove(Node<T> t) {
    T oldValue = null; size--; Node<T> r;
    if (t.left == null || t.right == null) {
      if (t.parent == null) {
        this.root = null; return oldValue;
      } r = t;
    } else {
      r = successor(t);
      t.key = r.key; oldValue = t.value; t.value = r.value;
    } Node<T> p = (r.left != null?r.left:r.right);
    if (p != null)        p.parent = r.parent;
    if (r.parent == null) this.root = p;
    else {
      	if (r == r.parent.left) r.parent.left = p;
      	else                    r.parent.right = p;
      	balance(r.parent);
    } return oldValue;
}
private Node<T> successor(Node<T> predec) {
    if (predec.right != null) { Node<T> r = predec.right;
      while (r.left != null) r = r.left;
      return r;
    } else { Node<T> p = predec.parent;
      while (p != null && predec == p.right) {
        predec = p; p = predec.parent;
      } return p;
    }
}
private void balance(Node<T> node) {
    int balance = node.balance();
    if (balance <= -2) {
      if (height(node.left.left) >= height(node.left.right)) 
      		node = rotateRight(node);
      else node = rotateLeftRight(node);
    } else if (balance >= 2) {
      if (height(node.right.right) >= height(node.right.left)) 
      		node = rotateLeft(node);
      else node = rotateRightLeft(node);
    }
    if (node.parent != null) balance(node.parent);
    else                     this.root = node;
}
private static <X> Node<X> rotateLeftRight(Node<X> node) {
    node.left = rotateLeft(node.left); return rotateRight(node);
}
private static <X> Node<X> rotateRightLeft(Node<X> node) {
    node.right = rotateRight(node.right); return rotateLeft(node);
}
private static <X> Node<X> rotateRight(final Node<X> r) {
    Node<X> pivot = r.left, left = pivot.left;
    if (r.parent != null) {
      	if (r.parent.left == r) r.parent.left = pivot;
      	else                    r.parent.right = pivot;
    }
    pivot.parent = r.parent; r.left = pivot.right;
    if (r.left != null) r.left.parent = r;
    pivot.right = r; r.parent = pivot;
    return pivot;
}
private static <X> Node<X> rotateLeft(final Node<X> r) {
    Node<X> pivot = r.right, right = pivot.right;
    if (r.parent != null) {
      if (r.parent.left == r) r.parent.left = pivot;
      else                    r.parent.right = pivot;
    }
    pivot.parent = r.parent; r.right = pivot.left;
    if (r.right != null) r.right.parent = r;
    pivot.left = r; r.parent = pivot;
    return pivot;
}