package com.example.android.pig;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import android.os.Handler;
import android.widget.Toast;

public class newActivity extends AppCompatActivity {

    public static String START_MESSAGE = "Your Score: %s Computer Score: %s";
    public static String YOLO = "Current Score: %s";
    public static int yourScore = 0;
    public static int computerScore = 0;
    public static int currentScore = 0;
    public static boolean PLAYER = true;
    private Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AssetManager assetManager = getAssets();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean defaultAction(View view) {
        TextView gameStatus = (TextView) findViewById(R.id.gameStatusView);
        TextView currentValue = (TextView) findViewById(R.id.currentValue);
        currentValue.setText(Html.fromHtml(String.format(YOLO, currentScore)));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ImageView imageDice = (ImageView) findViewById(R.id.imageDice);

        Button pushRoll = (Button) findViewById(R.id.PUSHMEGODDAMNIT);
        pushRoll.setVisibility(View.VISIBLE);
        Button pushHold = (Button) findViewById(R.id.PUSHITYEE);
        pushHold.setVisibility(View.VISIBLE);
        Button pushReset = (Button) findViewById(R.id.PUSHRESETBITCH);
        pushReset.setVisibility(View.VISIBLE);


        imageDice.setEnabled(true);
        imageDice.setImageResource(R.drawable.dice2);
        imageDice.setVisibility(View.VISIBLE);
        gameStatus.setText(Html.fromHtml(String.format(START_MESSAGE, yourScore, computerScore)));
            fab.hide();

        return true;
    }

    public void RollIt(View view) {
        TextView currentValue = (TextView) findViewById(R.id.currentValue);
        TextView gameStatus = (TextView) findViewById(R.id.gameStatusView);
        ImageView imageDice = (ImageView) findViewById(R.id.imageDice);
        int choice = random.nextInt(5)+1;

         switch(choice) {
             case 1:
                 imageDice.setImageResource(R.drawable.dice1);
                 break;
             case 2:
                 imageDice.setImageResource(R.drawable.dice2);
                 break;
             case 3:
                 imageDice.setImageResource(R.drawable.dice3);
                 break;
             case 4:
                 imageDice.setImageResource(R.drawable.dice4);
                 break;
             case 5:
                 imageDice.setImageResource(R.drawable.dice5);
                 break;
             case 6:
                 imageDice.setImageResource(R.drawable.dice6);
                 break;
         }

             if (choice != 1) {
                 Log.i("IF2", "FIEHFUIEHEUISFHSKEUFHUIEHFKUESHFUIESHFUIEHS");
                 currentScore += choice;
                 currentValue.setText(Html.fromHtml(String.format(YOLO, currentScore)));
             } else {
                 Log.i("ELSE2", "FIEHFUIEHEUISFHSKEUFHUIEHFKUESHFUIESHFUIEHS");
                 currentScore = 0;
                 HoldIt(view);
                 if (!PLAYER) {
                     computerAI(view);
                 }
             }
         }


    public void computerAI(View view){
        Context context = getApplicationContext();
        CharSequence text = "Computer's Turn";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        while(currentScore <= 20 && !PLAYER) {
            RollIt (view);
        }
        if(!PLAYER) HoldIt(view);
    }

    public void HoldIt(View view){
        Log.i("HOLDIT", "FIEHFUIEHEUISFHSKEUFHUIEHFKUESHFUIESHFUIEHS");
        TextView currentValue = (TextView) findViewById(R.id.currentValue);
        TextView gameStatus = (TextView) findViewById(R.id.gameStatusView);
        if(PLAYER) yourScore += currentScore;
        else computerScore += currentScore;
        PLAYER = !PLAYER;
        currentScore = 0;
        currentValue.setText(Html.fromHtml(String.format(YOLO, currentScore)));
        gameStatus.setText(Html.fromHtml(String.format(START_MESSAGE, yourScore, computerScore)));
        if(!PLAYER) computerAI(view);
    }

    public void FuckIt(View view){
        TextView currentValue = (TextView) findViewById(R.id.currentValue);

        ImageView imageDice = (ImageView) findViewById(R.id.imageDice);
        TextView gameStatus = (TextView) findViewById(R.id.gameStatusView);
        imageDice.setImageResource(R.drawable.dice1);
        yourScore = 0;
        PLAYER = true;
        computerScore = 0;
        currentScore = 0;
        gameStatus.setText(Html.fromHtml(String.format(START_MESSAGE, yourScore, computerScore)));
        currentValue.setText(Html.fromHtml(String.format(YOLO, currentScore)));
    }
}




