import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.lang.reflect.*;

public class Serializer{
    private ObjectCreator oCreator;
    private IdentityHashMap<Object,String> idHashMap;
    private Document doc;

    public Serializer(ObjectCreator objc) throws Exception{
        oCreator = objc;
        idHashMap = new IdentityHashMap<Object,String>();
        doc = new Document(new Element("serialized"));

    }
    
    public Document serializeEverything() throws Exception {
    	for (ObjectA a : oCreator.listOfObjA) {
    		serializeObject(a);
		}
    	
    	for(ObjectB b : oCreator.listOfObjB) {
    		serializeObject(b);
    	}
    	
    	for(ObjectC c : oCreator.listOfObjC) {
    		serializeObject(c);
    	}
    	
    	for(ObjectD d : oCreator.listOfObjD) {
    		serializeObject(d);
    	}
    	
    	for(ObjectE e : oCreator.listOfObjE) {
    		serializeObject(e);
    	}
    	
    	return doc;
    }

    public void serializeObject(Object obj) throws Exception {
		String id = Integer.toString(idHashMap.size());
		idHashMap.put(obj, id);
		Class sourceclass = obj.getClass();
    	
		Element oElt = new Element("object");
		oElt.setAttribute( "class", sourceclass.getName() );
		oElt.setAttribute( "id", id );
		doc.getRootElement().addContent(oElt);
		
		if(Collection.class.isAssignableFrom(sourceclass)) {
			
			Class componentType = sourceclass.getComponentType();
			
			ArrayList alist = new ArrayList<>();
			alist = new ArrayList<>((Collection<?>)obj);
			
			int length = alist.size();
			oElt.setAttribute( "length", Integer.toString(length) );
			
			for (int i=0; i< alist.size(); i++) {
				Element reference = new Element("reference");
				Object objVal = alist.get(i);
				String hashMapVal = idHashMap.get(objVal); 
				oElt.addContent(reference.setText(hashMapVal));

				//oElt.addContent(reference.setText(idHashMap.get(String.valueOf(alist.get(i)).toString())));
				//oElt.addContent(serializeVariable(componentType,Array.get(obj,i)));

				//reference.setText(idHashMap.get(alist.get(i)).toString());
				//oElt.addContent( "" + alist.get(i));
			}
			
		}
		
		else if (!sourceclass.isArray()) {
			Field[] fields = sourceclass.getDeclaredFields();
			for (int i=0; i<fields.length; i++) {
				
				fields[i].setAccessible(true);
				
				Element fElt = new Element("field");
				fElt.setAttribute("name", fields[i].getName());
				Class declClass = fields[i].getDeclaringClass();
				fElt.setAttribute("declaringclass", declClass.getName());
		
				Class fieldtype = fields[i].getType();
				Object child = fields[i].get(obj);
		
				if (Modifier.isTransient(fields[i].getModifiers())){
					child = null;
				}
				fElt.addContent( serializeVariable( fieldtype, child));
		
				oElt.addContent(fElt);
			}
		}
		
		else {
			Class componentType = sourceclass.getComponentType();
		
			int length = Array.getLength(obj);
			oElt.setAttribute( "length", Integer.toString(length) );
			for (int i=0; i<length; i++) {
				oElt.addContent( serializeVariable( componentType,Array.get(obj,i)));
			}
    	}
    	 
    }
    
    private Element serializeVariable(Class fieldtype, Object child) throws Exception{
    	//System.out.println(fieldtype.getTypeName());
    	if (child == null) {
    		return new Element("null");
    	}
   
    	else if (!fieldtype.isPrimitive()) {
    		Element reference = new Element("reference");
    		if (idHashMap.containsKey(child)) {
    			reference.setText(idHashMap.get(child).toString());
    		}
    		else {
    			reference.setText( Integer.toString(idHashMap.size()) );
    			serializeObject(child);
    		}
    		return reference;
    	}
    	
    	else {
    		Element value = new Element("value");
    		value.setText(child.toString());
    		return value;
    	}
    }
    
    public Document getDoc() {
    	return doc;
    }
    
    public void setDoc(Document d) {
    	doc = d;
    }

    public void displaySerializedDoc(Serializer s) {
        try {
        	System.out.println("=======================");
 		    XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
 		    out.output(serializeEverything(), System.out);
         }
         catch (Exception e) {
         	System.out.println(e);
         }
    }
    
    public void displayDoc() {
        try {
        	System.out.println("=======================");
 		    XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
 		    out.output(doc, System.out);
         }
         catch (Exception e) {
         	System.out.println(e);
         }
    }
}
