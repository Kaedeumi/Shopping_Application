package com.example.shoppingapp.DatabasePack;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shoppingapp.ShopClass.Goods;
import com.example.shoppingapp.ShopClass.User;

import java.util.ArrayList;

public class Database {
    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase database;

    public Database(MySQLiteHelper mySQLiteHelper){
        this.mySQLiteHelper = mySQLiteHelper;
    }


    /**
     * Inserts a new person into the database
     *
     * @param person an User object to be inserted
     */
    public void insertPersonToSQLite(User person){
        database = mySQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Attributes of Persons
        values.put("id",person.getId());
        values.put("num",person.getNum());
        values.put("username",person.getName());
        values.put("password",person.getPassword());
        values.put("money",person.getMoney());
        long id = database.insert("person",null,values);
        database.close();
    }

    /**
     * Retrieves the maximum value of the num column of the person table
     *
     * @return an integer result denoting the max index
     */
    public int getPersonMaxNumFromSQLite(){
        database = mySQLiteHelper.getWritableDatabase();
        int result;
        Cursor cursor = database.rawQuery("SELECT MAX(num) FROM person",null);
        if(cursor.getCount() ==0){
            return 0;
        } else {
            cursor.moveToFirst();
            result = cursor.getInt(0);
        }
        return result;
    }

    /**
     * Update the Person's information data on the database
     * @param person a User object to be updated
     *
     * @return how many rows are affected after this operation
     */
    public int updatePersonToSQLite(User person){
        database = mySQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("num",person.getNum());
        values.put("username",person.getName());
        values.put("password",person.getPassword());
        values.put("money",person.getMoney());
        int number = database.update("person",values,"id=?",new String[]{person.getId()});
        database.close();
        return number;
    }

    /**
     * Delete the specified person from the database
     * @param person The person to be deleted
     * @return how many rows are affected
     */
    public int deletePersonToSQLite(User person){
        database = mySQLiteHelper.getWritableDatabase();
        int number = database.delete("person","id =?",new String[]{person.getId()});
        database.close();
        return number;
    }

    /**
     * This method retrieved whether the specified person exists in the database or not.
     * Prioritize ID, if ID == null then retrieve the username
     * If the password is null then return the information of the 1st username, otherwise match the username and password
     * If not able to be retrieved, return null
     *
     * @param id the id of the person to be found
     * @param username the username of the person
     * @param password the password of the person
     * @return the user to be found if found, else null
     */
    public User findPersonFromSQLite(String id,String username,String password){
        database = mySQLiteHelper.getReadableDatabase();
        User person = new User(username,null);
        // Defines a cursor object using which the database is being retrieved.
        Cursor cursor ;
        // If there is an id
        if(id != null && (!id.isEmpty())){
            // SELECT * FROM person WHERE id = ? (? is a placeholder for the actual value）
            cursor = database.query("person",null,"id=?",new String[]{id},null,null,null);
            if (cursor.getCount() ==0){
                // cursor.getCount() == 0 means there is no matching result, then return null
                database.close();
                return null;
            }
            // Otherwise, move the cursor to the 1st row and then populate the person object with the user's data
            cursor.moveToFirst();
            person.setId(cursor.getString(0));
            person.setNum(cursor.getInt(1));
            person.setName(cursor.getString(2));
            person.setPassword(cursor.getString(3));
            person.setMoney(cursor.getInt(4));
            cursor.close();
            database.close();
            return person;

        // If no id but the user's name isn't null and his/her name isn't empty
        } else if(username != null && (!username.isEmpty())){

            // SELECT * FROM person WHERE username = ?
            cursor = database.query("person",null,"username=?",new String[]{username},null,null,null);
            // If no user returned, return null
            if (cursor.getCount() ==0){
                database.close();
                return null;
            // else if there is exactly 1 matching row, check the password
            } else if(cursor.getCount() ==1){
                cursor.moveToFirst();
                // If there is password
                if (password != null){
                    // If the password is exactly the same password as registered
                    if (password.equals(cursor.getString(3))){
                        // Set the person attributes as retrieved
                        person.setId(cursor.getString(0));
                        person.setNum(cursor.getInt(1));
                        person.setName(cursor.getString(2));
                        person.setPassword(cursor.getString(3));
                        person.setMoney(cursor.getInt(4));
                    // Else if the password isn't correct
                    } else {
                        // return null
                        person = null;
                    }
                // If there isn't a password
                } else {
                    // set the user's attribute
                    person.setId(cursor.getString(0));
                    person.setNum(cursor.getInt(1));
                    person.setName(cursor.getString(2));
                    person.setPassword(cursor.getString(3));
                    person.setMoney(cursor.getInt(4));
                }
                cursor.close();
                database.close();
                return person;
            // Else if there are multiple matching usernames, iterate through them to find a match with the password provided
            } else {
                cursor.moveToFirst();
                // If a person with matching password is found
                if (cursor.getString(3).equals(password)){
                    // Set the person's attribute
                    person.setId(cursor.getString(0));
                    person.setNum(cursor.getInt(1));
                    person.setName(cursor.getString(2));
                    person.setPassword(cursor.getString(3));
                    person.setMoney(cursor.getInt(4));
                // Else if the password in the CURRENT ROW doesn't match the password, execute this else block
                } else {
                    // Set the existence to be false
                    boolean isExist = false;
                    // This loop iterated through the list as long as there are more rows to check
                    while(cursor.moveToNext()){
                        // if any row with the correct password is being found
                        if (cursor.getString(3).equals(password)){
                            // populate the attributes and set the existance to be true, and breaks from the loop
                            cursor.moveToFirst();
                            person.setId(cursor.getString(0));
                            person.setNum(cursor.getInt(1));
                            person.setName(cursor.getString(2));
                            person.setPassword(cursor.getString(3));
                            person.setMoney(cursor.getInt(4));
                            isExist = true;
                            break;
                        }
                    }
                    // If still doesn't exist after a whole iteration on all the rows, set the person to null
                    if(!isExist) {
                        person = null;
                    }
                }
                cursor.close();
                database.close();
                return person;
            }
        // If neither id nor name is being provided, retrun null
        }else {
            database.close();
            return null;
        }
    }

