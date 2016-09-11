package br.unip.data.mongo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import br.unip.data.modelo.BaseModel;

public class MongoConnector {

	private MongoClient mongo;
	private DB mongoDB;
	
	public MongoConnector(String host, int port, String dbName) throws UnknownHostException {
		// TODO Auto-generated constructor stub
		mongo = new MongoClient( host , port);
		mongoDB = mongo.getDB(dbName);
		
	}
	
	public DB getDatabase(String dbName) {
		return mongoDB;
	}
	
	public DBCollection getCollection(String collectionName) {
		return mongoDB.getCollection(collectionName);
	}
	
	public void save(BaseModel model) {
		String name = model.getClass().getSimpleName();
		DBCollection collection = getCollection(name);
		BasicDBObject toBeInserted = createDBObject(name,model);
		collection.insert(toBeInserted);
	}
	
	private BasicDBObject createDBObject(String name, BaseModel model) {
		BasicDBObject toBeInserted = new BasicDBObject();
		Field[] fields = model.getClass().getDeclaredFields();
		for (Field field: fields){
			String fieldName = field.getName();
			try {
				Method method = model.getClass().getMethod("get"+WordUtils.capitalize(fieldName),
						null);
				Object[] args = {};
				toBeInserted.put(fieldName, method.invoke(model, args));
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return toBeInserted;
	}

	public DBCursor find(BaseModel model){
		String name = model.getClass().getSimpleName();
		DBCollection table =getCollection(name);
		BasicDBObject queryObject = createDBObject(name, model);
		return table.find(queryObject);	
	}
	
	public WriteResult remove(BaseModel model){
		String name = model.getClass().getSimpleName();
		DBCollection table =getCollection(name);
		BasicDBObject queryObject = createDBObject(name, model);
		return table.remove(queryObject);	
	}
	
}
