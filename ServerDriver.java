import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class ServerDriver{
    public static void main(String[] args) throws Exception {
        ObjectCreator oCreator = new ObjectCreator();
        
        SAXBuilder builder = new SAXBuilder();
        //DocumentBuilderFactory buildFactory = DocumentBuilderFactory.newInstance();  
        //DocumentBuilder builder = buildFactory.newDocumentBuilder();
        //org.w3c.dom.Document doc = null;        
        
        Serializer s = new Serializer(oCreator);
        //s.displaySerializedDoc(s);
       
        //send with byte stream to server
        //String serverAddress = menu.serverAddress();
        try {
        	ServerSocket serverSocket = new ServerSocket(7777);
        	Socket socket = serverSocket.accept();
        	
        	DataInputStream dataIn = new DataInputStream(socket.getInputStream());
        	
            Document doc = builder.build(dataIn);
            s.setDoc(doc);
            s.displayDoc();
        	/*
            int i = dataIn.readInt(); 
            byte[] data = new byte[i];
            dataIn.read(data, 0, i); 
            
            doc = builder.build(dataIn);
            */
        	/*
        	int size = dataIn.readInt();
        	byte[] xml = new byte[size];
        	dataIn.read(xml, 0, size);
        	
        	Document doc = (Document) builder.parse(new ByteArrayInputStream(xml));
        	
        	*/
        	//System.out.println(doc);
        	//s.displayDoc();
        	
        	socket.close();
        }
        catch(IOException e) {
        	System.out.println(e);
        }
        
        Deserializer des = new Deserializer(oCreator, s.getDoc());
        des.deserializeEverything();
        
        System.out.println("\n--------------------");
        
        Inspector i = new Inspector(oCreator);
        i.inspectEverything();
        
        System.out.println("\n======finished=======");

    }
    
}