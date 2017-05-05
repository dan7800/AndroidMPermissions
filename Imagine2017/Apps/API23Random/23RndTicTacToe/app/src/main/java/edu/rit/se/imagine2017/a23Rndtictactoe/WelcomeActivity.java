package edu.rit.se.imagine2017.a23Rndtictactoe;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    TextView textUserId;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        textUserId =  (TextView) findViewById(R.id.textViewUserId);
        setTitle("[23R]TicTacToe - Select Game Type");

        // call the web service to create a new user record in the database. The UserId is used as a parameter in other service calls.
        userID = ServiceCall.SaveUser("23Rnd");
        textUserId.setText("User ID: " + userID);
    }

    public void onClick(View v) {
        Intent gameIntent = new Intent("edu.rit.se.imagine2017.a23Rndtictactoe.MainActivity");
        gameIntent.putExtra("UserID", userID);
        startActivity(gameIntent);
    }
}
