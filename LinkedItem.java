public class LinkedItem<Item> {
   private final Item value;
   private LinkedItem<Item> next;
   private LinkedItem<Item> prev;

   public LinkedItem(Item value) {
      this.value = value;
   }

   public Item getValue() {
      return value;
   }

   public LinkedItem<Item> getNext() {
      return next;
   }

   public LinkedItem<Item> getPrev() {
      return prev;
   }

   public void setNext(LinkedItem<Item> item) {
      next = item;
   }

   public void setPrev(LinkedItem<Item> item) {
      prev = item;
   }
}
