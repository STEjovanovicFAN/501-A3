public class ObjectC{
    public int [] array;

    public ObjectC(int size){
        array = new int [size];
    }

    public void set(int index, int value){
        array[index] = value;
    }
}