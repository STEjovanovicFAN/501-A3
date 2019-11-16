import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.lang.reflect.*;

public class Deserializer{
    private ObjectCreator oCreator;
    private IdentityHashMap<Object,String> idHashMap;
    private Document doc;

    public Deserializer(ObjectCreator objc, Document d){
        oCreator = objc;
        idHashMap = new IdentityHashMap<Object,String>();
        doc = d;

    }
    
    public void deserializeEverything() {
    	List<Element> rootElement = doc.getRootElement().getChildren("object");
    	
    	for(int i = 0; i < rootElement.size(); i++) {
    		identifyChild(rootElement.get(i));
    	}
    	
    }
    
    private void identifyChild(Element child) {
    	String className = child.getAttributeValue("class");
    	if(className.equalsIgnoreCase("ObjectA")){
    		createA(child);
    	}
    	
    	else if(className.equalsIgnoreCase("ObjectB")){
    		createB(child);
    	}
    	
    	else if(className.equalsIgnoreCase("ObjectC")){
    		createC(child);
    	}
    	
    	else if(className.equalsIgnoreCase("ObjectD")){
    		
    	}
    	
    	else if(className.equalsIgnoreCase("ObjectE")){
    		
    	}

    }
    
    private void createC(Element child) {
    	
    }
    
    private void createB(Element child) {
    	System.out.println("in B");
    	ObjectB b = oCreator.createObjectB();
    	idHashMap.put(b, Integer.toString(idHashMap.size()));
    	
    	Element field = child.getChild("field");
    	Element reference = field.getChild("reference");
    	
    	String refValue = reference.getText();
    	oCreator.setObjectBValue(b, (ObjectB)findKey(refValue)); 
    	
    }
    
    
    private void createA(Element child) {
    	Element field = child.getChild("field");
    	//System.out.println(field.toString());
    	
    	Element value = field.getChild("value");
    	//System.out.println(value.toString());
    	
    	//System.out.println("printing value");
    	String valueStr = value.getText();
    	oCreator.createObjectA(valueStr);
    	idHashMap.put(oCreator.listOfObjA.get(oCreator.listOfObjA.size()-1), Integer.toString(idHashMap.size()));
    	
    	//oCreator.createObjectA(((Element) child).getAttributeValue("value"));
    }
    
    private Object findKey(String keyVal) {
    	for (Map.Entry<Object, String> entry : idHashMap.entrySet()) {
    	    Object value = entry.getKey();
    	    String key = entry.getValue();
    	    
    	    if(keyVal.equals(key)) {
    	    	return value;
    	    }
    	}
    	return null;
    }
}

