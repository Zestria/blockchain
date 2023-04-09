package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Tool {
    static void writeFile(String name, String data) {
        if(name.length()==0 || data.length()==0) return;
        
        File file = new File(name);
        try(FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter bf = new BufferedWriter(osw)) {
            bf.write(data);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    static String readFile(String name) { // читает только одну строку
        File file = new File(name);
        String line="";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
    static String createJSON(Block block) throws Exception {
        JSONObject resultJSON = new JSONObject();

        resultJSON.put("index", block.index);
        resultJSON.put("data", block.data);
        resultJSON.put("prevHash", block.prevHash);
        resultJSON.put("timestamp", block.timestamp);
        resultJSON.put("nonce", block.nonce);

        String json = resultJSON.toString();
        return json;
    }
    static Block parseJSON(String input) throws Exception { // проблема тут
        Object obj = new JSONParser().parse(input);
        // Кастим obj в JSONObject
        JSONObject jsonObj = (JSONObject) obj;
        
        int index = Integer.parseInt(jsonObj.get("index").toString()); // этот обработчик JSON не разбирает int, он почему то long ставит
        String data = (String) jsonObj.get("data");
        String prevHash = (String) jsonObj.get("prevHash");
        long timestamp = (long) jsonObj.get("timestamp");
        int nonce = Integer.parseInt(jsonObj.get("nonce").toString());
        Block block = new Block(prevHash, data, index, timestamp, nonce);
        return block;
    }
}
