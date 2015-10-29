/**
 * @author: ChrisRIjos
 */
import java.io.*;

public class DictionaryTable {

    public String workingDir = System.getProperty("user.dir");//stores uses working director
    public String DICT_FILENAME = "\\src\\Dictionary.txt";
    public String D_PATH = workingDir + DICT_FILENAME;

    private int initLoadFactor; //determines size of dictionary by dividing count of file by 75%
    final private Bucket[] array;

    public DictionaryTable() {
        setTableLoad(); //sets tables

        array = new Bucket[initLoadFactor];
        for (int i = 0; i < initLoadFactor; i++) {
            array[i] = new Bucket();
        }
    }
    protected int getTableLoad(){
    /*Returns initial table load*/
        return initLoadFactor;
    }
    protected void setTableLoad(){
    /*Sets table initial load*/
        try {
            initLoadFactor = countLines(D_PATH) % 31; //sets initial loadfactor to the remainder of the count of lines mod 31
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    //call hash() to decide which bucket to put it in, do it.
    public void add(String key) {
    /*hashes key and puts into hash table*/
        array[hash(key)].put(key);
    }

    //call hash() to find what bucket it's in, get it from that bucket.
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
    public Double bucketUsagePercentage(){
    /*Reads over hashtable to determine % of bucket usage*/
        return 0.0;
    }
    public Double avgChainLength(){
    /*Determines the average length of the chain*/
        return 0.0;
    }

    public void printStats(){
    /*Displays Stats for Avg Chain link, % of buckets full, Max chain length*/
        System.out.println("LoadFactor: " + getTableLoad());
    }
}