/**
 * @clr45
 * Creates node holding data within the buckets
 */
class Node {

    String word;
    Node next;

    public Node(String key, Node next) {
    /*Node constructor*/
        this.word = key;
        this.next = next;
    }

}