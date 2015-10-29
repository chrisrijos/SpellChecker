/**
 * @author: ChrisRIjos
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
    public void bucketUsagePercentage(){
    /*Reads over hashtable to determine % of bucket usage*/
        int buckCount = 0;
        int x = 0;
        int y = 0;
        for(Bucket i : array){
            buckCount++;
            if(!(i.first==null)){
                //buckets in use
                x++;
            }
            if(i.first==null){
                //buckets not in use
                y++;
            }
        }
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
                fw.write("\nTotal Number of Buckets: " + buckCount);
                fw.write("\nBuckets In Use : " + x);
                fw.write("\nBuckets Not in Use: " + y);
                fw.write("\nUsage % :" + (x / buckCount) + "%");
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void avgChainLength(){
    }
    public void maxChainLength(){

    }

}