package edu.rit.se.imagine2017.a23Rndtictactoe;

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
    AppPermission[] permissions;

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

        setTitle("[23R]TicTacToe - Select Game Type");

        permissions = RandomPermission.GenerateRandomPermissionSet();

        // call the web service to create a new user record in the database. The UserId is used as a parameter in other service calls.
        userID = ServiceCall.SaveUser("23Rnd");
        textViewUser.setText("User ID: " + userID);

        /*
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hi! \nThanks for installing Tic Tac Toe! \n\n" + permissions[0].PermissionRequestText)
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        AccessPermissionAPI(permissions[0]);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
        */
    }


    public void onClick(View v) {
        if (v.getId() == btnComputer.getId()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(permissions[0].PermissionRequestText)
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                            if (AccessPermissionAPI(permissions[0])) {
                                loadGame("Friend");
                            }
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
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

                    /*
                    if (permission.equals(this.permissions[0].Permission)) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            ServiceCall.SavePermissionAction(userID, this.permissions[0].PermissionDB, "DENY");
                        } else {
                            ServiceCall.SavePermissionAction(userID, this.permissions[0].PermissionDB, "ALLOW");
                        }
                    }
                    */

                    if (permission.equals(this.permissions[0].Permission)) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            ServiceCall.SavePermissionAction(userID, this.permissions[0].PermissionDB, "DENY");
                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage(this.permissions[0].PermissionRequestDenyText)
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
                            ServiceCall.SavePermissionAction(userID, this.permissions[0].PermissionDB, "ALLOW");
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
        Intent gameIntent = new Intent("edu.rit.se.imagine2017.a23Rndtictactoe.GameActivity");

        switch (gameType) {
            case "Computer":
                gameIntent.putExtra("GameType", "Computer");
                gameIntent.putExtra("UserID", userID);
                gameIntent.putExtra("Permissions", permissions);
                startActivity(gameIntent);
                break;

            case "Friend":
                gameIntent.putExtra("GameType", "Friend");
                gameIntent.putExtra("UserID", userID);
                gameIntent.putExtra("Permissions", permissions);
                startActivity(gameIntent);
                break;
        }
    }


    public boolean AccessPermissionAPI(AppPermission permission) {
        if (ActivityCompat.checkSelfPermission(this, permission.Permission) != PackageManager.PERMISSION_GRANTED) {
            if ((int) Build.VERSION.SDK_INT >= 23) {
                requestPermissions(new String[]{permission.Permission}, REQUEST_PERMISSION_CODE);
            }
            return false;
        }
        return true;
    }
}
