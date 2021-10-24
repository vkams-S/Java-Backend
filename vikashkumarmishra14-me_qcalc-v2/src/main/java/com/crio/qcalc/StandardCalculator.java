package com.crio.qcalc;

public class StandardCalculator {
    protected double result;
    public void divide(int a, int b){
        if(b==0){
            throw new ArithmeticException();
        }
        else{
            this.result=(double)(a/b);
        }
    }
    public void multiply(int a, int b)
    {
        long temp = (long)a*(long)(b);
        if(temp>Integer.MAX_VALUE)
        {
            throw new ArithmeticException();
        }
        else
        {
            this.result=a*b;
        }

    }
    public void  add(int a,int b){
        long temp=(long)a+(long)b;
        if(temp>Integer.MAX_VALUE)
        {
            throw new ArithmeticException();
        }
        else{
            this.result=a+b;
        }
        
    }
    public void subtract(int a,int b){
        long temp=(long)a-(long)b;
        if(temp<Integer.MIN_VALUE)
        {
            throw new ArithmeticException();
        }
        else{
            this.result=a-b;
        }
        
    }
    public void setResult(int value){
        if(value!=0){
            return;
        }
        else {
           this.result=value;
        }
    }
    public double getResult(){
        return result;
    }
}
