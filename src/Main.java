/**
 * Created by Snap on 16.11.2015.
 */
public class Main {
    public static void main(String[] args){
        DatabaseHelper databaseHelper = new DatabaseHelper();

        System.out.println(databaseHelper.checkUser("demonlord32", "1234"));
    }
}
