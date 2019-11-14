import java.util.ArrayList;
import java.util.List;

public class ObjectCreator{
    public ArrayList<ObjectA> listOfObjA;
    public ArrayList<ObjectB> listOfObjB;
    public ArrayList<ObjectC> listOfObjC;
    public ArrayList<ObjectD> listOfObjD;

    public ObjectCreator(){
        listOfObjA = new ArrayList<ObjectA>();
        listOfObjB = new ArrayList<ObjectB>();
        listOfObjC = new ArrayList<ObjectC>();
        listOfObjD = new ArrayList<ObjectD>();
    }

    public boolean createObjectA(String name){
        try{
            ObjectA a = new ObjectA(name);
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
        c.array[index] = value;
        return true;
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

    public boolean setObjectDValue(ObjectD d, int index, String aIndexInput){
        int aIndex;
        try{
            aIndex = Integer.parseInt(aIndexInput);
        }
        catch(Exception e){
            return false;
        }

        if(aIndex >= listOfObjA.size()){
            return false;
        }

        d.array[index] = listOfObjA.get(aIndex);
        return true;
    }
}