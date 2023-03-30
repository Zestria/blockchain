import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;

public class Main {
    public static void main(String[] args) {
        JSONObject resultJSON = new JSONObject();

        resultJSON.put("index", 0);
        resultJSON.put("data", "Genesis block");
        resultJSON.put("hash", null);
        resultJSON.put("timestamp", System.currentTimeMillis());

        String json = resultJSON.toString();
        System.out.println(json);

        File block = new File("block.txt");
        try(FileOutputStream fos = new FileOutputStream(block);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter bf = new BufferedWriter(osw)) {
            bf.write(json);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        
    }
    // написать функции записывания и чтения блоков
    
}
