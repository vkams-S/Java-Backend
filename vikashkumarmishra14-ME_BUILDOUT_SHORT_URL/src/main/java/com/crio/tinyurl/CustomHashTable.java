package com.crio.tinyurl;

import java.util.ArrayList;


//implementing custom hashTable
class hashNode{ // a node of chain (linked list) as we are using chaining technique to reslve collision
    private String key;
    private String value;

    hashNode Next;
    public hashNode(String key,String value){
        this.key=key;
        this.value=value;
    }
    public String getKey(){
        return key;
    }
    public String getValue(){
        return value;
    }
    
}
//to maintain the hitcount
class hitNode{
    private String key;
    private int hitCount;
    hitNode link;
    public hitNode(String key,int hitCount)
    {
        this.key=key;
        this.hitCount=hitCount;
    }
    public String getHKey()
    {
        return this.key;
    }
    public int value()
    {
        return this.hitCount;
    }
    public void setHitCount(int hit)
    {
        this.hitCount=hit;
    }
}

//structure of hash table
class hashTable{
    // it will be used to keep the track of all the assigned nodes
    private ArrayList<hashNode> hTable; //array of type headNode
    private ArrayList<hitNode> HNT;
    private int tSize;
    private int hSize; // to keep track of number of current table size
    private int tCapacity;
    private int hCapacity; // to keep track capcity of table
    public hashTable(){
        hTable = new ArrayList<>();
        HNT = new ArrayList<>();

        // let say initially we want the capicity to be 100 elements
        hCapacity=tCapacity=100;
        tSize=0;
        hSize=0; //initially table is empty
        for(int i=0;i<tCapacity;i++){
            hTable.add(null); // assigning all the null as initailly there are no elements to refer.
            HNT.add(null);

        }
        
    }
    public int size(){
        return tSize;
    }
    public boolean isEmpty()
    {
        if(size()==0)
        return true;
        else
        return false;
    }
    //for finding index of keys
    private int getHtableIndex(String key){
        int hashCode =key.hashCode();
        int index = hashCode % tCapacity;
        return Math.abs(index);
    }
    //removing an key-value pair
    public String removeElement(String key){
        int tableIndex=getHtableIndex(key);

        hashNode head = hTable.get(tableIndex);// getting stating element from chain
        hashNode prev=null;
        while(head !=null){
            if(head.getKey().equals(key))
            break;
            prev=head;
            head=head.Next;
        }
        if(head==null) //if key not found
        return null;
       //if found reduce the size
       tSize--;
       // now remove the key
       if(prev!=null)
           prev.Next=head.Next;
       else
           hTable.set(tableIndex,head.Next);
      return head.getValue();

    }

//get hit count;
    public int getHitCount(String key)
    {
        int tableIndex=getHtableIndex(key);
        hitNode hn = HNT.get(tableIndex);
        while(hn!=null)
        {
            if(hn.getHKey().equals(key))
            {
                return hn.value();
            }
              
            hn=hn.link;
        }
      return -1;
    }
//increament hit count
public void increamentHitCount(String key)
{
    int tableIndex=getHtableIndex(key);
        hitNode hn = HNT.get(tableIndex);
        while(hn!=null)
        {
            if(hn.getHKey().equals(key))
            {
                int prev=getHitCount(key);
                hn.setHitCount(prev+1);
                
            }
              
            hn=hn.link;
        }

        
}
//adding hit key-cont
public void addHC(String key,int value){  
 if(getHitCount(key)==-1)
 {

 
    //insert the key
    hSize++;
    //FIND INDEX OF KEY 
    int tableIndex=getHtableIndex(key);
    hitNode head = HNT.get(tableIndex);
    hitNode newNode = new hitNode(key,value);
    if(head != null)
    {
    newNode=head.link;
    HNT.set(tableIndex,newNode);
    }
    else
    {
        HNT.set(tableIndex, newNode);
    }
    
   // if load factor goes beyond threshold then double the size of hash table
   if(((1.0)*hSize/hCapacity)>=0.7){
       ArrayList<hitNode> temp = HNT;
       HNT = new ArrayList<>();
       hCapacity=2*hCapacity;
       hSize=0;
       for(int i=0;i<hCapacity;i++){
           HNT.add(null);
       }
       for(hitNode headNode:temp){
           while(headNode.link!=null){
               addHC(headNode.getHKey(),headNode.value());
               headNode=headNode.link;
           }
       }
   }
    

}
} 
    // get value of a key

    public String getKeyValue(String key){
        int tableIndex=getHtableIndex(key);
        hashNode head = hTable.get(tableIndex);
        

        //search key in chains
        while(head!=null)
        {
            if(head.getKey().equals(key))
            {
                return head.getValue();
            }
              
            head=head.Next;
        }

        // if key not found 
        return null;
    }

   // adding an key-value pair
   public void addKV(String key,String value){
        
            //insert the key
            tSize++;
            //FIND INDEX OF KEY 
            int tableIndex=getHtableIndex(key);
            hashNode head = hTable.get(tableIndex);
            hashNode newNode = new hashNode(key,value);
            if(head != null)
            {
            newNode=head.Next;
            hTable.set(tableIndex,newNode);
            }
            else
            {
            	hTable.set(tableIndex, newNode);
            }
            
           // if load factor goes beyond threshold then double the size of hash table
           if(((1.0)*tSize/tCapacity)>=0.7){
               ArrayList<hashNode> temp = hTable;
               hTable = new ArrayList<>();
               tCapacity=2*tCapacity;
               tSize=0;
               for(int i=0;i<tCapacity;i++){
                   hTable.add(null);
               }
               for(hashNode headNode:temp){
                   while(headNode.Next!=null){
                       addKV(headNode.getKey(),headNode.getValue());
                       headNode=headNode.Next;
                   }
               }
           }
        addHC(key, 0);
        
        
   } 

   public void print(){
       for(int i=0;i<tCapacity;i++)
       {
    	   hashNode head = hTable.get(i);
           if(head!=null)
           {
        	   System.out.print("\n"+i+":"+" ");
               
           while(head!=null)
           {
               System.out.print(head.getKey()+":"+head.getValue()+",");
               head=head.Next;
           }
           }
           

       }
   }

}
public class CustomHashTable{

}


