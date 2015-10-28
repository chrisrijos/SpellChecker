/**
 * Created by Rijos on 10/26/2015.
 */
import java.io.*;

public class DictionaryTable {

    public String workingDir = System.getProperty("user.dir");//stores uses working director
    public String DICT_FILENAME = "\\src\\dictionary.txt";
    public String D_PATH = workingDir + DICT_FILENAME;

    private int DICT_SIZE; //determines size of dictionary
    final private Bucket[] array;

    public DictionaryTable() {
        setTableLoad(); //sets default table load to 50% initial load

        array = new Bucket[DICT_SIZE];
        for (int i = 0; i < DICT_SIZE; i++) {
            array[i] = new Bucket();
        }
    }
    private void setTableLoad(){
        try {
            DICT_SIZE = countLines(D_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % DICT_SIZE;
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
        array[hash(key)].put(key);
    }

    //call hash() to find what bucket it's in, get it from that bucket.
    public boolean contains(String input) {
        input = input.toLowerCase();
        return array[hash(input)].get(input);
    }

    public void build(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                add(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    //this method is used in my unit tests
    public String[] getRandomEntries(int num) {
        String[] toRet = new String[num];
        for (int i = 0; i < num; i++) {
            //pick a random bucket, go out a random number
            Node n = array[(int) Math.random() * DICT_SIZE].first;
            int rand = (int) Math.random() * (int) Math.sqrt(num);

            for (int j = 0; j < rand && n.next != null; j++) n = n.next;
            toRet[i] = n.word;


        }
        return toRet;
    }
}