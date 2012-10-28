package ch.fhnw.claudemartin.algd2;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class AVLTreeTest {

  @Test
  public void testIsEmpty() {
    AVLTree<Object> tree = new AVLTree<>();
    Assert.assertTrue(tree.isEmpty());

    tree.insert(1, "eins");
    Assert.assertFalse(tree.isEmpty());
    testTree(tree);
    
    tree.remove(1);
    Assert.assertTrue(tree.isEmpty());
  }

  @Test
  public void testInsert() {
    AVLTree<Object> tree = new AVLTree<>();
    Assert.assertEquals(0, tree.size());
    Assert.assertNull(tree.find(1));

    Object eins = new Object();
    tree.insert(1, eins);
    Assert.assertEquals(1, tree.size());
    Assert.assertSame(eins, tree.find(1));
    testTree(tree);
    
    // LEFT LEFT CASE:
    tree = new AVLTree<>();
    tree.insert(5, "Fünf");
    Assert.assertEquals("Fünf", tree.getRoot().value);
    tree.insert(3, "Drei");
    Assert.assertEquals("Fünf", tree.getRoot().value);
    tree.insert(2, "Zwei");
    Assert.assertEquals("Drei", tree.getRoot().value);
    Assert.assertEquals("Zwei", tree.getRoot().left.value);
    Assert.assertEquals("Fünf", tree.getRoot().right.value);
    testTree(tree);
    
    // RIGHT RIGHT CASE
    tree = new AVLTree<>();
    tree.insert(3, "Drei");
    Assert.assertEquals("Drei", tree.getRoot().value);
    tree.insert(5, "Fünf");
    Assert.assertEquals("Drei", tree.getRoot().value);
    tree.insert(7, "Sieben");
    Assert.assertEquals("Fünf", tree.getRoot().value);
    Assert.assertEquals("Drei", tree.getRoot().left.value);
    Assert.assertEquals("Sieben", tree.getRoot().right.value);
    testTree(tree);
    
    // LEFT RIGHT CASE
    tree = new AVLTree<>();
    tree.insert(5, "Fünf");
    Assert.assertEquals("Fünf", tree.getRoot().value);
    tree.insert(3, "Drei");
    Assert.assertEquals("Fünf", tree.getRoot().value);
    tree.insert(4, "Vier");
    Assert.assertEquals("Vier", tree.getRoot().value);
    Assert.assertEquals("Drei", tree.getRoot().left.value);
    Assert.assertEquals("Fünf", tree.getRoot().right.value);
    testTree(tree);

    // RIGHT LEFT CASE
    tree = new AVLTree<>();
    tree.insert(3, "Drei");
    Assert.assertEquals("Drei", tree.getRoot().value);
    tree.insert(5, "Fünf");
    Assert.assertEquals("Drei", tree.getRoot().value);
    tree.insert(4, "Vier");
    Assert.assertEquals("Vier", tree.getRoot().value);
    Assert.assertEquals("Drei", tree.getRoot().left.value);
    Assert.assertEquals("Fünf", tree.getRoot().right.value);
    testTree(tree);

  }

  @Test
  public void testRemove() {
    AVLTree<Object> tree = new AVLTree<>();
    Assert.assertEquals(0, tree.size());
    
    Object eins = new Object();
    tree.insert(1, eins);
    Assert.assertEquals(1, tree.size());
    testTree(tree);
    
    tree.remove(1);
    Assert.assertEquals(0, tree.size());
    Assert.assertNull(tree.find(1));
  }

  @Test
  public void testFirst() {
    AVLTree<Object> tree = new AVLTree<>();
    Assert.assertNull(tree.first());

    Object eins = new Object();
    tree.insert(1, eins);
    Assert.assertSame(eins, tree.first());
    testTree(tree);
    
    Object zwei = new Object();
    tree.insert(2, zwei);
    Assert.assertSame(eins, tree.first());
    testTree(tree);
  }

  @Test
  public void testLast() {
    AVLTree<Object> tree = new AVLTree<>();
    Assert.assertNull(tree.last());

    Object eins = new Object();
    tree.insert(1, eins);
    Assert.assertSame(eins, tree.last());

    Object zwei = new Object();
    tree.insert(2, zwei);
    Assert.assertSame(zwei, tree.last());
  }

  @Test
  public void testFind() {
    AVLTree<Object> tree = new AVLTree<>();

    final int N = 20;

    for (int i = 0; i < N; i++) {
      final String val = String.valueOf(i);
      tree.insert(i, val);
      testTree(tree);
      Assert.assertEquals(val, tree.last());
    }

    Assert.assertEquals(N, tree.size());

    for (int i = 0; i < N; i++) {
      Assert.assertEquals(String.valueOf(i), tree.find(i));
    }

    Assert.assertEquals(String.valueOf(0), tree.first());
    Assert.assertEquals(String.valueOf(N - 1), tree.last());
  }

  @Test
  public void testRandom() throws Exception {
    AVLTree<Object> tree = new AVLTree<>();
    final int N = 100;
    Random rng = new Random();
    for (int i = 0; i < N; i++) {
      final int rnd = rng.nextInt(N)-(N/2);
      final String val = String.valueOf(rnd);
      tree.insert(rnd, val);
      testTree(tree);
    }
  }
  
  static void testTree(AVLTree<?> tree) {
    testTree(tree.getRoot());
    Assert.assertEquals(tree.toList().size(), tree.size());
  }

  static void testTree(AVLTree.Node<?> root) {
    if (root == null)
      return;
    if (root.left != null) {
      Assert.assertEquals(root, root.left.parent);
      testTree(root.left);
    }
    if (root.right != null) {
      Assert.assertEquals(root, root.right.parent);
      testTree(root.right);
    }
  }
}
