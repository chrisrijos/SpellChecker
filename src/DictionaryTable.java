/**
 * @author: clr45
 * Creates the hashtable by implementing a linked list of buckets which contain nodes.
 * also controls the hashing of the hashtable and the printing of its metrics
 */
import java.io.*;

public class DictionaryTable {

    public String workingDir = System.getProperty("user.dir");//stores uses working director

    public String DICT_FILENAME = "\\src\\Dictionary.txt";
    public String D_PATH = workingDir + DICT_FILENAME;
    public String REPORT_FILE = "\\src\\Report.txt";
    public String R_PATH = workingDir + REPORT_FILE;

    //loads buckets and LoadFactor
    private int initLoadFactor;
    final private Bucket[] array;

    public DictionaryTable() {
        try {
            initLoadFactor = (countLines(D_PATH) % 31); //sets load factor to the count of the dictionary % 31
        } catch (IOException e) {
            e.printStackTrace();
        }

        array = new Bucket[initLoadFactor];
        for (int i = 0; i < initLoadFactor; i++) {
            array[i] = new Bucket();
        }
    }
    protected int getTableLoad(){
    /*Returns initial table load*/
        return initLoadFactor;
    }
    private int hash(String key) {
    /*Hash function hashes key and forces the return of a positive integer*/
        return (key.hashCode() & 0x7fffffff) % initLoadFactor;
    }
    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
    public void add(String key) {
    /*hashes key and puts into hash table*/
        array[hash(key)].put(key);
    }
    public boolean contains(String input) {
    /*Checks to see if input matches */
        input = input.toLowerCase();
        return array[hash(input)].get(input);
    }
    public void build(String file) {
    /*Builds filepath for DICTIONARY TABLE */
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                add(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /*Statistics*/
    public void printStats(){
    /*Reads over hashtable to determine % of bucket usage*/

        double bucketFreq = avgChainLength();
        /*Print to table*/
        FileWriter fw = null;
        try {
            fw = new FileWriter(R_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
                fw.write("\n--------------Load Factor-----------------------");
                fw.write("\n LF: " + getTableLoad());
                fw.write("\n--------------Bucket Usage----------------------");
                fw.write("\nTotal Number of Buckets: " + bucketSize());
                fw.write("\nUsage % :" + bucketUsage() + "%");
                fw.write("\n-----------------------------------------------");
                fw.write("\n-------------Avg Chain Length------------------");
                fw.write("\n" + bucketFreq);
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public double avgChainLength(){
    //returns the average length of the hashtable chains
       int buckSize = bucketSize();
       int[] bucketFreq = new int[bucketSize()];
       for(Bucket b : array){
           bucketFreq[b.hashCode() % buckSize]++;
       }
        int count = 0;
        for(int i : bucketFreq){
            if(i > 0) count++;
        }
        return (double) array.length / count;
    }
    public double bucketUsage(){
    //returns the percentage of the buckets used
        int buckSize = bucketSize();
        int[] bucketFreq = new int[buckSize];
        for(Bucket b : array){
            bucketFreq[b.hashCode() % buckSize]++;
        }
        int count = 0;
        for(int i : bucketFreq){
            if(i > 0) count++;
        }
        return (double) count / buckSize;
    }
    public int log2(int number){
    // returns log 2 of number
        if(number == 0)
            return 0;
        return 31 - Integer.numberOfLeadingZeros(number);
    }
    public int bucketSize(){
    //returns the size of the buckets by bitshifting
        int n = array.length;
        int bucketSize = 2 << log2(n);
        if(bucketSize * initLoadFactor <= n){
            bucketSize <<= 1;
        }
        return bucketSize;
    }

}