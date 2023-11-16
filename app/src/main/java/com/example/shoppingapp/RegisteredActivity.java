package com.example.shoppingapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.DatabasePack.Database;
import com.example.shoppingapp.DatabasePack.MySQLiteHelper;
import com.example.shoppingapp.ShopClass.User;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private Button cancel;
    private EditText userName;
    private EditText passWord1;
    private EditText passWord2;

    // Driver Code
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the registered_layout page
        setContentView(R.layout.registered_layout);
        initView();

    }

    /**
     * initView() method whose purpose is to find the views and set the onClickListeners
     */
    private void initView() {
        register = findViewById(R.id.buttonRegisteredR);
        cancel = findViewById(R.id.buttonCancelR);
        userName = findViewById(R.id.editTextUsernameR);
        passWord1 = findViewById(R.id.editTextPasswordR1);
        passWord2 = findViewById(R.id.editTextPasswordR2);
        // set the listeners on the buttons of register and cancel
        register.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }


    /**
     * This function handles different onClick events on different views
     * Called when a button with an attached OnClickListener is clicked.
     * @param view The view that was clicked. (represents the clicked button)
     */
    @Override
    public void onClick(View view) {
        // Handles different scenarios based on the different view ids.
        switch (view.getId()) {
            case R.id.buttonCancelR:    // the cancel button
                // If cancelled, go back to the login page
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                // close the current registration activity
                finish();
                break;

            case R.id.buttonRegisteredR:    // the registration button
                // To obtain the username, password, confirmed password from the user.
                String username = userName.getText().toString().trim();
                String password = passWord2.getText().toString().trim();
                String password2 = passWord1.getText().toString().trim();

                // Validation of the registration
                // If all 3 fields are not empty,
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(password2)) {
                        // Put the username and password into the database
                        Database database = new Database(new MySQLiteHelper(getBaseContext()));
                        // Find the person in the database using the username and password provided.
                        User person;
                        person = database.findPersonFromSQLite(null,username,password);

                        // If the person is a existing one in the database
                        if(person !=null){
                            Toast.makeText(this, "The user already exists, registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        else{
                            // if the 2 passwords don't match
                            if (!password.equals(password2)){
                                Toast.makeText(RegisteredActivity.this,"The passwords you inputted were inconsistent!",Toast.LENGTH_SHORT).show();

                            }else{
                                // Otherwise, a new User object is being created and the related information is assigned.
                                // This user will be inserted into the database and a toast message indicating the success will be displayed.
                                person = new User(username,password);
                                person.setNum(database.getPersonMaxNumFromSQLite()+1);
                                person.setId(String.valueOf(person.getNum()));
                                person.setMoney(0); // 0 initial money
                                database.insertPersonToSQLite(person); // insert the user into the database
                                // Redirect to the LoginActivity
                                Intent intent2 = new Intent(this, LoginActivity.class);
                                startActivity(intent2);
                                // Close the current registration activity
                                finish();
                                Toast.makeText(this,  "The validation passed, registration succeeded.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                break;
                }


        }
    }
