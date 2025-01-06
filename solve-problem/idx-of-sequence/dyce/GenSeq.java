// 101010111010100101010000000 len=27 <90000000> 
import java.io.*;
public class GenSeq {
    public static void main(String[] args) throws Exception {
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream("seq.txt"))) {
            for (int i =0; i <1000; i++) {
                String bf = Integer.toBinaryString(i);
                for (int j =0; j <bf.length(); j++)
                    if (bf.charAt(j) == '1')
                        out.write((i+"\n").getBytes());
            }
            out.flush();
        }
    }
}
