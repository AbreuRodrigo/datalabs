package com.landcraft.server.services;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

public class MongoDB {
	private final String MONGO_CONNECTION = "mongodb://";
	private final String USERNAME = "datalab-server-admin";
	private final String PASSWORD = "Ro00Ma0027172004DLS";
	private final String HOST_TERMINATION = "@ds061385.mongolab.com:61385/datalab-server-db";
	private final String CLIENT_TOKEN = "clientToken";
	private final String DOC_ID = "_id";

	private MongoClientURI uri;
	private MongoClient mongo;
	private DB db;

	public MongoDB() {
		uri = new MongoClientURI(mountDBHostFullName());
		mongo = new MongoClient(uri);
		db = mongo.getDB(uri.getDatabase());
	}

	public int countByDocument(String collectionName, String clientToken) {
		DBCollection table = db.getCollection(collectionName);
		
		BasicDBObject query = new BasicDBObject();
		query.append(CLIENT_TOKEN, clientToken);
		
		DBCursor cursor = table.find(query);

		return cursor.count();
	}

	public WriteResult save(String collectionName, HashMap<String, Object> document) {
		DBCollection table = db.getCollection(collectionName);

		BasicDBObject query = new BasicDBObject();
		
		for(Map.Entry<String, Object> entry : document.entrySet()) {
			query.put(entry.getKey(), entry.getValue());
		}
		
		WriteResult result = table.insert(query);
				
		return result;
	}
	
	public void update(String collectionName, HashMap<String, Object> document) {
		DBCollection table = db.getCollection(collectionName);
		
		BasicDBObject query = new BasicDBObject();
		query.put(CLIENT_TOKEN, document.get(CLIENT_TOKEN));

		BasicDBObject newDocument = new BasicDBObject();
		
		for(Map.Entry<String, Object> entry : document.entrySet()) {
			newDocument.put(entry.getKey(), entry.getValue());
		}
					
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		table.update(query, updateObj);
	}
	
	public void persist(String collectionName, HashMap<String, Object> document) {
		String clientToken = (String) document.get(CLIENT_TOKEN);
				
		if(countByDocument(collectionName, clientToken) == 0) {
			this.save(collectionName, document);
		}else {
			this.update(collectionName, document);
		}
	}
	
	public HashMap<String, Object> findAll(String document) {
		DBCollection collection = db.getCollection(document);
		DBCursor cursor = collection.find();
		
		return processCursorResponse(cursor);
	}
	
	public HashMap<String, Object> findWithCriterias(String document, HashMap<String, Object> criterias) {
		BasicDBObject query = new BasicDBObject(criterias);
		
		DBCollection table = db.getCollection(document);

		DBCursor cursor = table.find(query);
				
		return processCursorResponse(cursor);
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> processCursorResponse(DBCursor cursor) {
		HashMap<String, Object> response = null;
		
		if(cursor.hasNext()) {
			response = (HashMap<String, Object>) cursor.next().toMap();
			response.remove(DOC_ID);
		}
		
		return response;
	}
	
	private String mountDBHostFullName() {
		return MONGO_CONNECTION + USERNAME + ":" + PASSWORD + HOST_TERMINATION;
	}
}