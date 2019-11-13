import java.util.ArrayList;
import java.util.List;

public class ObjectCreator{
    public ArrayList<ObjectA> listOfObjA;
    public ArrayList<ObjectB> listOfObjB;


    public ObjectCreator(){
        listOfObjA = new ArrayList<ObjectA>();
        listOfObjB = new ArrayList<ObjectB>();
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
}