package edu.rit.se.imagine2017.a21Etictactoe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        userID = ServiceCall.SaveUser("21E");
        textViewUser.setText("User ID: " + userID);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hi! \nThanks for installing Tic Tac Toe! \n\nFor a better playing experience the app needs to monitor your call status.")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        dialog.cancel();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Permission Request! \n\n" +
                                "This app is requesting the following permission:\n" +
                                "READ_PHONE_STATE\n\n" +
                                "This permission is also used by the following apps:\n" +
                                "AirDroid, Bank of America, Skype.")
                                .setCancelable(false)
                                .setPositiveButton("Authorize", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                        dialog.cancel();
                                        ServiceCall.SavePermissionAction(userID, "READ_PHONE_STATE", "ALLOW");
                                    }
                                })
                                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                        dialog.cancel();
                                        ServiceCall.SavePermissionAction(userID, "READ_PHONE_STATE", "DENY");
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }


    public void onClick(View v) {
        if (v.getId() == btnComputer.getId()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("To play against your friends we need to access your location.")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                            dialog.cancel();
                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Permission Request! \n\n" +
                                    "This app is requesting the following permission:\n" +
                                    "ACCESS_FINE_LOCATION\n\n" +
                                    "This permission is also used by the following apps:\n" +
                                    "Camera, Facebook, Instagram.")
                                    .setCancelable(false)
                                    .setPositiveButton("Authorize", new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                            dialog.cancel();
                                            ServiceCall.SavePermissionAction(userID, "ACCESS_FINE_LOCATION", "ALLOW");
                                            if (getLocation()) {
                                                loadGame("Friend");
                                            }
                                        }
                                    })
                                    .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                            dialog.cancel();
                                            ServiceCall.SavePermissionAction(userID, "ACCESS_FINE_LOCATION", "DENY");
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                            builder.setMessage("Unable to access your location. You will now play against the Computer.")
                                                    .setCancelable(false)
                                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                                            dialog.cancel();
                                                            loadGame("Computer");
                                                        }
                                                    });
                                            final AlertDialog alert = builder.create();
                                            alert.show();
                                        }
                                    });
                            final AlertDialog alert = builder.create();
                            alert.show();

                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
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
        Intent gameIntent = new Intent("edu.rit.se.imagine2017.a21Etictactoe.GameActivity");

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
