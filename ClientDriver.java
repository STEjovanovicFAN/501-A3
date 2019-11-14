public class ClientDriver{
    public static void main(String[] args) {
        ObjectCreator oCreator = new ObjectCreator();

        MenuSystem menu = new MenuSystem(oCreator);
        menu.start();
        
        //System.out.println(String.join(" ", oCreator.listOfObjC.get(0).array));
    }
}