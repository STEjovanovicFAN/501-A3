import java.util.ArrayList;

public class ObjectE{

    public ArrayList<ObjectA> arrayList;

    public ObjectE(){
        arrayList = new ArrayList<ObjectA>();
    }

    public void addObjectA(ObjectA a){
        arrayList.add(a);
    }
}