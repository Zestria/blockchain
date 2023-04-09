package src.tools;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ByteArrayToStream {
    static String streamToString(InputStream input, Charset encoding) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(input, encoding));
        StringBuffer sb = new StringBuffer(1024);

        String line = br.readLine();
        while(line!=null) {
            sb.append(line);
            line = br.readLine();
        }
        br.close();

        return sb.toString();
    }
}