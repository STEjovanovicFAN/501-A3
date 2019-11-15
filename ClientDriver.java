import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ClientDriver{
    public static void main(String[] args) throws Exception {
        ObjectCreator oCreator = new ObjectCreator();
        
        MenuSystem menu = new MenuSystem(oCreator);
        menu.start();
        
        Inspector i = new Inspector(oCreator);
        i.inspectEverything();
        
        Serializer s = new Serializer(oCreator);
        s.displaySerializedDoc(s);
        
        //send 
        
        
        System.out.println("\n======finished=======");

    }
    
}