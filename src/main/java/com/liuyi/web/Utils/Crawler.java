package com.liuyi.web.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
public class Crawler {
    public static ArrayList<String> GetImgPath(){
        ArrayList<String> target=new ArrayList<String>();
        target.add("http://www.nmc.cn/publish/observations/china/dm/weatherchart-h000.htm");
        //target.add("http://www.nmc.cn/publish/satellite/fy2.htm");
        target.add("http://www.nmc.cn/publish/observations/hourly-winds.html");
        target.add("http://www.nmc.cn/publish/typhoon/probability-img1.html");
        ArrayList<String> imgPath=new ArrayList<String>();
        for (String tar:target){
            try{
                Document body= Jsoup.connect(tar).execute().parse();
                imgPath.add(body.getElementById("imgpath").attr("src"));
            }catch (Exception e){
            }
        }
        try {
            Document body = Jsoup.connect("http://www.weather.com.cn/index/zxqxgg1/new_wlstyb.shtml").execute().parse();
            Elements imgs=body.getElementsByTag("img");
            int count=0;
            for (Element element:imgs){
                //if (count==3) break;
                String src=element.attr("src");
                if (src.split("sevp")[0].length()<src.length()) imgPath.add(src);
                count++;
            }
        }catch (Exception e){

        }
        return imgPath;
    }
}
