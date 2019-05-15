package com.example.buzzmeup;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ArrayList<Integer> buzzArray;
    private int colorWhite = Color.rgb(255,255,255);
    private int colorBlue = Color.rgb(0,0,255);
    private ImageView[] tiles;
    private Vibrator vibrator;
    DatabaseHandler db = new DatabaseHandler(this);

    public static final int PATTERN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);



        String[] arraySpinner = new String[]{""};
                arraySpinner = db.getAllPatterns().toArray(arraySpinner);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        if(spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
            if(arraySpinner.length>1) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        tiles = new  ImageView[]{findViewById(R.id.tile1),findViewById(R.id.tile2),findViewById(R.id.tile3),findViewById(R.id.tile4),findViewById(R.id.tile5),findViewById(R.id.tile6),findViewById(R.id.tile7),findViewById(R.id.tile8)};

        if(savedInstanceState != null && !savedInstanceState.isEmpty()) {

            this.buzzArray = savedInstanceState.getIntegerArrayList("buzzArray");
            for (int i = 0; i < buzzArray.size(); i++) {
                if (buzzArray.get(i) == 500) {
                    tiles[i].setBackgroundColor(colorWhite);
                } else {
                    tiles[i].setBackgroundColor(colorBlue);
                }
            }
        }else {
             buzzArray = new ArrayList(Arrays.asList(0,0,0,0,0,0,0,0)) ;
            for (int i = 0; i < 8; i++) {
                    tiles[i].setBackgroundColor(colorBlue);
            }
           
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        long[] array = new long[buzzArray.size()];
        for (int i = 0; i < buzzArray.size(); i++) {
            array[i] = (long)buzzArray.get(i);
        }
        outState.putIntegerArrayList("buzzArray", buzzArray);
    }

    public void changeColor(View view) {
       int tileNumber = view.getId();

       switch (tileNumber) {
           case R.id.tile1 :
               if(buzzArray.get(0)== 500){
                   buzzArray.set(0, 0);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(0, 500);
                   view.setBackgroundColor(colorWhite);
               }
                break;
           case R.id.tile2 :
               if(buzzArray.get(1) == 500){
                   buzzArray.set(1, 0);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(1, 500);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile3 :
               if(buzzArray.get(2) == 500){
                   buzzArray.set(2, 0);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(2, 500);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile4 :
               if(buzzArray.get(3) == 500){
                   buzzArray.set(3, 0);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(3, 500);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile5 :
               if(buzzArray.get(4) == 500){
                   buzzArray.set(4, 0);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(4, 500);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile6 :
               if(buzzArray.get(5) == 500){
                   buzzArray.set(5, 0);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(5,  500);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile7 :
               if(buzzArray.get(6) == 500){
                   buzzArray.set(6, 0);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(6, 500);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile8 :
               if(buzzArray.get(7) == 500){
                   buzzArray.set(7, 0);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(7, 500);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           default:
               break;
       }
    }


    public void vibrateMe(View view){

        if (vibrator != null && vibrator.hasVibrator()) {

            long[] vibratePattern;
            switch (buzzArray.size()) {
                case 6:
                    vibratePattern = new long[12];
                    break;
                case 7:
                    vibratePattern = new long[14];
                    break;
                case 8:
                    vibratePattern = new long[16];
                    break;
                default:
                    vibratePattern = new long[12];
                    break;
            }
            for (int i = 0; i < buzzArray.size(); i++) {
                if(buzzArray.get(i) == 0){
                    vibratePattern[i*2] = 500;
                    vibratePattern[i*2+1] = 0;
                }else{
                    vibratePattern[i*2] = 0;
                    vibratePattern[i*2+1] = 500;
                }

            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                VibrationEffect effect = VibrationEffect.createWaveform(vibratePattern, -1);
                vibrator.vibrate(effect);
            } else {

                vibrator.vibrate(vibratePattern, -1);
                vibrator.vibrate(vibratePattern, -1);
            }
        }else{
            Toast.makeText(this, getResources().getString(R.string.vibration_not_supported), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerLabel = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, spinnerLabel,Toast.LENGTH_LONG);
        String[] items = spinnerLabel.split(" ");

        int[] results = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {

            }
        }
        buzzArray.clear();

        for (int i = 0; i < results.length; i++) {
           buzzArray.add(results[i]);
        }
        for (int i = 0; i < buzzArray.size()-1; i++) {
            if (buzzArray.get(i) == 500) {
                tiles[i].setBackgroundColor(colorWhite);
            } else {
                tiles[i].setBackgroundColor(colorBlue);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void savePattern(View view){

        String pattern = "";
        for(int i = 0; i<buzzArray.size();i++){
            pattern=pattern+" "+buzzArray.get(i).toString();
        }
        db.addPattern(pattern);

        String[] arraySpinner = new String[]{""};
        arraySpinner = db.getAllPatterns().toArray(arraySpinner);

        Spinner s = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

    }


}
