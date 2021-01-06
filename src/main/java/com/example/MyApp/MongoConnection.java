package com.example.MyApp;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MongoConnection {
	MongoClient mongoClient ;
	DB database ;
	DBCollection collection;
	static final String tableName="transactions";

	public MongoConnection() {
		//TODO: better error handling
		try {
			mongoClient = new MongoClient("localhost", 27017);
		} catch (UnknownHostException e) {
			System.out.println("FAILED to connect to MongoDB");
			e.printStackTrace();
		}
		database = mongoClient.getDB("test");
		collection=database.getCollection(tableName);
		if (database.collectionExists(tableName)) {
			database.createCollection(tableName, null);
		}
	}

	public BodyStatusDetail findRecord(String uuid) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("uuid", uuid);
		DBCursor cursor = collection.find(whereQuery);
		if( cursor.hasNext() ) {
			DBObject obj = cursor.next();
			String body=obj.get("body").toString();
			String status=obj.get("status").toString();
			String detail=obj.get("detail").toString();
			return new BodyStatusDetail(body,status,detail);
		}else {
			return new BodyStatusDetail();
		}
	}

	public boolean writeUpdateRecord(String uuid, String body,String status,String detail) {
		BasicDBObject newObj = new BasicDBObject();
		newObj.put("uuid", uuid);
		newObj.put("body", body); 
		newObj.put("status", status);
		newObj.put("detail", detail);

		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("uuid", uuid);
		DBCursor cursor = collection.find(whereQuery);
		System.out.println(cursor);
		WriteResult result;
		if( cursor.hasNext() ) {
			//TODO: do not overwrite old values if not in new values
			DBObject obj = cursor.next();
			result=collection.update(obj, newObj, true, false);
		}else {
			System.out.println("Could not find existing record, creating.");
			result=collection.insert(newObj);
		}
		//TODO: better error checking here
		return result.getN()==1;
	}

	//unit test
	public static void main(String[] args) throws UnknownHostException {
		MongoConnection con = new MongoConnection();
		DB database=con.database;
		System.out.println(database);

		DBCollection table=database.getCollection(tableName);
		System.out.println(table.getCount());

		BasicDBObject document = new BasicDBObject();
		document.put("name", "a");
		document.put("company", "b");
		table.insert(document);

		DBCollection tables=database.getCollection(tableName);
		System.out.println(tables.getCount());

		String uuid="5ff59fcb848e1f47e6b21d07";
		con.writeUpdateRecord(uuid, "a", "b", "c");

	}
}