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
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class ServerSock{
	
	public Document getDoc() throws JDOMException {
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
        try {
        	ServerSocket serverSocket = new ServerSocket(7777);
        	Socket socket = serverSocket.accept();
        	
        	DataInputStream dataIn = new DataInputStream(socket.getInputStream());
        	
        	doc = builder.build(dataIn);

        	socket.close();
        }
        catch(IOException e) {
        	System.out.println(e);
        }
        
        return doc;
	}
}