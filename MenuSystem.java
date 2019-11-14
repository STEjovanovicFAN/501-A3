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
        "\n[5] Create an ArrayList and add onto it" +
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
            thirdOption();
        else if (userInput.equalsIgnoreCase("4"))
            fourthOption();
        else if (userInput.equalsIgnoreCase("5"))
            fifthOption();
    }

    private void fifthOption(){
        ObjectE e = oCreator.createObjectE();
        fifthOptionDisplayQuitText();
        while(true){
            fifthOptionDisplayCreateObject();
            String in = input.nextLine();

            if(in.equalsIgnoreCase("d"))
                break;
            else
                oCreator.addObjectEValue(e, in);
        }
        createdObjMsg();
    }

    private void fifthOptionDisplayCreateObject(){
        System.out.print("\nCreate ObjectA to add to this arraylist: ");
    }

    private void fifthOptionDisplayQuitText(){
        System.out.println("Note: Type in [D] to finish adding ObjectA's to the array");
    }

    private void fourthOption(){
        fourthOptionDisplayArrayLength();
        ObjectD d = oCreator.createObjectD(input.nextLine());
        if(d == null){
            objErrorMsg();
            return;
        }

        for(int i = 0; i < d.array.length; i++){
            fourthOptionCreateObject(i);
            if(!oCreator.setObjectDValue(d, i, input.nextLine())){
                System.out.println("Object was incorrectly created");
            }
        }
        createdObjMsg();

    }
    
    private void fourthOptionCreateObject(int index){
        System.out.print("\nCreate ObjectA for the index value " + index + ": ");
    }

    private void fourthOptionDisplayArrayLength(){
        System.out.print("\nSet the array length for this object: ");
    }

    private void thirdOption(){
        thirdOptionDisplayArrayLength();
        ObjectC c = oCreator.createObjectC(input.nextLine());
        if(c == null){
            objErrorMsg();
            return;
        }

        for(int i = 0; i < c.array.length; i++){
            thirdOptionDisplayArrayValue(i);
            if(!oCreator.setObjectCValue(c, i, input.nextLine())){
                objErrorMsg();
            }
        }
        createdObjMsg();
    }

    private void thirdOptionDisplayArrayLength(){
        System.out.print("\nSet the array length for this object: ");
    }

    private void thirdOptionDisplayArrayValue(int index){
        System.out.print("\nSet the array value at index " + index + ": ");
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
        System.out.print("\nSet the value \"name\" for this object: ");
    }

    private void createdObjMsg(){
        System.out.println("Object was successfully created!");
    }

    private void objErrorMsg(){
        System.out.println("Object was not successfully created...");
    }
}