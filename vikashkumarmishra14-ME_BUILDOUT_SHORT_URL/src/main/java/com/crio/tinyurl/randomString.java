package com.crio.tinyurl;
import java.util.*;
import java.nio.charset.*;
public class randomString{
    public static String getRandomString(int n){
        byte []arr = new byte[256];
        Random r = new Random();
        r.nextBytes(arr); // to fill with random bytes
        String randomString = new String(arr, Charset.forName("UTF-8")); //converting from bytes to string
        StringBuffer s = new StringBuffer();  //to store the result
        String  AlphaNumericString = randomString.replaceAll("[^A-Za-z0-9]", ""); // replace all characters other than alpha numeric
        int m =AlphaNumericString.length();
        while(n>0)
        {
            int i= r.nextInt(m+1);
            if(i>=0 && i<m)
            {
                s.append(AlphaNumericString.charAt(i));
                n--;
            }

        }
        return s.toString();
        

    }
    
}