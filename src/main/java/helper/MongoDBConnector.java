package helper;

import com.mongodb.client.*;
import org.bson.Document;

public class MongoDBConnector {
    public MongoCollection collection;
    public MongoDatabase database;
    public MongoClient client;

    public MongoDBConnector() {
    }

    public void connectToDatabase(String connectionString, String databaseName){
        client = MongoClients.create(connectionString);
        database = client.getDatabase(databaseName);
    }

    public void writeToCollection(String collectionName, Document bsonDocument){
        database.getCollection(collectionName).insertOne(bsonDocument);
    }

    public FindIterable getDocumentFromCollection(String collectionName){
        return database.getCollection(collectionName).find();
    }

    public void disconnectFromServer(){
        client.close();
    }

    public String logTest(){
        return "Log test";
    }
}
