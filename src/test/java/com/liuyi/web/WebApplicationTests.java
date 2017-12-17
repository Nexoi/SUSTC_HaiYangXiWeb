package com.liuyi.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class WebApplicationTests {

	@Test
	public void contextLoads() {
    int []arr={2,1,4,5,6};
    try{
        for (int val :arr) haha(val);
    }catch (Exception e){
        System.out.print("E");
    }
	}
	public void haha(int val) throws Exception{
	    try{
	        if (val%2==0) throw new Exception();
            System.out.print(val);
        }finally {
            System.out.print("e");
        }
    }

}
