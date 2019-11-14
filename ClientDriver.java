public class ClientDriver{
    public static void main(String[] args) {
        ObjectCreator oCreator = new ObjectCreator();

        MenuSystem menu = new MenuSystem(oCreator);
        menu.start();
        
        Serializer s = new Serializer(oCreator);

        //send 

    }
}