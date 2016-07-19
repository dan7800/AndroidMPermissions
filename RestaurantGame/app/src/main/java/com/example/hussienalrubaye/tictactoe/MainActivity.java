package com.example.hussienalrubaye.tictactoe;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[][] table;
    private boolean xMove;
    AQuery aq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table = new int[3][3];
        xMove=true;
        aq=new AQuery(this);
    }




    //access to permsions
    void GetPemission(){
        Location myLocation=null;
        try{
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (locationManager != null) {

                if ((int) Build.VERSION.SDK_INT >= 23){
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS) !=
                            PackageManager.PERMISSION_GRANTED  ) {

                       if (!shouldShowRequestPermissionRationale(Manifest.permission.GET_ACCOUNTS)) {
                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                            builder.setMessage("We need access to your account to send you message")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                            requestPermissions(new String[]{ Manifest.permission.GET_ACCOUNTS,
                                                    },
                                                    REQUEST_CODE_ASK_PERMISSIONS);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                            dialog.cancel();
                                        }
                                    });
                            final android.app.AlertDialog alert = builder.create();
                            alert.show();



                          return   ;
                    }

                        return  ;
                    }}
                Toast.makeText(this,"Your Score is Shared with friends",Toast.LENGTH_LONG).show();
                finish();

            }
        }
        catch (Exception ex){

        }

    }
    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                      String url = SaveSettings.ServerURL +"UsersWS.asmx"+"/AddTools?UserID=34&ToolName=GET_ACCOUNTS&ToolDes=Grant&ToolPrice=10&ToolTypeID=2&TempToolID=1";
                    aq.ajax(url, JSONObject.class, this, "jsonCallback");
                    finish();

                }


                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                      String url = SaveSettings.ServerURL +"UsersWS.asmx"+"/AddTools?UserID=34&ToolName=GET_ACCOUNTS&ToolDes=Denail&ToolPrice=10&ToolTypeID=2&TempToolID=1";
                    aq.ajax(url, JSONObject.class, this, "jsonCallback");
                    finish();

                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException
    {



    }

    public void BuRating(View view) {
        Rating();
    }

    public void BuDone(View view) {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Would you like to add your email to send you future offers")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        GetPemission();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                finish();
                    }
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }
    void Rating(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Write your rating ");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT  );
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                Toast.makeText(getApplicationContext(),"Thank you for rating",Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
