package com.tw.rds.filter;
/**
 * Created by C1mone on 1/9/15.
 */
public class Filter {
    public Boolean isPass(String str){
        if(str.startsWith("love"))
            return true;
        else
            return false;
    }
}
