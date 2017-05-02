package edu.rit.se.imagine2017.a23tictactoe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnHuman, btnComputer;
    TextView textViewUser;
    String userID;

    final private int REQUEST_PERMISSION_CODE = 1122;

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
        userID = ServiceCall.SaveUser("23");
        textViewUser.setText("User ID: " + userID);

        // when the app loads, ask user to grant permission to access the Phone State info
        getDeviceId();
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
    public void onAboutClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("About")
                .setMessage("A simple TicTacToe game.")//\n\nFeedback: axp6201@rit.edu")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Close", null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];

                    // Permission required for "Play Against Myself!"
                    if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            ServiceCall.SavePermissionAction(userID, "READ_PHONE_STATE", "DENY");
                        } else {
                            ServiceCall.SavePermissionAction(userID, "READ_PHONE_STATE", "ALLOW");
                        }
                    }

                    // Permission required for "Play Against Friend!"
                    if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            ServiceCall.SavePermissionAction(userID, "ACCESS_FINE_LOCATION", "DENY");
                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                        } else {
                            ServiceCall.SavePermissionAction(userID, "ACCESS_FINE_LOCATION", "ALLOW");
                            loadGame("Friend");
                        }
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void loadGame(String gameType) {
        Intent gameIntent = new Intent("edu.rit.se.imagine2017.a23tictactoe.GameActivity");

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

            /*
            case "Self":
                if (!getDeviceId()) {
                    showPemissionMessage("Phone");
                    return;
                }
                gameIntent.putExtra("GameType", "Human");
                gameIntent.putExtra("UserID", userID);
                startActivity(gameIntent);
                break;
             */
        }
    }

    private void showPemissionMessage(String permission) {
        switch (permission) {
            case "Contacts":
                Toast.makeText(this, "Permission to access Contacts not granted! Cannot play with friends!", Toast.LENGTH_SHORT).show();
                break;
            case "Phone":
                Toast.makeText(this, "Permission to access Phone details not granted! Cannot play against yourself!", Toast.LENGTH_SHORT).show();
                break;
            case "Location":
                Toast.makeText(this, "Permission to access location not granted! Cannot play with friends!", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private boolean getDeviceId() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if ((int) Build.VERSION.SDK_INT >= 23) {
                requestPermissions(new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_CODE);
            }
            return false;
        }
        /* we don't need to access any info related to the permission
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Toast.makeText(this, "Voicemail Number: "+telephonyManager.getVoiceMailNumber(), Toast.LENGTH_SHORT).show();
        */
        return true;
    }

    private boolean getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if ((int) Build.VERSION.SDK_INT >= 23) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_CODE);
            }
            return false;
        }

        //we don't really need to access the API methods related to this permission
        /*
        try {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Define the criteria how to select the location provider
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);   //default
            criteria.setCostAllowed(false);
            // get the best provider depending on the criteria
            String provider = locationManager.getBestProvider(criteria, false);

            Location location = locationManager.getLastKnownLocation(provider);
            Toast.makeText(this, "Lat: " + location.getLatitude() + " Lng: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception error){
        }
        */
        return true;
    }
}