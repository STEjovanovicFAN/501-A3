import java.util.IdentityHashMap;
import org.jdom.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.lang.reflect.*;

public class Serializer{
    private ObjectCreator oCreator;
    private int id;
    private IdentityHashMap<Object,Integer> idHashMap;
    private String fileName;
    private SAXBuilder builder;
    private Document doc;

    public Serializer(ObjectCreator objc){
        oCreator = objc;
        id = 0;
        idHashMap = new IdentityHashMap<Object,Integer>();
        fileName = "serializedObjects.xml";
        builder = new SAXBuilder();
        doc = builder.build(new File(fileName));
    }




}
