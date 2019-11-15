import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.lang.reflect.Array;

public class Inspector {

    public static final String NONE = "none"; 
    public static final String VOID = "void"; 
    public static final String NULL = "null";
    public static final String ARRAY = "Array";
    private ObjectCreator oCreator;
    private IdentityHashMap<Object, Integer> idHashMap;

    
    public Inspector(ObjectCreator o) {
    	oCreator = o;
    }
    
    public void inspectEverything() {
    	for (ObjectA a : oCreator.listOfObjA) {
    		inspect(a, true);
		}
    	
    	for(ObjectB b : oCreator.listOfObjB) {
    		inspect(b, true);
    	}
    	
    	for(ObjectC c : oCreator.listOfObjC) {
    		inspect(c, true);
    	}
    	
    	for(ObjectD d : oCreator.listOfObjD) {
    		inspect(d, true);
    	}
    	
    	for(ObjectE e : oCreator.listOfObjE) {
    		inspect(e, true);
    	}
    }
    
    public void inspect(Object obj, boolean recursive) {
    	idHashMap = new IdentityHashMap<Object,Integer>();
        Class c = getClass(obj);
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        String className = getSimpleName(c);
        formatOutputDepth("Declaring Class Name: " + className, depth);

        //inspectImmediateSuperClass(c, obj, recursive, depth);
        //inspectInterfaces(c, obj, recursive, depth);
        //inspectConstructors(c, obj, recursive, depth);
        //inspectMethods(c, obj, recursive, depth);
        inspectFields(c, obj, recursive, depth);
    }    

    private void inspectFields(Class c, Object obj, boolean recursive, int depth){
    	boolean same = false;
    	if(idHashMap.containsKey(obj)) {
    		same = true;
    	}
    	
    	else {
    		idHashMap.put(obj, 0);
    	}
    	
        if(checkArrayEmpty(getDeclaredFields(c))){
            formatOutputDepth("Declared Fields: " + NONE, depth);
        }

        else{
            for(Field field: getDeclaredFields(c)){
                formatOutputDepth("Declared Field Name: " + field.getName(), depth);

                if(field.getType().isArray()){
                    formatOutputDepth("- Type: " + ARRAY, depth+1);
                }

                else{
                    String typeName = field.getType().getName();
                    formatOutputDepth("- Type: " + typeName, depth+1);
                }
                String modName = ModifierToString(field.getModifiers());
                formatOutputDepth("- Modifier: " + modName, depth+1);

                field.setAccessible(true);
                try{
                    if(field.getType().isPrimitive()){
                        String valueType = String.valueOf(field.get(obj));
                        formatOutputDepth("- Value: " + valueType, depth+1);
                    }

                    else if(field.getType().isAssignableFrom(String.class)) {
                    	formatOutputDepth("- Value: ", depth+1);
                    	String strValue = (String) field.get(obj);
                    	for(int i = 0; i < strValue.length(); i++) {
                    		formatOutputDepth("- " + strValue.charAt(i), depth+2);
                    	}
                    }
                    
                    else if(field.getType().isArray()){
                        handelArrayField(field.getType(), field.get(obj), recursive, depth);
                    }

                    //recursive 
                    else{
                        if(recursive && !same){
                            inspectClass(getClass(field.get(obj)), obj, recursive, depth);
                        }

                        else{
                            String objectReference = getObjectReference(obj, field);
                            formatOutputDepth("- Value: " + objectReference, depth+1);
                        }
                    }

                }

                catch(NullPointerException e){
                    formatOutputDepth("- Value: " + NULL, depth+1);
                }

                catch(IllegalAccessException e){
                    formatOutputDepth("- Value: Error! Cannot Access field", depth+1);
                }

                catch(Exception e){
                    formatOutputDepth("- Value: Error! Something went really wrong, trace message--> " + e, depth+1);
                }

            }
        }
    }

    private void handelArrayField(Class c, Object obj, boolean recursive, int depth){
        String componentType = getSimpleName(c.getComponentType());
        String lengthUnit = Integer.toString(ArrayGetLength(obj));
        formatOutputDepth("- Component Type: " + componentType, depth+1);
        formatOutputDepth("- Length: " + lengthUnit, depth+1);
        formatOutputDepth("- Contents: ", depth+1);
        
        for(int i = 0; i < ArrayGetLength(obj); i++){
            if(ArrayGetObj(obj, i) == null){
                formatOutputDepth("- " + NULL, depth+2);
            }
            else if(c.getComponentType().isPrimitive()){
                formatOutputDepth("- " + Array.get(obj, i), depth+2);
            }
            //2d arrays
            else if(c.getComponentType().isArray()){
                handelArrayField(getClass(ArrayGetObj(obj, i)), ArrayGetObj(obj, i), recursive, depth+2);
            }
            //recursive 
            else{
                if(recursive){
                    if(recursive){
                        inspectClass(getClass(ArrayGetObj(obj, i)), ArrayGetObj(obj, i), recursive, depth+2);
                    }

                    else{
                        String objReferance = getObjectReference(obj, i);
                        formatOutputDepth("- Value: " + objReferance, depth+2);
                    }
                }
            }
        }
    }

