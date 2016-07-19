package com.example.hussienalrubaye.tictactoe;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.games.Player;
import com.google.android.gms.games.Players;

import java.util.ArrayList;
import java.util.Random;

public class PlayerList extends AppCompatActivity {
    ArrayList<SettingItem> fullsongpath =new ArrayList<SettingItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        //list of the players
        ArrayList<String> players =new ArrayList<String>();
        players.add("McDonlad");
        players.add("KFC");
        players.add("Burger king");
        players.add("Turkey Res");
        players.add("India Food");
        players.add("Albasash");
        players.add("Dan Donate");
        players.add("Hot Bizza");
        players.add("Hot Food");
        players.add("Chicken Food");

        Random r = new Random();
        int PlayerNumber = r.nextInt(players.size() - 1) + 2;
        for (int i=0;i<PlayerNumber;i++)
        {  Random RIndex = new Random();
            int PlayerIndex = RIndex.nextInt(players.size() - 0) + 0;

            fullsongpath.add(new SettingItem(players.get(PlayerIndex), R.drawable.restaurant));
            players.remove(PlayerIndex);
        }
        ListView ls=( ListView) findViewById(R.id.listView);
        ls.setAdapter(new MyCustomAdapter());
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent PlayerList=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(PlayerList);
                finish();
            }
        });


    }
    // adapter for song list
    private class MyCustomAdapter extends BaseAdapter {


        public MyCustomAdapter() {

        }


        @Override
        public int getCount() {
            return fullsongpath.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getLayoutInflater();

              final   SettingItem s = fullsongpath.get(position);

                View myView = mInflater.inflate(R.layout.settingitem, null);
                TextView textView = (TextView) myView.findViewById(R.id.textView);
                textView.setText(s.Name);
                ImageView img=(ImageView)myView.findViewById(R.id.imgchannel);
                img.setImageResource(s.ImageURL);
                return myView ;
        }
    }
}
