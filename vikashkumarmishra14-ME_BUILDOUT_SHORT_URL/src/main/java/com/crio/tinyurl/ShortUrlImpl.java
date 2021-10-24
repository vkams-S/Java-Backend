package com.crio.tinyurl;
import com.crio.shorturl.*;
import com.crio.tinyurl.randomString;
import com.crio.tinyurl.CustomHashTable;
public class ShortUrlImpl implements ShortUrl{
    hashTable HT = new hashTable();
    hashTable VT = new hashTable();
   static  int hitCount=0;
    public String registerNewUrl(String longUrl){
        
         String url;
         String encString;
        //randomString rd = new randomString();
        //checking if it already exists
        if(HT.isEmpty())
        {
            encString=randomString.getRandomString(9);
            url="http://short.url/"+encString;
            HT.addKV(longUrl, url);
            VT.addKV(url, longUrl); //make the sort url and insert into table
        }
        else if(HT.getKeyValue(longUrl)==null)
        {
            do
            {
                encString="http://short.url/"+randomString.getRandomString(9);
            } while(VT.getKeyValue(encString)!=null);
            
            url=encString;
            HT.addKV(longUrl,url);
            VT.addKV(url,longUrl);
        }
        else
        {
            url=HT.getKeyValue(longUrl);
        }

        return url;
    }

   public String registerNewUrl(String longUrl,String shortUrl){
        if(VT.getKeyValue(shortUrl)!=null)
        {
            return null;
        }
        else
        {
            HT.addKV(longUrl, shortUrl);
            VT.addKV(shortUrl, longUrl);
            return shortUrl;
        }
                   
    }
    public String getUrl(String shortUrl){
        if(VT.getKeyValue(shortUrl)!=null)
        {
            HT.increamentHitCount(VT.getKeyValue(shortUrl));
        }
       return VT.getKeyValue(shortUrl);
        
        
    }
    public Integer getHitCount(String longUrl){
             if(HT.getHitCount(longUrl)==-1)
             {
                 return 0;
             }
             else
             {
                 return HT.getHitCount(longUrl);
             }
    }
    public String delete(String longUrl)
    {
        VT.removeElement(HT.getKeyValue(longUrl));
        HT.removeElement(longUrl);
        return "Success";
    }
}