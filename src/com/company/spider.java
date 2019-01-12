package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yihsiu on 2019/1/4.
 */
public class spider {

    ArrayList titleList = new ArrayList() ;//放title的arraylist
    ArrayList hrefList = new ArrayList() ;//放抓到的href的arraylist
    int k =0;

    public void reset(){
        //       call 此function時清空arraylist
        ArrayList resetList = new ArrayList();
        titleList = resetList;
        ArrayList resetList2 = new ArrayList();
        hrefList = resetList2;
        k=0;
    }


/** 丟進去爬蟲的東西 不用判斷有沒有18+的版
    這裡是函式 像f(x)=x+1 裡面的String值是參考用 等到有人call他的時候再從call的地方丟值進去**/
    public void spiderSearchNon18(String pttBoard,String pttLikeNum) throws IOException {

        /**各版的網址 之後放到gui介面**/
        String urlBaseball = "https://www.ptt.cc/bbs/Baseball/index.html";
        String urlBoygirl = "https://www.ptt.cc/bbs/Boy-Girl/index.html";
        String urlWomenTalk = "https://www.ptt.cc/bbs/WomenTalk/index.html";
        String urlMobileComm = "https://www.ptt.cc/bbs/MobileComm/index.html";
        String urlPC_Shopping = "https://www.ptt.cc/bbs/PC_Shopping/index.html";
        String urlNBA = "https://www.ptt.cc/bbs/NBA/index.html";
        String urlmovie = "https://www.ptt.cc/bbs/movie/index.html";
        String urlBeauty = "https://www.ptt.cc/bbs/Beauty/index.html";

//        System.out.println(pttBoard);

        String tempHref; //上一頁網址暫存
        Document tempDoc; //doc文本暫存
        Element tempNextPage; //抓到的標籤暫存



        Document doc = Jsoup.connect(pttBoard).get();//以connect去連接該網址之後get下來得到doc文本
        Element Nextpage = doc.select("div[class=btn-group btn-group-paging]>a").get(1);//取第2個的div[class=btn-group btn-group-paging]>a標籤
        String relHref = Nextpage.attr("href");//抓標籤裡面的值
        tempHref = "https://www.ptt.cc" + relHref;
//        System.out.println(tempHref);
//        tempNextPage = Nextpage;


        //for迴圈 第一頁不跑 往前跑i頁爬蟲
        for (int i = 1; i < 11; i++) {
            k=0;//換頁要歸0
            System.out.println("正在查的網址是 " + tempHref);
            tempDoc = Jsoup.connect(tempHref).get();
            tempNextPage = tempDoc.select("div[class=btn-group btn-group-paging]>a").get(1);
            System.out.println(tempNextPage);

            String Href = tempNextPage.attr("href");
            tempHref = "https://www.ptt.cc" + Href;
            System.out.println("下一個要查的是 "+tempHref);

            //抓文章標題 每頁20筆
            try {
                for (int j = 0; j < 20; j++) {
                    String title;
                    try {
                        title = tempDoc.select("div[class=r-ent]>div[class=title]").get(j).text();//抓title  div[class=r-ent]標籤裡的div[class=title]標籤的a標籤
//                        System.out.println("ssss"+title);
//                        if(title.equals("")){
//                            title="被刪文了";
//                        }
                    }catch (IndexOutOfBoundsException e1){
                        title = "被刪文了";
                    }
                    String like ;
                    try {
                        like = tempDoc.select("div[class=r-ent]>div[class=nrec]").get(j).text();//抓推文數

                        if(like.equals("")){
                            like="0";
                        }
                    }catch (IndexOutOfBoundsException e4){
                        like = "0";
                    }

                    Element href ;
//                  Element href2 ;
                    String titleHref ;
                    String titleHrefPttcc ;
                    try {
//                      href2 = tempDoc.select("div[class=r-ent]>div[class=title]>a").get(j);//抓文章網址那段的a標籤

//                        System.out.println("ssss"+href);
                        if(title.contains("刪除")){
                            titleHref = "https://www.ptt.cc";
                            k++;
                        }else{
                            href = tempDoc.select("div[class=r-ent]>div[class=title]>a").get(j-k);//抓文章網址那段的a標籤
                            titleHref = "https://www.ptt.cc"+href.attr("href");//抓titleHref <a>標籤裡面的href值
                        }
                    }catch (IndexOutOfBoundsException e){
                        href = null ;
                        titleHref = " ";
                    }


                    int likeNum;

                    if (like.equals("0")||like.equals(" ") || like.equals("XX") || like.equals("X1") || like.equals("X2") || like.equals("X3") || like.equals("X4") || like.equals("X5") || like.equals("X6") || like.equals("X7") || like.equals("X8") || like.equals("X9")) {
                        likeNum = 0;
                    } else if (like.equals("爆")) {
                        likeNum = 100;
                    } else {
                        likeNum = Integer.parseInt(like);
                    }

                    //判斷推文數
                    if (like.equals(pttLikeNum)) {
                        //根據入多少就多少
                        titleList.add(title);//寫入進arrayList
                        hrefList.add(titleHref);
                    } else if (pttLikeNum.equals("爆") && likeNum == 100) {
                        titleList.add(title);
                        hrefList.add(titleHref);

                    } else if (pttLikeNum.equals("我全都要")) {
                        titleList.add(title);
                        hrefList.add(titleHref);

                    } else if (pttLikeNum.equals("30+") && likeNum >= 30) {
                        titleList.add(title);
                        hrefList.add(titleHref);

                    } else if (pttLikeNum.equals("50+") && likeNum >= 50) {
                        titleList.add(title);
                        hrefList.add(titleHref);

                    } else if (pttLikeNum.equals("70+") && likeNum >= 70) {
                        titleList.add(title);
                        hrefList.add(titleHref);

                    } else if (pttLikeNum.equals("廢文") && likeNum == 0) {
                        titleList.add(title);
                        hrefList.add(titleHref);

                    }else{

                    }


                    System.out.println(title);
                    System.out.println(like);
                    System.out.println(titleHref);
                    System.out.println(hrefList);
                    System.out.println("aaa"+k);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    //把Arraylist 轉 Array
    public String[] getArray(){
        int n = titleList.size();
        String[] titleArr = new String[n];
        for(int i=0; i<n; ++i ){
            titleArr[i] = titleList.get(i).toString();
        }
        return titleArr ;
    }

    public String[] getText(){
        String[] txtArr = new String[1];
        txtArr[0]= aa;
        return txtArr ;
    }

    String aa;
    //抓文章內容的 等gui選擇文章後 把href丟進myHref
    public void getTxt(String myHref) throws IOException{
        System.out.println(myHref);
        Document doc = Jsoup.connect(myHref).get();//以connect去連接該網址之後get下來得到doc文本
        aa = doc.select("div[class=bbs-screen bbs-content]").get(0).text();
        System.out.println(aa);
    }

}
