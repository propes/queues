import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
   public static void main(String[] args) {
      if (args.length == 0) {
         StdOut.println("Please specify number of strings to sample.");
         return;
      }

      int k;
      try {
         k = Integer.parseInt(args[0]);
      }
      catch (NumberFormatException ex) {
         StdOut.println("Input must be a number greater than or equal to 0.");
         return;
      }

      if (k < 0) {
         StdOut.println("Input must be a number greater than or equal to 0.");
         return;
      }

      RandomizedQueue<String> queue = new RandomizedQueue<>();

      while (!StdIn.isEmpty()) {
         String str = StdIn.readString();
         queue.enqueue(str);
      }

      Iterator<String> iter = queue.iterator();
      for (int i = 0; i < k; i++) {
         if (iter.hasNext()) {
            StdOut.println(iter.next());
         }
      }
   }
}
