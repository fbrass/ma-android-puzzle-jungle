package hm.edu.mabrst.puzzleJungle;

import hm.edu.mabrst.puzzleJungle.app.Bubble;
import hm.edu.mabrst.puzzleJungle.app.BubbleColor;
import hm.edu.mabrst.puzzleJungle.app.Handler;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity {
	private Handler handler;
	ImageView image11;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView tv = (TextView) findViewById(R.id.loadView);
        tv.setText("starting Game");
        this.handler=new Handler();
        handler.newGame();
        tv.setText("Game Created");
        image11= (ImageView) findViewById(R.id.imageView11);
        /*
        Bubble b = handler.getMap().get(handler.getCoordinates()[1][1]);
        /*
        if(b.color==BubbleColor.BLUE) tv.setText("blau");
        if(b.color==BubbleColor.YELLOW) tv.setText("gelb"); 
        if(b.color==BubbleColor.RED) tv.setText("rot");
        if(b.color==BubbleColor.GREEN) tv.setText("grün");
      
        /*
        if(b.color==BubbleColor.BLUE) image11.setImageResource(R.drawable.blau);
        if(b.color==BubbleColor.YELLOW) image11.setImageResource(R.drawable.gelb);
        if(b.color==BubbleColor.RED) image11.setImageResource(R.drawable.rot);
        if(b.color==BubbleColor.GREEN) tv.setTag("grün");
        */
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game, menu);
        return true;
    }
}
