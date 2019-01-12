package com.company;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Main {

    public static void main(String[] args) {
	// write your code here
        GUI gui = new GUI();
        gui.guitest();


//        try{
//            // 連接到mongoDB服務
//            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
//
//            // 連接到資料庫
//            MongoDatabase mongoDatabase = mongoClient.getDatabase("mycol");
//            System.out.println("Connect to database successfully");
//
//        }catch(Exception e){
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//        }
    }


}
