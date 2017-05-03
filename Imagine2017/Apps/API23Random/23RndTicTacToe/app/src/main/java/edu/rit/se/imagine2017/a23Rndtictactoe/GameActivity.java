package edu.rit.se.imagine2017.a23Rndtictactoe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    final private int REQUEST_PERMISSION_CODE = 1122;
    AppPermission[] permissions;
    String userID;

    private State currentPlayer;
    private int colorCurrentPlayer,colorWinningBackground,colorDefaultBackground;
    private boolean gameCompleted, vsComputer;
    private int availableSquares;

    private String[] friends = {"Fred","Mary","Dave","Lisa","Mark","Pete"};

    private State[][] squares;

    TextView textGameStatus;

    Button btn00,btn01,btn02;
    Button btn10,btn11,btn12;
    Button btn20,btn21,btn22;
    Button btnNewGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btn00 = (Button) findViewById(R.id.button00);
        btn01 = (Button) findViewById(R.id.button01);
        btn02 = (Button) findViewById(R.id.button02);

        btn10 = (Button) findViewById(R.id.button10);
        btn11 = (Button) findViewById(R.id.button11);
        btn12 = (Button) findViewById(R.id.button12);

        btn20 = (Button) findViewById(R.id.button20);
        btn21 = (Button) findViewById(R.id.button21);
        btn22 = (Button) findViewById(R.id.button22);

        textGameStatus = (TextView) findViewById(R.id.textGameStatus);
        btnNewGame = (Button) findViewById(R.id.buttonNewGame);



        btnNewGame.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initializeGame();
                    }
                }
        );

        initializeGame();
    }
    private void systemMove(){
        int row,column;

        while (true) {
            row = (int) (Math.random() * 3 + 0);
            column = (int) (Math.random() * 3 + 0);
            if(squares[row][column] == State.Blank)
            {
                String buttonTag = "["+row+"]["+column+"]";

                TableLayout grid = (TableLayout) findViewById(R.id.tableLayout1);
                int rowCount = grid.getChildCount();
                for(int i=0;i<rowCount;i++){
                    View rowView = grid.getChildAt(i);
                    if(rowView instanceof TableRow){
                        for(int j=0;j<((TableRow) rowView).getChildCount();j++){
                            View v = ((TableRow) rowView).getChildAt(j);
                            if (v instanceof Button){
                                if(v.getTag()!=null && v.getTag().equals(buttonTag)) {
                                    onClick(v);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void onClick(View v) {

        if (!gameCompleted) {

            Button button = (Button) v;

            button.setText(currentPlayer.toString());
            button.setTextColor(colorCurrentPlayer);
            button.setClickable(false);

            if(btn00.getId() == v.getId()){
                squares[0][0] = currentPlayer;
                availableSquares--;
            }
            else if(btn01.getId() == v.getId()){
                squares[0][1] = currentPlayer;
                availableSquares--;
            }
            else if(btn02.getId() == v.getId()){
                squares[0][2] = currentPlayer;
                availableSquares--;
            }
            else if(btn10.getId() == v.getId()){
                squares[1][0] = currentPlayer;
                availableSquares--;
            }
            else if(btn11.getId() == v.getId()){
                squares[1][1] = currentPlayer;
                availableSquares--;
            }
            else if(btn12.getId() == v.getId()){
                squares[1][2] = currentPlayer;
                availableSquares--;
            }
            else if(btn20.getId() == v.getId()){
                squares[2][0] = currentPlayer;
                availableSquares--;
            }
            else if(btn21.getId() == v.getId()){
                squares[2][1] = currentPlayer;
                availableSquares--;
            }
            else if(btn22.getId() == v.getId()){
                squares[2][2] = currentPlayer;
                availableSquares--;
            }

            updateScore();

            if (!gameCompleted) {
                if (availableSquares == 0){
                    gameCompleted = true;
                    textGameStatus.setText("Game Drawn!");
                    gameCompletedPermissions();
                    return;
                }
                else {
                    changePlayer();
                    textGameStatus.setText("Current Player: "+currentPlayer.name());
                    if(currentPlayer == State.O && vsComputer)
                        systemMove();
                }
            }
            else{
                textGameStatus.setText("Winner: "+currentPlayer.name());
                gameCompletedPermissions();
            }

        }
    }


    private void updateScore(){
        if(squares[0][0] == currentPlayer && squares[0][1] == currentPlayer && squares[0][2] == currentPlayer){
            btn00.setBackgroundColor(colorWinningBackground);
            btn01.setBackgroundColor(colorWinningBackground);
            btn02.setBackgroundColor(colorWinningBackground);
            gameCompleted = true;
            return;
        }

        if(squares[1][0] == currentPlayer && squares[1][1] == currentPlayer && squares[1][2] == currentPlayer){
            btn10.setBackgroundColor(colorWinningBackground);
            btn11.setBackgroundColor(colorWinningBackground);
            btn12.setBackgroundColor(colorWinningBackground);
            gameCompleted = true;
            return;
        }

        if(squares[2][0] == currentPlayer && squares[2][1] == currentPlayer && squares[2][2] == currentPlayer){
            btn20.setBackgroundColor(colorWinningBackground);
            btn21.setBackgroundColor(colorWinningBackground);
            btn22.setBackgroundColor(colorWinningBackground);
            gameCompleted = true;
            return;
        }

        if(squares[0][0] == currentPlayer && squares[1][0] == currentPlayer && squares[2][0] == currentPlayer){
            btn00.setBackgroundColor(colorWinningBackground);
            btn10.setBackgroundColor(colorWinningBackground);
            btn20.setBackgroundColor(colorWinningBackground);
            gameCompleted = true;
            return;
        }

        if(squares[0][1] == currentPlayer && squares[1][1] == currentPlayer && squares[2][1] == currentPlayer){
            btn01.setBackgroundColor(colorWinningBackground);
            btn11.setBackgroundColor(colorWinningBackground);
            btn21.setBackgroundColor(colorWinningBackground);
            gameCompleted = true;
            return;
        }

        if(squares[0][2] == currentPlayer && squares[1][2] == currentPlayer && squares[2][2] == currentPlayer){
            btn02.setBackgroundColor(colorWinningBackground);
            btn12.setBackgroundColor(colorWinningBackground);
            btn22.setBackgroundColor(colorWinningBackground);
            gameCompleted = true;
            return;
        }

        if(squares[0][0] == currentPlayer && squares[1][1] == currentPlayer && squares[2][2] == currentPlayer){
            btn00.setBackgroundColor(colorWinningBackground);
            btn11.setBackgroundColor(colorWinningBackground);
            btn22.setBackgroundColor(colorWinningBackground);
            gameCompleted = true;
            return;
        }

        if(squares[0][2] == currentPlayer && squares[1][1] == currentPlayer && squares[2][0] == currentPlayer){
            btn02.setBackgroundColor(colorWinningBackground);
            btn11.setBackgroundColor(colorWinningBackground);
            btn20.setBackgroundColor(colorWinningBackground);
            gameCompleted = true;
            return;
        }


    }

    private void changePlayer(){
        currentPlayer = currentPlayer.equals(State.X)? State.O: State.X;
        colorCurrentPlayer=  colorCurrentPlayer == Color.argb(255,236,26,97)?  Color.argb(255,255,137,2):Color.argb(255,236,26,97);
    }

    private void initializeGame(){
        colorCurrentPlayer = Color.argb(255,236,26,97);
        colorDefaultBackground = Color.parseColor("#ffd6d7d7");
        colorWinningBackground = Color.parseColor("#FF34B5E5");

        squares = new State[3][3];
        gameCompleted = false;
        availableSquares = 9;
        currentPlayer = State.X;

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                squares[i][j] = State.Blank;

        btn00.setText("");
        btn01.setText("");
        btn02.setText("");
        btn10.setText("");
        btn11.setText("");
        btn12.setText("");
        btn20.setText("");
        btn21.setText("");
        btn22.setText("");

        btn00.setClickable(true);
        btn01.setClickable(true);
        btn02.setClickable(true);
        btn10.setClickable(true);
        btn11.setClickable(true);
        btn12.setClickable(true);
        btn20.setClickable(true);
        btn21.setClickable(true);
        btn22.setClickable(true);

        btn00.setBackgroundColor(colorDefaultBackground);
        btn01.setBackgroundColor(colorDefaultBackground);
        btn02.setBackgroundColor(colorDefaultBackground);
        btn10.setBackgroundColor(colorDefaultBackground);
        btn11.setBackgroundColor(colorDefaultBackground);
        btn12.setBackgroundColor(colorDefaultBackground);
        btn20.setBackgroundColor(colorDefaultBackground);
        btn21.setBackgroundColor(colorDefaultBackground);
        btn22.setBackgroundColor(colorDefaultBackground);


        textGameStatus.setText("Current Player: "+currentPlayer.name());

        String oponent = "Computer";
        userID = getIntent().getStringExtra("UserID");
        permissions = (AppPermission[]) getIntent().getSerializableExtra("Permissions");

        if(getIntent().getStringExtra("GameType").equalsIgnoreCase("Computer") || getIntent().getStringExtra("GameType").equalsIgnoreCase("Friend")){
            vsComputer = true;
            if(getIntent().getStringExtra("GameType").equalsIgnoreCase("Friend")){
                Random r = new Random();
                oponent = friends[r.nextInt(friends.length)];
            }
        }
       // vsComputer = getIntent().getStringExtra("GameType").equalsIgnoreCase("Computer")?true:false;

        if(vsComputer){
            setTitle("TicTacToe - Play Against " + oponent);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("You Vs. " + oponent)
                    .setMessage("You start the game. You're 'X'.\n\nGood Luck!")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("Close", null)
                    .show();
        }
        else
            setTitle("TicTacToe - Play Against Myself!");

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];

                    if (permission.equals(this.permissions[2].Permission)) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            ServiceCall.SavePermissionAction(userID, this.permissions[2].PermissionDB, "DENY");
                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage(this.permissions[2].PermissionRequestDenyText)
                                    .setCancelable(false)
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                            dialog.cancel();
                                        }
                                    });
                            final AlertDialog alert = builder.create();
                            alert.show();
                        } else {
                            ServiceCall.SavePermissionAction(userID,this.permissions[2].PermissionDB, "ALLOW");
                        }
                    }

                    if (permission.equals(this.permissions[3].Permission)) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            ServiceCall.SavePermissionAction(userID, this.permissions[3].PermissionDB, "DENY");
                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage(this.permissions[3].PermissionRequestDenyText)
                                    .setCancelable(false)
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                            dialog.cancel();
                                        }
                                    });
                            final AlertDialog alert = builder.create();
                            alert.show();
                        } else {
                            ServiceCall.SavePermissionAction(userID,this.permissions[3].PermissionDB, "ALLOW");
                        }
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void gameCompletedPermissions(){
        AccessPermissionAPI(permissions[2]);
        AccessPermissionAPI(permissions[3]);
    }

    public boolean AccessPermissionAPI(AppPermission permission){
        if (ActivityCompat.checkSelfPermission(this, permission.Permission) != PackageManager.PERMISSION_GRANTED) {
            if ((int) Build.VERSION.SDK_INT >= 23) {
                requestPermissions(new String[]{permission.Permission}, REQUEST_PERMISSION_CODE);
            }
            return false;
        }
        return true;
    }

    private enum State{
        X,O,Blank
    }

}
