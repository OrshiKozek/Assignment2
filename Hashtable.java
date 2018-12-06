import java.util.ArrayList;
import java.lang.Math;

public class Hashtable{

    private int size;
    private int numBuckets;
    private ArrayList<Hashnode> buckets;

    public class Hashnode {
        private String key;
        private String value;
        private Hashnode next;

        public Hashnode(String k, String v){
            this.key = k;
            this.value = v;
            this.next = null;
        }
    }//hashnode class

    public Hashtable(){
        this.size = 0;
        this.numBuckets = 314527;
        this.buckets = new ArrayList<>();

        for (int i = 0; i < numBuckets; i++)
            buckets.add(null);
    }

    public boolean containsKey(String key) { //find key
//        Returns “true” if a key/value object pair (with the key matching the
//                argument and any value).
        int bucketID = getBucket(key);
        Hashnode node = buckets.get(bucketID);
        while (node != null) {
            if (node.key.equals(key))
                return true;
        }
        return false;

    }

    public String get (String key) { //find
//        Returns the value associated with the key which is passed as an
//        argument; returns null if no key/value pair is contained by the
//        Hashtable instance.

        int bucketID = getBucket(key);
        Hashnode node = buckets.get(bucketID);
        while (node != null) {
            if (node.key.equals(key))
                return node.value;
            node = node.next;
        }
        //if not found,
        return null; //or throw error
    }

    public void put (String key, String value) {
//        Adds the key/value pair into the Hashtable instance. If there is an
//        existing key/value pair, the Hashtable instance replaces the stored
//        value with the argument value.
        int bucketID = getBucket(key);
        Hashnode node = buckets.get(bucketID);
        while (node != null) {
            if (node.key.equals(key)){
                node.value = value;
//                return;
                break;
            }
            node = node.next;
        }

        //check the threshold
        double threshold = 0.8;
        double dblSize = 1.0 * size;
        double dblNumBuckets = 1.0 * numBuckets;

        if(dblSize / dblNumBuckets > threshold) {
            resize();
        }

        //add the new node
        Hashnode newNode = new Hashnode(key, value);
        newNode.next = buckets.get(bucketID);
        buckets.set(bucketID, newNode);
        size++;
    }

    public String remove (String key){
//        Removes the key/value pair from the Hashtable instance and
//        returns the value associated with the key to the caller. Throws an
//        Exception instance if the key is not present in the Hashtable
//        instance.

        int bucket_id = getBucket(key); //DEFINE THIS FUNCTION!!!!
        Hashnode node = buckets.get(bucket_id);
        Hashnode prev = null;
        while(node != null){
            if (node.key.equals(key))
                break;
            prev = node;
            node = node.next;
        }
        //A- node is null- key is not in the list
        if (node == null)
            return null;
        size--;
        //B- prev is null; delete head
        if (prev == null)
            buckets.set(bucket_id, node.next);
        else{ //delete from middle
            prev.next = node.next;
        }

        return node.value;

    }

    private int getBucket(String key){
        int hashcode = key.hashCode();
        return Math.abs(hashcode) % numBuckets;
    }

    private void resize(){
        ArrayList newArray = new ArrayList(numBuckets * 2);
        for (int i = 0; i < numBuckets*2; i++)
            newArray.add(null);

        for (Hashnode hashNode : buckets) {
            while (hashNode != null) {
                put(hashNode.key, hashNode.value);
                hashNode = hashNode.next;
            }
        }

        buckets = newArray;
    }

}
