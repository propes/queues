import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

   private LinkedItem<Item> first;
   private LinkedItem<Item> last;
   private int size;

   public Deque() {
   }

   public boolean isEmpty() {
      return size == 0;
   }

   public int size() {
      return size;
   }

   public void addFirst(Item item) {
      if (item == null) throw new IllegalArgumentException("Item cannot be null");

      LinkedItem<Item> linkedItem = new LinkedItem<Item>(item);
      if (first == null) {
         if (last == null) last = linkedItem;
      }
      else {
         linkedItem.setNext(first);
         first.setPrev(linkedItem);
      }
      first = linkedItem;
      size++;
   }

   public void addLast(Item item) {
      if (item == null) throw new IllegalArgumentException("Item cannot be null");

      LinkedItem<Item> linkedItem = new LinkedItem<Item>(item);
      if (last == null) {
         if (first == null) first = linkedItem;
      }
      else {
         last.setNext(linkedItem);
         linkedItem.setPrev(last);
      }
      last = linkedItem;
      size++;
   }

   public Item removeFirst() {
      if (isEmpty()) throw new NoSuchElementException("Deque is empty");

      LinkedItem<Item> oldFirst = first;
      first = first.getNext();
      if (--size == 0) last = null;

      return oldFirst.getValue();
   }

   public Item removeLast() {
      if (isEmpty()) throw new NoSuchElementException("Deque is empty");

      LinkedItem<Item> oldLast = last;
      last = last.getPrev();
      if (--size == 0) first = null;

      return oldLast.getValue();
   }

   public Iterator<Item> iterator() {
      return new DequeIterator();
   }

   private class DequeIterator implements Iterator<Item> {
      private LinkedItem<Item> current;

      public DequeIterator() {
         current = first;
      }

      public boolean hasNext() {
         return current != null;
      }

      public Item next() {
         if (!hasNext()) throw new NoSuchElementException("Deque is empty");

         LinkedItem<Item> oldCurrent = current;
         current = current.getNext();
         return oldCurrent.getValue();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   public static void main(String[] args) {
      Deque<Integer> deque;
      Iterator<Integer> iter;

      deque = new Deque<Integer>();
      iter = deque.iterator();

      Assert.equal(0, deque.size(), "size should be zero on initialisation");
      Assert.isTrue(deque.isEmpty(), "deque should be empty on initialisation");
      Assert.isFalse(iter.hasNext(), "iterator.hasNext should be false on initialisation");

      boolean doesThrow = false;
      try {
         iter.next();
      }
      catch (NoSuchElementException ex) {
         doesThrow = true;
      }
      finally {
         Assert.isTrue(doesThrow, "iterator.next should throw on initialisation");
      }

      deque.addFirst(1);
      iter = deque.iterator();

      Assert.equal(1, deque.size(), "size should be one when item is added first");
      Assert.isFalse(deque.isEmpty(), "deque should not be empty when item is added first");
      Assert.isTrue(iter.hasNext(), "iterator.hasNext should be true when item is added first");

      deque.removeFirst();
      iter = deque.iterator();

      Assert.equal(0, deque.size(), "size should be zero when first item is removed");
      Assert.isTrue(deque.isEmpty(), "deque should be empty when first item is removed");
      Assert.isFalse(iter.hasNext(), "iterator.hasNext should be false when first item is removed");

      deque.addFirst(1);
      deque.addFirst(2);

      Assert.equal(2, deque.size(), "size should be updated when items are added first");

      iter = deque.iterator();
      int first = iter.next();
      int second = iter.next();

      Assert.equal(2, first, "first item is correct");
      Assert.equal(1, second, "second item is correct");

      int last = deque.removeLast();
      int secondLast = deque.removeLast();
      iter = deque.iterator();

      Assert.equal(1, last, "last value should be correctly returned");
      Assert.equal(2, secondLast, "second last value should be correctly returned");

      Assert.equal(0, deque.size(), "size should be zero when all items are removed from last");
      Assert.isFalse(iter.hasNext(),
                     "iterator.hasNext should be false when all items are removed from last");

      deque.addLast(3);
      deque.addLast(4);

      Assert.equal(2, deque.size(), "size should be updated when items are added last");

      iter = deque.iterator();
      first = iter.next();
      second = iter.next();

      Assert.equal(3, first, "first item is correct");
      Assert.equal(4, second, "second item is correct");

      first = deque.removeFirst();
      last = deque.removeLast();
      iter = deque.iterator();

      Assert.equal(3, first, "last value should be correctly returned");
      Assert.equal(4, last, "second last value should be correctly returned");

      Assert.equal(0, deque.size(),
                   "size should be zero when all items are removed from first then last");
      Assert.isTrue(deque.isEmpty(),
                    "deque should be empty when all items are removed from first then last");
      Assert.isFalse(iter.hasNext(),
                     "iterator.hasNext should be false when all items are removed from first then last");

      Deque<String> dequeStr = new Deque<String>();

      dequeStr.addLast("d");
      dequeStr.addFirst("c");
      dequeStr.addFirst("b");
      dequeStr.addLast("e");
      dequeStr.addFirst("a");
      dequeStr.addLast("f");

      Assert.equal(6, dequeStr.size(),
                   "size should be correct when multiple items are added first and last");

      String[] expected = { "a", "b", "c", "d", "e", "f" };
      int i = 0;
      for (String actual : dequeStr) {
         Assert.equal(expected[i++], actual,
                      "items should be in correct order when multiple items are added first and last");
      }

      i = 0;
      while (!dequeStr.isEmpty()) {
         String actual = dequeStr.removeFirst();
         Assert.equal(expected[i++], actual,
                      "items should be correctly returned when multiple items are removed from first");
      }

      Assert.equal(0, dequeStr.size(),
                   "size should be zero after multiple items are removed from first");

      if (Assert.allTestsPassed())
         StdOut.println("All tested passed.");
   }
}
