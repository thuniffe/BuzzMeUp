package com.example.buzzmeup;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ArrayList<Boolean> buzzArray;
    private int colorWhite = Color.rgb(255,255,255);
    private int colorBlue = Color.rgb(0,0,255);
    private int colorBlack = Color.rgb(0,0,0);
    private ImageView[] tiles;
    private Vibrator vibrator;
    private DatabaseHandler db;
    private int previousArraySize = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        db =  new DatabaseHandler(this);


        updateSpinner();


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        tiles = new  ImageView[]{findViewById(R.id.tile1),findViewById(R.id.tile2),findViewById(R.id.tile3),findViewById(R.id.tile4),findViewById(R.id.tile5),findViewById(R.id.tile6),findViewById(R.id.tile7),findViewById(R.id.tile8)};

        if(savedInstanceState != null && !savedInstanceState.isEmpty()) {
            buzzArray = new ArrayList<>();
            boolean[] temp = savedInstanceState.getBooleanArray("buzzArray");
            for(int i = 0; i < temp.length;i++){
                buzzArray.add(new Boolean(temp[i]));
            }


            for (int i = 0; i < buzzArray.size(); i++) {
                if (buzzArray.get(i)) {
                    tiles[i].setBackgroundColor(colorWhite);
                } else {
                    tiles[i].setBackgroundColor(colorBlue);
                }
            }
        }else {
             buzzArray = new ArrayList(Arrays.asList(false,false,false,false,false,false)) ;
        }
        updateTiles();
        previousArraySize=buzzArray.size();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        boolean[] array = new boolean[buzzArray.size()];
        for (int i = 0; i < buzzArray.size(); i++) {
            array[i] = (boolean)buzzArray.get(i);
        }
        outState.putBooleanArray("buzzArray", array);

    }

    public void changeColor(View view) {
       int tileNumber = view.getId();
        previousArraySize=buzzArray.size();

       switch (tileNumber) {
           case R.id.tile1 :
               if(buzzArray.get(0)){
                   buzzArray.set(0, false);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(0, true);
                   view.setBackgroundColor(colorWhite);
               }
                break;
           case R.id.tile2 :
               if(buzzArray.get(1)){
                   buzzArray.set(1, false);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(1, true);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile3 :
               if(buzzArray.get(2)){
                   buzzArray.set(2, false);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(2, true);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile4 :
               if(buzzArray.get(3)){
                   buzzArray.set(3, false);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(3, true);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile5 :
               if(buzzArray.get(4)){
                   buzzArray.set(4, false);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(4, true);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile6 :
               if(buzzArray.get(5)){
                   buzzArray.set(5, false);
                   view.setBackgroundColor(colorBlue);
               }else{
                   buzzArray.set(5,  true);
                   view.setBackgroundColor(colorWhite);
               }
               break;
           case R.id.tile7 :
               if(buzzArray.size()==6){
                   buzzArray.add(false);
                   tiles[6].setImageResource(android.R.color.transparent);
                   tiles[6].setBackgroundColor(colorBlue);
                   tiles[7].setVisibility(View.VISIBLE);
                   tiles[7].setImageResource(android.R.drawable.ic_input_add);
               }else {
                   if (buzzArray.get(6)) {
                       buzzArray.set(6, false);
                       view.setBackgroundColor(colorBlue);
                   } else {
                       buzzArray.set(6, true);
                       view.setBackgroundColor(colorWhite);
                   }
               }
               break;
           case R.id.tile8 :
               if(buzzArray.size()<7){
                   break;
               }
               if(buzzArray.size()==7){
                   buzzArray.add(false);
                   tiles[7].setImageResource(android.R.color.transparent);
                   tiles[7].setBackgroundColor(colorBlue);
               }else {
                   if (buzzArray.get(7)) {
                       buzzArray.set(7, false);
                       view.setBackgroundColor(colorBlue);
                   } else {
                       buzzArray.set(7, true);
                       view.setBackgroundColor(colorWhite);
                   }
               }
               break;
           default:
               break;
       }
       updateTiles();
        previousArraySize=buzzArray.size();
    }

    public void vibrateMe(View view){
        Toast.makeText(this.getApplicationContext(),"size "+String.valueOf(buzzArray.size()), Toast.LENGTH_LONG);
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
                if(buzzArray.get(i)){
                    vibratePattern[i*2] = 0;
                    vibratePattern[i*2+1] = 500;
                }else{
                    vibratePattern[i*2] = 500;
                    vibratePattern[i*2+1] = 0;
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
        String[] items = spinnerLabel.split(" ");
        previousArraySize=buzzArray.size();
        buzzArray.clear();

        for (int i = 0; i < items.length; i++) {

            if(items[i].equals("true")){
                buzzArray.add(true);
            }else if(items[i].equals("false")){
                buzzArray.add(false);
            }
        }
        updateTiles();
        previousArraySize=buzzArray.size();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void savePattern(View view){

        String pattern = "";
        for(int i = 0; i<buzzArray.size();i++){
            pattern=pattern+" "+buzzArray.get(i);
        }
        db.addPattern(pattern);

        updateSpinner();
    }

    public void updateSpinner(){
        String[] arraySpinner = new String[]{""};
        arraySpinner = db.getAllPatterns().toArray(arraySpinner);

        Spinner spinner = findViewById(R.id.spinner);
        if(spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        if(arraySpinner.length>0 && arraySpinner!= null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    public void updateTiles(){

        if(previousArraySize==6){
            if(buzzArray.size()==6){
                tiles[6].setBackgroundColor(colorBlack);
                tiles[6].setImageResource(android.R.drawable.ic_input_add);
                tiles[7].setImageResource(android.R.color.transparent);
                tiles[7].setVisibility(View.INVISIBLE);
            }else if(buzzArray.size()==7){
                tiles[6].setImageResource(android.R.color.transparent);
                tiles[7].setVisibility(View.VISIBLE);
                tiles[7].setBackgroundColor(colorBlack);
                tiles[7].setImageResource(android.R.drawable.ic_input_add);
            }else if(buzzArray.size()==8){
                tiles[6].setImageResource(android.R.color.transparent);
                tiles[7].setVisibility(View.VISIBLE);
                tiles[7].setImageResource(android.R.color.transparent);
            }
        }else if(previousArraySize==7){
            if(buzzArray.size()==6){
                tiles[6].setBackgroundColor(colorBlack);
                tiles[6].setImageResource(android.R.drawable.ic_input_add);
                tiles[7].setImageResource(android.R.color.transparent);
                tiles[7].setVisibility(View.INVISIBLE);
            }else if(buzzArray.size()==7){
                tiles[7].setBackgroundColor(colorBlack);
                tiles[7].setImageResource(android.R.drawable.ic_input_add);
            }else if(buzzArray.size()==8){
                tiles[7].setImageResource(android.R.color.transparent);
            }
        }else if(previousArraySize==8){
            if(buzzArray.size()==6){
                tiles[6].setBackgroundColor(colorBlack);
                tiles[6].setImageResource(android.R.drawable.ic_input_add);
                tiles[7].setVisibility(View.INVISIBLE);
            }else if(buzzArray.size()==7){
                tiles[7].setBackgroundColor(colorBlack);
                tiles[7].setImageResource(android.R.drawable.ic_input_add);
            }else if(buzzArray.size()==8){
                tiles[7].setImageResource(android.R.color.transparent);
            }
        }

        for (int i = 0; i < buzzArray.size(); i++) {
            if(buzzArray.get(i)){
                tiles[i].setBackgroundColor(colorWhite);
            }else{
                tiles[i].setBackgroundColor(colorBlue);
            }
        }
    }
    public void clearButton(View view){
        previousArraySize=buzzArray.size();

        buzzArray.clear();
        buzzArray = new ArrayList(Arrays.asList(false,false,false,false,false,false));

       updateTiles();
       previousArraySize=buzzArray.size();
    }

}
