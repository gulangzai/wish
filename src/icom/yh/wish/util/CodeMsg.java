package icom.yh.wish.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CodeMsg extends Properties{

    
   public CodeMsg() {
       InputStream is = getClass().getResourceAsStream("/codeMsg.properties");
       try {
           load(is);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   
   public static void main(String[] args) {
	}
   
}
