package chap3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAround {
    
    public static void main(String[] args) throws IOException{
    	String oneLine=processFile((BufferedReader br)->br.readLine());
    	System.out.println(oneLine);
    	String twoLine=processFile((BufferedReader b)->b.readLine()+b.readLine());
    	System.out.println(twoLine);
    }


    public static String processFile(BufferedReaderProcessor p) throws IOException{
        try(BufferedReader br=
                new BufferedReader(new FileReader("E:\\codes\\IJ\\Java8InAction\\src\\main\\resources\\lambdasinaction\\chap3\\data.txt"))){
            return p.process(br);
        }
    }

    public interface BufferedReaderProcessor{
        String process(BufferedReader b) throws IOException;
    }
}
