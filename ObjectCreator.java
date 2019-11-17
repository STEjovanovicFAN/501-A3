import java.util.ArrayList;
import java.util.List;

public class ObjectCreator{
    public ArrayList<ObjectA> listOfObjA;
    public ArrayList<ObjectB> listOfObjB;
    public ArrayList<ObjectC> listOfObjC;
    public ArrayList<ObjectD> listOfObjD;
    public ArrayList<ObjectE> listOfObjE;

    public ObjectCreator(){
        listOfObjA = new ArrayList<ObjectA>();
        listOfObjB = new ArrayList<ObjectB>();
        listOfObjC = new ArrayList<ObjectC>();
        listOfObjD = new ArrayList<ObjectD>();
        listOfObjE = new ArrayList<ObjectE>();
    }

    public boolean createObjectA(String value){
        try{
        	int num = Integer.parseInt(value);
            ObjectA a = new ObjectA(num);
            listOfObjA.add(a);
            return true;
        }

        catch(Exception e){
            return false;
        }
    }

    public ObjectB createObjectB(){
        try{
            ObjectB b = new ObjectB();
            listOfObjB.add(b);
            return b;
        }

        catch(Exception e){
            return null;
        }
    }

    public boolean setObjectBValue(ObjectB objB, int position){
        if(position >= listOfObjB.size()){
            return false;
        }
        objB.objB = listOfObjB.get(position);
        return true;
    }

    public ObjectC createObjectC(String size){
        int length = 0;
        try{
            length = Integer.parseInt(size);
        }
        catch(Exception e){
            return null;
        }
        ObjectC c = new ObjectC(length);
        listOfObjC.add(c);

        return c;
    }

    public boolean setObjectCValue(ObjectC c, int index, String value){
        try{
        	int num = Integer.parseInt(value);
        	c.array[index] = num;
            return true;
        }

        catch(Exception e){
            return false;
        }
    }

    public ObjectD createObjectD(String size){
        int length = 0;
        try{
            length = Integer.parseInt(size);
        }
        catch(Exception e){
            return null;
        }

        ObjectD d = new ObjectD(length);
        listOfObjD.add(d);
        return d;
    }

    public boolean setObjectDValue(ObjectD d, int index, String aObjectName){
        if(!createObjectA(aObjectName)) {
        	return false;
        }

        d.array[index] = listOfObjA.get(listOfObjA.size() -1);
        return true;
    }
    
    public boolean setObjectDValue(ObjectD d, int index, ObjectA a){
        d.array[index] = a;
        return true;
    }

    public ObjectE createObjectE(){
        ObjectE e = new ObjectE();
        listOfObjE.add(e);
        return e;
    }

    public boolean addObjectEValue(ObjectE e, String aObjectVal){
        if(createObjectA(aObjectVal)) {
        	e.addObjectA(listOfObjA.get(listOfObjA.size()-1));
        	return true;
        }
        else {
        	return false;
        }
    }
    
    public boolean addObjectEValue(ObjectE e, ObjectA objectA){
    	e.addObjectA(objectA);
    	return true;
    }

	public void setObjectBValue(ObjectB oB, ObjectB b) {
		oB.objB = b;
	}

}