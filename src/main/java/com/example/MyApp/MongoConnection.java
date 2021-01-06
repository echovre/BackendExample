package com.example.MyApp;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoConnection {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB database = mongoClient.getDB("test");
		System.out.println(database);
		DBCollection table=database.createCollection("transactions", null);
		System.out.println(table);
		
		BasicDBObject document = new BasicDBObject();
		document.put("name", "a");
		document.put("company", "b");
		table.insert(document);

		Set<String> tables=database.getCollectionNames();
		System.out.println(tables);
		
	}
}