import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class ClientDriver{
    public static void main(String[] args) throws Exception {
        ObjectCreator oCreator = new ObjectCreator();
        
        MenuSystem menu = new MenuSystem(oCreator);
        menu.start();
        
        Inspector i = new Inspector(oCreator);
        i.inspectEverything();
        
        Serializer s = new Serializer(oCreator);
        s.displaySerializedDoc(s);
        
        //send with byte stream to server
        String serverAddress = menu.serverAddress();
        try {
        	Socket socket = new Socket(serverAddress, 7777);
        	DataOutputStream dOutStream = new DataOutputStream(socket.getOutputStream());
        	
        	ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        	XMLOutputter xmlOut = new XMLOutputter();
        	xmlOut.output(s.getDoc(), byteOutput);
        	byte [] b = byteOutput.toByteArray();
        	dOutStream.write(b);
        	
        	dOutStream.close();
        	//dOutStream.writeBytes(s.getDoc());
        	/*
        	ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        	XMLSerializer xSerializer = new XMLSerializer();
        	xSerializer.setOutputByteStream(byteOutput);
        	xSerializer.serialize((Element) s.getDoc());
        	
        	dOutStream.writeInt(byteOutput.size());
        	dOutStream.write(byteOutput.toByteArray());
        	dOutStream.flush();
        	*/
        	socket.close();
        }
        catch(IOException e) {
        	System.out.println(e);
        }
        
        
        System.out.println("\n======finished=======");

    }
    
}