package hm.edu.mabrst.puzzleJungle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HelloAndroidActivity extends Activity {

    private static String TAG = "puzzleJungle";

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        findViewById(R.id.start).setOnClickListener(new  OnClickListener(){
        	public void onClick(View arg0){
        		Intent in = new Intent(HelloAndroidActivity.this,GameActivity.class);
        		startActivity(in);
        		
        	}
        });
        findViewById(R.id.highscore).setOnClickListener(new OnClickListener(){
        	public void onClick(View arg0){
        		Intent in = new Intent(HelloAndroidActivity.this,HighscoreActivity.class);
        		startActivity(in);
        	}
        });
        
        
    }
    public void startGame(View view){
    	
    }
    

}

