package com.crio.qcalc;


public class ScientificCalculator extends StandardCalculator{

void add(double a,double b){
    result=a+b;
}
void subtract(double a, double b){
    result=a-b;
}
void multiply(double a, double b){
    result=a*b;
}
void divide(double a, double b){
    result=a/b;
}
@Override
public void add(int a,int b){
    this.add((double)a,(double)b);
}
@Override
public void subtract(int a,int b){
    this.subtract((double)a,(double)b);
}
@Override
public void multiply(int a,int b){
    this.multiply((double)a,(double)b);
}
@Override
public void divide(int a,int b){
    this.divide((double)a,(double)b);
}
}
