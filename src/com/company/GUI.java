package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by yihsiu on 2019/1/4.
 */
public class GUI {
    spider sp = new spider();
    public String number ="爆";//預設
    public String boardName = "baseball";//預設
    public String boardHref = "https://www.ptt.cc/bbs/Baseball/index.html" ;//預設

    ArrayList getHrefList = new ArrayList() ;
    ArrayList a ;

    public ArrayList getHref(){

        a = sp.hrefList;
        getHrefList = a ;
        return getHrefList;
    }

    //有void的方法不用回傳值 若無則要
    public void guitest() {
        JFrame guiTest = new JFrame("HighPassFinal");
        guiTest.setSize(1500, 900);
        guiTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//關視窗的
        guiTest.setVisible(true);

        JLabel title = new JLabel("我要看u質文章la");
        JLabel board = new JLabel("選擇看板");
        JLabel txt = new JLabel("共10頁,200則貼文");
        JComboBox boardChoose = new JComboBox();
        JList showTitle = new JList();
        JList showtext = new JList();

        JLabel num = new JLabel("設定推文數");
        JComboBox numChoose = new JComboBox();

        JButton btn = new JButton("幫我爬u文！");
        JScrollPane scroller=new JScrollPane(showTitle);
        JScrollPane scroller2=new JScrollPane(showtext);

        guiTest.getContentPane().setLayout(null);//setLayout設成null 須自行定義座標
        title.setBounds(10, 10, 100, 20);

        board.setBounds(10,50,100,20);
        boardChoose.setBounds(120,50,200,20);

        num.setBounds(10, 90, 100, 30);
        numChoose.setBounds(120,90,200,30);

        btn.setBounds(65,130,200,30);
        txt.setBounds(120,510,200,20);
        scroller.setBounds(350,20,500,800);//因為JList已經放入JScrollPane,不需再次setBounds
        scroller2.setBounds(870,20,500,800);


        guiTest.getContentPane().add(scroller);
        guiTest.getContentPane().add(scroller2);
        guiTest.getContentPane().add(title);
        guiTest.getContentPane().add(num);
        guiTest.getContentPane().add(boardChoose);
        guiTest.getContentPane().add(numChoose);
        guiTest.getContentPane().add(board);
        guiTest.getContentPane().add(btn);
        guiTest.getContentPane().add(txt);
//        guiTest.getContentPane().add(showTitle);不需要再把JList放入getContentPane,不然會被移出JScrollPane






        numChoose.addItem("爆");
        numChoose.addItem("我全都要");
        numChoose.addItem("30+");
        numChoose.addItem("50+");
        numChoose.addItem("70+");
        numChoose.addItem("廢文");
        for(int i=1 ; i<100; i++ ){
            numChoose.addItem(i);
        }
//        numChoose.addItem("XX");
//        numChoose.addItem("X1-X5");
//        numChoose.addItem("X6-X9");

        boardChoose.addItem("Baseball");
        boardChoose.addItem("boy-girl");
        boardChoose.addItem("WomenTalk");
        boardChoose.addItem("MobileComm");
        boardChoose.addItem("PC_Shopping");
        boardChoose.addItem("NBA");
        boardChoose.addItem("movie");
        boardChoose.addItem("Beauty");

//從這裡call spider方法 那邊記得寫回傳參數

        //選了之後
        boardChoose.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                boardName = e.getItem().toString();
                System.out.println(boardName);
                if(boardName.equals("baseball")){
                    boardHref = "https://www.ptt.cc/bbs/Baseball/index.html";
                }else if(boardName.equals("boy-girl")){
                    boardHref = "https://www.ptt.cc/bbs/Boy-Girl/index.html";
                }else if(boardName.equals("WomenTalk")){
                    boardHref = "https://www.ptt.cc/bbs/WomenTalk/index.html";
                }else if(boardName.equals("MobileComm")){
                    boardHref = "https://www.ptt.cc/bbs/MobileComm/index.html";
                }else if(boardName.equals("PC_Shopping")){
                    boardHref = "https://www.ptt.cc/bbs/PC_Shopping/index.html";
                }else if(boardName.equals("NBA")){
                    boardHref = "https://www.ptt.cc/bbs/NBA/index.html";
                }else if(boardName.equals("movie")){
                    boardHref = "https://www.ptt.cc/bbs/movie/index.html";
                }else if(boardName.equals("Beauty")){
                    boardHref = "https://www.ptt.cc/bbs/Beauty/index.html";
                }

            }
        });

        numChoose.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                number = e.getItem().toString();
                System.out.println(number);
            }
        });

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //因為sp.spiderSearch的方法有throws exception 所以要加
                try {
                    sp.reset();
                    //傳user選的值 過去給spider方法 要爬蟲的東西
                    sp.spiderSearchNon18(boardHref,number);

                    showTitle.setListData(sp.getArray()); //sp.getArray()回傳陣列進來
                    showTitle.repaint();
//                    scroller.repaint();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });


        showTitle.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                getHref();
                int a = showTitle.getSelectedIndex();//取得選取的index
                String inputHref = getHrefList.get(a).toString();//取得index(a)的文章url
                try{
                    sp.getTxt(inputHref);
                }catch (IOException e1){
                    e1.printStackTrace();
                }
                showtext.setListData(sp.getText());
                showtext.repaint();
//
//                System.out.println(inputHref);
                System.out.println(a);
            }
        });


    }


//接收spider爬蟲完之後 傳回來的值 推文數 標題 內容
    public void getText(String pttTitle,String pttContent,String pttLikeNum){


    }

}


