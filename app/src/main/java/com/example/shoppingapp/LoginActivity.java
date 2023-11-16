package com.example.shoppingapp;




import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.DatabasePack.Database;
import com.example.shoppingapp.DatabasePack.MySQLiteHelper;
import com.example.shoppingapp.ShopClass.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private Button login;
    private EditText userName;
    private EditText passWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting the login view
        setContentView(R.layout.login_layout);
        initView();

    }

    private void initView() {
        login = findViewById(R.id.buttonLogin);
        register = findViewById(R.id.buttonRegistered);
        userName = findViewById(R.id.editTextUsername);
        passWord = findViewById(R.id.editTextPassword);
        register.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    /**
     * This function handles different onClick events on different views
     * @param view
     */
    @Override
    public void onClick(View view) {
        // redirect to different activities depending on the different views
        switch (view.getId()) {
            // Direct to the registration page
            case R.id.buttonRegistered:
                startActivity(new Intent(this, RegisteredActivity.class));
                finish();
                break;

                // Direct to the login page
            case R.id.buttonLogin:
                // get the username and password inputted by the user
                String username = userName.getText().toString().trim();
                String password= passWord.getText().toString().trim();

                // if the user has inputted both username and password,
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    // Open the database and looks for the person
                    Database database = new Database(new MySQLiteHelper(getBaseContext()));
                    User person = database.findPersonFromSQLite(null,username,password);

                    // If no result is returned, return false alarm
                    if (person == null){
                        Toast.makeText(getBaseContext(), "Wrong username or password, please check again before logging in", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        // Otherwise, the user exists, direct to the MainActivity
                        Intent intent = new Intent(this, MainActivity.class);
                        // Create Bundle objects
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username);
                        bundle.putString("password", password);
                        bundle.putString("id",person.getId());

                        // Pass the data packet using intent
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();// destroy the activity
                    }
                }
                else {
                    // If the user hasn't provided BOTH username and password
                    Toast.makeText(this, "Please input your username and password", Toast.LENGTH_SHORT).show();
                }
                break;
        }



    }

}


