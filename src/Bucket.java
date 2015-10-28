/**
 * @author Christopher
 */
class Bucket {

    public Node first;

    public boolean get(String key) {
    /*Iterates through bucket to find key*/
        Node current = first;
        while (current != null) {
            if (current.word.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void put(String key) {
    /*Puts a new node within the bucket containing a word*/
        for (Node curr = first; curr != null; curr = curr.next) {
            if (key.equals(curr.word)) {
                return;                     //search hit: return
            }
        }
        first = new Node(key, first); //search miss: add new node
    }
}