    private void inspectMethods(Class c, Object obj, boolean recursive, int depth){
        if(checkArrayEmpty(getDeclaredMethods(c))){
            formatOutputDepth("Method Name: " + NONE, depth);
        }

        else{
            for(Method meth : getDeclaredMethods(c)){
                String methodName = meth.getName();
                formatOutputDepth("Method Name: " + methodName, depth);

                if(checkArrayEmpty(meth.getExceptionTypes())){
                    formatOutputDepth("- Exception Type: " + NONE, depth+1);
                }
                else{
                    for(Class except: meth.getExceptionTypes()){
                        String exceptionTypeName = except.getName();
                        formatOutputDepth("- Exception Type: " + exceptionTypeName, depth+1);
                    }
                }

                if(checkArrayEmpty(meth.getParameterTypes())){
                    formatOutputDepth("- Parameter Type: " + NONE, depth+1);
                }

                else{
                    for(Class parType : meth.getParameterTypes()){
                        String parameterTypeName = getSimpleName(parType);
                        formatOutputDepth("- Parameter Type: " + parameterTypeName, depth+1);
                    }
                }

                if(meth.getReturnType() == null){
                    formatOutputDepth("- Return Type: " + VOID, depth+1);
                }

                else{
                    String returnTypeName = String.valueOf(meth.getReturnType());
                    formatOutputDepth("- Return Type: " + returnTypeName, depth+1);
                }
                String modName = ModifierToString(meth.getModifiers());
                formatOutputDepth("- Modifier: " + modName, depth+1);
            }
        }
    }

    private void inspectConstructors(Class c, Object obj, boolean recursive, int depth){
        if(checkArrayEmpty(getDeclaredConstructors(c))){
            formatOutputDepth("Constructor Name: " + NONE,depth);
        }

        else{
            for(Constructor cons: getDeclaredConstructors(c)){
                String constantName = cons.getName();
                formatOutputDepth("Constructor Name: " + constantName, depth);

                if(checkArrayEmpty(cons.getParameterTypes())){
                    formatOutputDepth("- Parameter: " + NONE, depth+1);
                }

                else{
                    for(Class par: cons.getParameterTypes()){
                        String parametername = par.getName();
                        formatOutputDepth("- Parameter: " + parametername, depth+1);
                    }     
                }
                String modName = ModifierToString(cons.getModifiers());
                formatOutputDepth("- Modifier: " + modName, depth+1);

            }
        }
    }

    private void inspectInterfaces(Class c, Object obj, boolean recursive, int depth){
        if(checkArrayEmpty(getInterfaces(c))){
            formatOutputDepth("Interface Name: "  + NONE, depth);
        }

        else{
            for(Class inter: getInterfaces(c)){
                String interfaceName = getSimpleName(inter);
                formatOutputDepth("Interface Name: " + interfaceName,depth);
                inspectClass(inter, obj, recursive, depth+1);
            }
        }
    }

    private void inspectImmediateSuperClass(Class c, Object obj, boolean recursive, int depth){
        if(getSuperClass(c) == null){
            formatOutputDepth("Super Class Name: " + NONE, depth);
        }

        else{
            String superClassName = getSimpleName(getSuperClass(c));
            formatOutputDepth("Super Class Name: " + superClassName,depth);
            inspectClass(getSuperClass(c), obj, recursive, depth+1);
        }
    }

    public Class getSuperClass(Class c){
        return c.getSuperclass();
    }

    public Class [] getInterfaces(Class c){
        return c.getInterfaces();
    }

    public String getSimpleName(Class c){
        return c.getSimpleName();
    }

    public Constructor [] getDeclaredConstructors(Class c){
        return c.getDeclaredConstructors();
    }
    
    public Class getClass(Object obj)
	{
		return obj.getClass();
    }
    
    public Object ArrayGetObj(Object obj, int i){
        return Array.get(obj, i);
    }

    public int ArrayGetLength(Object obj){
        return Array.getLength(obj);
    }

    public Field [] getDeclaredFields(Class c){
        return c.getDeclaredFields();
    }

    public Method [] getDeclaredMethods(Class c){
        return c.getDeclaredMethods();
    }

    public String ModifierToString (int i){
        return Modifier.toString(i);
    }

    public String getObjectReference(Object obj, int i){
        String className = getClass(ArrayGetObj(obj, i)).getName();
        String hashCode = Integer.toHexString(System.identityHashCode(ArrayGetObj(obj, i)));
        return className + "@" + hashCode; 
    }

    public String getObjectReference(Object obj, Field field){
        String className = "";
        String hashCode = "";
        try{
            className = getClass(field.get(obj)).getName();
            hashCode = Integer.toHexString(System.identityHashCode(field.get(obj)));
        }
        catch(Exception e){
            System.out.println("Error: something happened" + e);
        }
        return className + "@" + hashCode; 
    }

    public <T> boolean checkArrayEmpty( T [] array){
        if(array.length == 0){
            return true;
        }
        return false;
    }
    
    public void formatOutputDepth(String value, int depth){
        for(int i = 0; i < depth; i++){
            System.out.print("\t");
        }
        System.out.println(value);
    }
}