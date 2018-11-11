package Supervised;

import java.io.*;

public class SaveRestoreObjFromFile {
    public static void saveToFile(String outputFileName, Object object) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(outputFileName)));
        out.writeObject(object);
        out.close();
    }

    public static Object restoreFromFile(String inputFileName) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(inputFileName)));
        Object object = in.readObject();
        in.close();
        return object;
    }
}
