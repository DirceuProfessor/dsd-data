import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Test;

import com.mongodb.DBCursor;
import com.mongodb.WriteResult;

import br.unip.data.modelo.mongo.Usuario;
import br.unip.data.mongo.MongoConnector;


public class TestMongoInsert {

	@Test
	public void test() {
		try {
			MongoConnector mongoCon = new MongoConnector("localhost", 27017, "dirceu");
			Usuario usuario = new Usuario();
			usuario.setNome("Teste");
			mongoCon.save(usuario);
			DBCursor dbCursor = mongoCon.find(usuario);
			while (dbCursor.hasNext()) {
				System.out.println(dbCursor.next());
			}
			assertEquals(1,dbCursor.size());
			WriteResult removeResult = mongoCon.remove(usuario);
			assertEquals(1,removeResult.getN());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
