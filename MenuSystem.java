import java.util.Scanner;

public class MenuSystem{
    private Scanner input;
    private ObjectCreator oCreator;

    public MenuSystem(ObjectCreator objc){
        oCreator = objc;
        input = new Scanner(System.in);
    }

    public void start (){
        boolean stop = false;
        String userInput;

        while(!stop){
            displayMenu();
            userInput = input.nextLine();
            parseInput(userInput);
            stop = checkQuit(userInput);
        } 
    }

    private void displayMenu(){
        System.out.print(
        "\n[1] Simple Object with primative variables" +
        "\n[2] Object that contains references to other Objects" + 
        "\n[3] Object with array of primatives" +
        "\n[4] Object that contains an array of object references" +
        "\n[5] Create an ArrayList" +
        "\n[S] Send" + 
        "\n[Q] Quit" +
        "\n---------------------------------" +
        "\nUser Input: ");
    }

    private boolean checkQuit(String userInput){
        if(userInput.equalsIgnoreCase("s"))
            return true;
        else if(userInput.equalsIgnoreCase("q"))
            System.exit(0);
        return false;
    }

    private void parseInput(String userInput){
        if(userInput.equalsIgnoreCase("1"))
            firstOption();
        else if (userInput.equalsIgnoreCase("2"))
            secondOption();
        else if (userInput.equalsIgnoreCase("3"))
            System.out.println("3");
        else if (userInput.equalsIgnoreCase("4"))
            System.out.println("4");
        else if (userInput.equalsIgnoreCase("5"))
            System.out.println("5");
    }

    private void secondOption(){
        ObjectB b = oCreator.createObjectB();
        secondOptionDisplay();
        if(oCreator.setObjectBValue(b, Integer.parseInt(input.nextLine()))){
            createdObjMsg();
        }
        else{
            objErrorMsg();
        }
    }

    private void secondOptionDisplay(){
        System.out.println("\nDisplaying list of all object B's: ");
        for(int i = 0; i < oCreator.listOfObjB.size(); i++){
            System.out.println("- ObjectB[" + i + "]");
        }
        System.out.print("\nSet the object reference for this object: ");
    }


    private void firstOption(){
        firstOptionDisplay();
        String name = input.nextLine();
        if(oCreator.createObjectA(name)){
            createdObjMsg();
        }
        else{
            objErrorMsg();
        }
    }
    
    private void firstOptionDisplay(){
        System.out.print("\n\nSet the value \"name\" for this object: ");
    }

    private void createdObjMsg(){
        System.out.println("Object was successfully created!");
    }

    private void objErrorMsg(){
        System.out.println("Object was not successfully created...");
    }
}