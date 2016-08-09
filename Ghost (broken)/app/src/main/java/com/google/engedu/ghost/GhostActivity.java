package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends ActionBarActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    public static String wordGame = "";

    private boolean userTurn = false;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(inputStream);
        }
     catch (IOException e) {
        Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
        toast.show();
    }

        onStart(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode >= 29 && keyCode <= 64){
            char letter = (char) (keyCode + 68);
            wordGame += letter;
            Log.i("test", "" + keyCode);

            if(dictionary.isWord(wordGame)){
                TextView label = (TextView) findViewById(R.id.gameStatus);
                    label.setText("This is a word");
            }
            TextView text = (TextView) findViewById(R.id.ghostText);
            text.setText(wordGame);

            userTurn = false;
            computerTurn();
            TextView label = (TextView) findViewById(R.id.gameStatus);
            label.setText(USER_TURN);
        }

        return super.onKeyUp(keyCode, event);
    }


    public boolean onChallenge(View view) {


        if (wordGame.length() >= 4 && dictionary.isWord(wordGame)) {
            //Victory
            TextView label = (TextView) findViewById(R.id.gameStatus);
            label.setText("Player Wins!");
        }
        String possibleWord = dictionary.getAnyWordStartingWith(wordGame);
         if (!(possibleWord == null)) {
            TextView text = (TextView) findViewById(R.id.ghostText);
            text.setText(possibleWord);

            TextView label = (TextView) findViewById(R.id.gameStatus);
            label.setText("Computer Wins!");
        }
        else{
            TextView label = (TextView) findViewById(R.id.gameStatus);
            label.setText("Player Wins!");
        }

    return true;
    }





    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);;

            if (wordGame.length() >= 4 && dictionary.isWord(wordGame)) {
                //Victory
                label.setText("Computer Wins!");
                return;
            }
            String possibleWord = dictionary.getAnyWordStartingWith(wordGame);
            if (possibleWord.isEmpty()) {
                label.setText("Challenged! Computer Wins!");
                return;
            }
            else {
                wordGame = possibleWord.substring(0, wordGame.length()+1);

                TextView text = (TextView) findViewById(R.id.ghostText);
                text.setText(wordGame);
            }

        //wordGame = wordGame + (char)(random.nextInt(26) + 97);
        //TextView text = (TextView) findViewById(R.id.ghostText);
        //text.setText(wordGame);

        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        //userTurn = random.nextBoolean();
        wordGame = "";
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
}
