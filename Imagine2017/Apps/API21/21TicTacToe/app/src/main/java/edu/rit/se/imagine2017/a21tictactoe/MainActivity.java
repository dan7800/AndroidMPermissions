package edu.rit.se.imagine2017.a21tictactoe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnHuman, btnComputer;
    TextView textViewUser;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewUser = (TextView) findViewById(R.id.textViewUserID);
        btnComputer = (Button) findViewById(R.id.buttonComputer);
        btnHuman = (Button) findViewById(R.id.buttonHuman);

        setTitle("TicTacToe - Select Game Type");

        // call the web service to create a new user record in the database. The UserId is used as a parameter in other service calls.
        userID = ServiceCall.SaveUser("21");
        textViewUser.setText("User ID: " + userID);

        // when the app loads, ask user to grant permission to access the Phone State info
        getDeviceId();

        ServiceCall.SavePermissionAction(userID, "READ_PHONE_STATE", "ALLOW");
        ServiceCall.SavePermissionAction(userID, "ACCESS_FINE_LOCATION", "ALLOW");

    }


    public void onClick(View v) {
        if (v.getId() == btnComputer.getId()) {
            if (getLocation()) {
                loadGame("Friend");
            }
        }

        //This button is hidden
        if (v.getId() == btnHuman.getId()) {
            loadGame("Self");
        }
    }

    //This button is hidden
    public void onAboutClick(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("About")
                .setMessage("A simple TicTacToe game.")//\n\nFeedback: axp6201@rit.edu")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Close", null)
                .show();
    }

    private void loadGame(String gameType) {
        Intent gameIntent = new Intent("edu.rit.se.imagine2017.a21tictactoe.GameActivity");

        switch (gameType) {
            case "Computer":
                gameIntent.putExtra("GameType", "Computer");
                gameIntent.putExtra("UserID", userID);
                startActivity(gameIntent);
                break;

            case "Friend":
                gameIntent.putExtra("GameType", "Friend");
                gameIntent.putExtra("UserID", userID);
                startActivity(gameIntent);
                break;

        }
    }


    private boolean getDeviceId() {
        return true;
    }

    private boolean getLocation(){
        return true;
    }

}