    /**
     * This method inserts goods to the database
     *
     * @param person the person entity
     * @param goods the goods entity
     */
    public void insertGoodsToSQLite(User person, Goods goods){
        database = mySQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",person.getId());
        values.put("goodsName",goods.getTitle());
        values.put("goodsPrice",goods.getPrice());
        values.put("icon",goods.geticon());
        // name the database to be shoppingDATA
        database.insert("shoppingDATA",null,values);
        database.close();
    }

    /**
     * Deleted goods from the database
     *
     * @param person the person entity
     * @param goods the goods entity
     * @return how many rows are affected
     */
    public int deleteGoodsToSQLite(User person,Goods goods){
        database = mySQLiteHelper.getWritableDatabase();
        int number = database.delete("shoppingDATA","id =? and goodsName =?",new String[]{person.getId(),goods.getTitle()});
        database.close();
        return number;
    }


    /**
     * Finds the list of goods associated with a person from the shoppingDATA database
     *
     * @param person the user information
     * @return an ArrayList of Goods
     */
    public ArrayList<Goods> findGoodsListFromSQLite(User person){
        database = mySQLiteHelper.getReadableDatabase();
        // Initializes an ArrayList to store the goods information retrieved from the database
        ArrayList<Goods> list = new ArrayList<>();
        // Selects all columns from shoppingDATA table where the id column is exactly the same as provided
        Cursor cursor = database.query("shoppingDATA",null,"id=?",new String[]{person.getId()},null,null,null);

        // if there is nothing found
        if (cursor.getCount() == 0){
            // close and return the empty list
            cursor.close();
            database.close();
            return list;
        } else {
            // If there are some matching rows
            cursor.moveToFirst();
            // get the name, price, num and store them as the Goods attribute
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            int num = cursor.getInt(3);
            Goods goods = new Goods(name,price,"",num);
            // add the goods object to the list
            list.add(goods);
            // iterate through the rest of the list and add every row as a new Goods object in the list
            while (cursor.moveToNext()){
                name = cursor.getString(1);
                price = cursor.getString(2);
                num = cursor.getInt(3);
                list.add(new Goods(name,price,"",num));
            }
            cursor.close();
            database.close();
            // return the list of Goods containing all the information of Goods being retrieved
            return list;
        }
    }

}
