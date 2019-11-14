public class ObjectC{
    public String [] array;

    public ObjectC(int size){
        array = new String [size];
    }

    public void set(int index, String value){
        array[index] = value;
    }
}