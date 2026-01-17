package online.jeffdev;

import online.jeffdev.model.Task;
import online.jeffdev.persistence.JsonFilePersistence;
import online.jeffdev.persistence.Persistence;

public class App 
{
    public static void main( String[] args )
    {
        

        if(args.length == 2){
            String action = args[0];
            String info = args[1];

            switch(action){
                case "add":
                    add(info);
                    break;
                case "other":
                    System.out.println("Not implemented");
                    break;
                default:
                    System.out.println("Wrong command");           
            }
        }
        
    }

    private static void add(String description){
        Persistence jsonFile = new JsonFilePersistence();
        Task taskCreated = jsonFile.addNewTask(new Task(description));
        System.out.println("Task added succesfully (ID: "+ taskCreated.getId()+")");
    }
}
