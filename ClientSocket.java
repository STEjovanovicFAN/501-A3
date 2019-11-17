import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class ClientSocket{
	
	public void sendDoc(String serverAddress, Document doc) {
        try {
        	Socket socket = new Socket(serverAddress, 7777);
        	DataOutputStream dOutStream = new DataOutputStream(socket.getOutputStream());
        	
        	ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        	XMLOutputter xmlOut = new XMLOutputter();
        	xmlOut.output(doc, byteOutput);
        	byte [] b = byteOutput.toByteArray();
        	dOutStream.write(b);
        	
        	dOutStream.close();

        	socket.close();
        }
        catch(IOException e) {
        	System.out.println(e);
        }
	}

    
}