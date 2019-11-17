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
        
        Serializer s = new Serializer(oCreator);
        
        ServerSock serverS = new ServerSock();
        s.setDoc(serverS.getDoc());
        s.displayDoc();
        
        Deserializer des = new Deserializer(oCreator, s.getDoc());
        des.deserializeEverything();
        
        System.out.println("\n--------------------");
        
        Inspector i = new Inspector(oCreator);
        i.inspectEverything();
        
        System.out.println("\n======finished=======");

    }
    
}