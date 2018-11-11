package Supervised;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVReader implements Serializable {
    private String fileName = "data.csv";
    private ArrayList<String[]> data = new ArrayList<>();

    CSVReader(String fileName) {
        this.fileName = fileName;
    }

    public void importData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        String str;
        while ((str = br.readLine()) != null) {
            data.add(str.split(","));
        }
        br.close();
    }

    public int getNumberOfEntries() {
        return data.size();
    }

    public ArrayList<String> getEntry(int i) {
        return new ArrayList<String>(Arrays.asList(data.get(i)));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("--------------------------r---------------------------");
        CSVReader r = new CSVReader("data.csv");
        r.importData();
        for (int i = 0; i < r.getNumberOfEntries(); i++) {
            for (String str : r.getEntry(i))
                System.out.print(str + "  ");
            System.out.println();
        }
        System.out.println("Number of entries: " + r.getNumberOfEntries());

        System.out.println("--------------------------r2---------------------------");
        SaveRestoreObjFromFile.saveToFile("data2.csv", r);
        CSVReader r2 = (CSVReader) SaveRestoreObjFromFile.restoreFromFile("data2.csv");
        for (int i = 0; i < r2.getNumberOfEntries(); i++) {
            for (String str : r2.getEntry(i))
                System.out.print(str + "  ");
            System.out.println();
        }
        System.out.println("Number of entries: " + r2.getNumberOfEntries());
    }
}
