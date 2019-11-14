public class ObjectD{
    public ObjectA [] array;

    public ObjectD(int size){
        array = new ObjectA [size];
    }

    public void set(int index, ObjectA value){
        array[index] = value;
    }
}