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
        
        String serverAddress = menu.serverAddress();
        ClientSocket cs = new ClientSocket();
        cs.sendDoc(serverAddress, s.getDoc());
        
        System.out.println("\n======finished=======");
    }
    
}