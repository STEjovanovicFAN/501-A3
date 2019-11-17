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
    		identifyChild(rootElement.get(i), rootElement);
    	}
    	
    }
    
    private void identifyChild(Element child, List<Element> rootElement) {
    	String className = child.getAttributeValue("class");
    	if(className.equalsIgnoreCase("ObjectA")){
    		createA(child);
    	}
    	
    	else if(className.equalsIgnoreCase("ObjectB")){
    		createB(child);
    	}
    	
    	else if(className.equalsIgnoreCase("ObjectC")){
    		createC(child, rootElement);
    	}
    	
    	else if(className.equalsIgnoreCase("ObjectD")){
    		createD(child, rootElement);
    	}
    	
    	else if(className.equalsIgnoreCase("ObjectE")){
    		createE(child, rootElement);
    	}

    }
    
    private void createE(Element child, List<Element> rootElement) {
    	Element arrayChild = findReference(child, rootElement); 
    	ObjectE e = oCreator.createObjectE();
    	
    	List <Element> getChildrenRef = arrayChild.getChildren("reference");
    	for(int i = 0; i < getChildrenRef.size(); i++) {
    		oCreator.addObjectEValue(e, (ObjectA)findKey(getChildrenRef.get(i).getText()));
    	}
    }
    
    private void createD(Element child, List<Element> rootElement) {
    	Element arrayChild = findReference(child, rootElement); 
    	ObjectD d = oCreator.createObjectD(arrayChild.getAttributeValue("length"));
    	
    	List <Element> getChildrenRef = arrayChild.getChildren("reference");
    	for(int i = 0; i < getChildrenRef.size(); i++) {
    		oCreator.setObjectDValue(d, i, (ObjectA)findKey(getChildrenRef.get(i).getText()));
    	}
    }
    
    private void createC(Element child, List<Element> rootElement) {
    	Element arrayChild = findReference(child, rootElement); 
    	ObjectC c = oCreator.createObjectC(arrayChild.getAttributeValue("length"));
    	
    	List <Element> getChildrenValue = arrayChild.getChildren("value");
    	for(int i = 0; i < getChildrenValue.size(); i++) {
    		oCreator.setObjectCValue(c, i, getChildrenValue.get(i).getText());
    	}
    }
    
    private void createB(Element child) {
    	ObjectB b = oCreator.createObjectB();
    	idHashMap.put(b, Integer.toString(idHashMap.size()));
    	
    	Element field = child.getChild("field");
    	Element reference = field.getChild("reference");
    	
    	String refValue = reference.getText();
    	oCreator.setObjectBValue(b, (ObjectB)findKey(refValue)); 
    	
    }
    
    private void createA(Element child) {
    	Element field = child.getChild("field");
    	Element value = field.getChild("value");
   
    	String valueStr = value.getText();
    	oCreator.createObjectA(valueStr);
    	idHashMap.put(oCreator.listOfObjA.get(oCreator.listOfObjA.size()-1), Integer.toString(idHashMap.size()));
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
    
    private Element findReference(Element child, List<Element> rootElement) {
    	Element field = child.getChild("field");
    	Element reference = field.getChild("reference");
    	String refVal = reference.getText();
    	Element arrayChild = null; 
    	for(int i = 0; i < rootElement.size(); i++) {
    		if(rootElement.get(i).getAttributeValue("id").equals(refVal)) {
    			arrayChild = rootElement.get(i);
    		}
    	}
    	return arrayChild;
    }
}

