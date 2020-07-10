import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

   private int size = 0;
   private Item[] items = (Item[]) new Object[1];

   public RandomizedQueue() {
   }

   public boolean isEmpty() {
      return size == 0;
   }

   public int size() {
      return size;
   }

   public void enqueue(Item item) {
      if (item == null) throw new IllegalArgumentException("item cannot be null");

      if (size == items.length - 1) {
         doubleItems();
      }

      items[size] = item;
      size++;
   }

   public Item dequeue() {
      if (isEmpty()) throw new NoSuchElementException("queue is empty");

      int i = StdRandom.uniform(0, size);

      size--;
      Item item = items[i];
      items[i] = items[size];
      items[size] = null;

      if (size < items.length / 4) halveItems();

      return item;
   }

   public Item sample() {
      if (isEmpty()) throw new NoSuchElementException("queue is empty");

      int i = StdRandom.uniform(0, size);
      return items[i];
   }

   public Iterator<Item> iterator() {
      return new RandomizedQueueIterator();
   }

   private class RandomizedQueueIterator implements Iterator<Item> {

      private final int[] indexes;
      private int pos;

      public RandomizedQueueIterator() {
         indexes = StdRandom.permutation(size);
      }

      public boolean hasNext() {
         return pos < indexes.length;
      }

      public Item next() {
         if (!hasNext()) throw new NoSuchElementException("queue is empty");

         return items[indexes[pos++]];
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private void doubleItems() {
      Item[] newItems = (Item[]) new Object[2 * items.length];
      for (int i = 0; i < size; i++) {
         newItems[i] = items[i];
      }
      items = newItems;
   }

   private void halveItems() {
      Item[] newItems = (Item[]) new Object[items.length / 2];
      for (int i = 0; i < size; i++) {
         newItems[i] = items[i];
      }
      items = newItems;
   }

   public static void main(String[] args) {
      RandomizedQueue<Integer> queue = new RandomizedQueue<>();

      Assert.equal(0, queue.size(), "size should be zero on instantiation");
      Assert.isTrue(queue.isEmpty(), "queue should be empty on instantiation");

      boolean doesThrow = false;
      try {
         queue.enqueue(null);
      }
      catch (IllegalArgumentException ex) {
         doesThrow = true;
      }

      Assert.isTrue(doesThrow, "enqueue should throw exception when null item is added");

      doesThrow = false;
      try {
         queue.sample();
      }
      catch (NoSuchElementException ex) {
         doesThrow = true;
      }

      Assert.isTrue(doesThrow, "sample should throw exception when queue is empty");

      doesThrow = false;
      try {
         queue.dequeue();
      }
      catch (NoSuchElementException ex) {
         doesThrow = true;
      }

      Assert.isTrue(doesThrow, "dequeue should throw exception when queue is empty");

      Iterator<Integer> iter = queue.iterator();

      doesThrow = false;
      try {
         iter.next();
      }
      catch (NoSuchElementException ex) {
         doesThrow = true;
      }

      Assert.isTrue(doesThrow, "iterator.next should throw exception when queue is empty");

      queue.enqueue(1);

      Assert.equal(1, queue.size(), "size should be one when item is added");
      Assert.isFalse(queue.isEmpty(), "queue should not be empty when item is added");

      int item = queue.sample();

      Assert.equal(1, item, "item should be correctly returned when item is sampled");

      iter = queue.iterator();

      Assert.isTrue(iter.hasNext(), "iterator should have next when item is added");

      item = iter.next();

      Assert.equal(1, item, "iterator should return item when item is added");

      item = queue.dequeue();

      Assert.equal(0, queue.size(), "size should be zero when item is removed");
      Assert.isTrue(queue.isEmpty(), "queue should be empty when item is removed");
      Assert.equal(1, item, "item should be correctly returned when item is removed");

      iter = queue.iterator();

      Assert.isFalse(iter.hasNext(), "iterator should not have next when item is removed");

      queue.enqueue(2);
      queue.enqueue(3);

      Assert.equal(2, queue.size(), "size should be correct when more than one item is added");

      item = queue.sample();
      boolean changed = false;
      for (int i = 0; i < 100; i++) {
         if (item != queue.sample()) changed = true;
      }

      Assert.isTrue(changed, "sample returns different value");

      int first = queue.dequeue();
      int second = queue.dequeue();

      Assert.isTrue(first == 2 || first == 3, "dequeue should return correct value");
      Assert.isTrue(second == 2 || second == 3, "dequeue should return correct value");

      boolean isRandom = false;
      queue.enqueue(2);
      queue.enqueue(3);
      first = queue.dequeue();
      second = queue.dequeue();
      for (int n = 0; n < 100; n++) {
         queue.enqueue(2);
         queue.enqueue(3);

         int newFirst = queue.dequeue();
         int newSecond = queue.dequeue();
         if (first != newFirst && newFirst != newSecond) isRandom = true;
      }

      Assert.isTrue(isRandom, "dequeue should return random and different items");

      queue.enqueue(4);
      queue.enqueue(5);
      queue.enqueue(6);
      queue.enqueue(7);
      queue.enqueue(8);
      queue.enqueue(9);

      Assert.equal(6, queue.size(), "size should be correct when adding multiple items");

      int sum = 0;
      int expectedSum = 4 + 5 + 6 + 7 + 8 + 9;
      for (int it : queue) {
         sum += it;
      }

      Assert.equal(expectedSum, sum, "iterator should return all items");

      for (int n = 0; n < 6; n++) {
         queue.dequeue();
      }

      Assert.equal(0, queue.size(), "size should be zero when removing all items");
      Assert.isTrue(queue.isEmpty(), "queue should be empty when removing all items");

      if (Assert.allTestsPassed())
         StdOut.println("All tested passed.");
   }
}
