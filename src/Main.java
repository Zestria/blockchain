package src;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        File dir = new File("./database");
        String[] files = dir.list();
        System.out.println(files.length);
        for(String file : files) System.out.println(file);

    }
}
