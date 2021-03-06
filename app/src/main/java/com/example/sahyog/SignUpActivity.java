package com.example.sahyog;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import static java.sql.Types.NULL;

public class SignUpActivity extends AppCompatActivity {
    EditText nameText;
    EditText userNameText;
    EditText passwordText;
    EditText reEnterPassword;
    EditText phoneNoText;
    String name;
    String userName;
    String password ;
    String reEnterPass;
    String phoneNo;
    ParseUser user;
    Intent loginIntent;
    Intent chngeIntent;



    public void SignUp (String name,String usrnme , String password, String phoneNo){
        user = new ParseUser();

        user.setUsername(usrnme);
        user.setPassword(password);
        user.put("phone",phoneNo);
        user.put("name",name);


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if( e==null ){
                    Toast.makeText(SignUpActivity.this, "Sign Up : Succesful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignUpActivity.this, "SignUp : unSuccessful :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public  void OnClickAlreadyUser(View view){
        startActivity(loginIntent);
    }

    public void onClickSignUp (View view){
        name = String.valueOf(nameText.getText());
        userName = String.valueOf(userNameText.getText());
        password = String.valueOf(passwordText.getText());
        reEnterPass = String.valueOf(reEnterPassword.getText());
        phoneNo =String.valueOf(phoneNoText.getText());

        if(name == null || userName == null || password == null || reEnterPassword==null || phoneNo == "NULL"){
            Toast.makeText(SignUpActivity.this, "Please fill all the Fields !", Toast.LENGTH_SHORT).show();

        }else {
            if (password.equals(reEnterPass)) {
                SignUp(name,userName,password,phoneNo);
            } else {
                Toast.makeText(SignUpActivity.this, "Passwords not same !", Toast.LENGTH_SHORT).show();

            }

        }
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        nameText = (EditText)findViewById(R.id.nameText);
        userNameText = (EditText)findViewById(R.id.userNameText);
        passwordText = (EditText)findViewById(R.id.passwordText);
        reEnterPassword = (EditText)findViewById(R.id.reEnterPassword);
        phoneNoText = (EditText)findViewById(R.id.phoneNoText);

        loginIntent = new Intent(getApplicationContext(),LoginActivity.class);

        chngeIntent = new Intent(getApplicationContext(),LiveLocationActivity.class);




    }



    public void OnclickChangeIntent(View view){
        startActivity(chngeIntent);
    }
}
