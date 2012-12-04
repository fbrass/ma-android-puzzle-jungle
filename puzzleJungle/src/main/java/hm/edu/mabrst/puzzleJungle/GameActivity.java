package hm.edu.mabrst.puzzleJungle;

import hm.edu.mabrst.puzzleJungle.app.Bubble;
import hm.edu.mabrst.puzzleJungle.app.BubbleColor;
import hm.edu.mabrst.puzzleJungle.app.Handler;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
		this.handler = new Handler();
		handler.newGame();
		tv.setText("Game Created");

		final DrawBubbleView drawView = new DrawBubbleView(this);
		drawView.setH(handler);
		this.setContentView(drawView);
		
		drawView.requestFocus();
		Canvas can=new Canvas();
		drawView.onDraw(can);
		
		
		
		ShootActivity shoot=new ShootActivity(handler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_game, menu);
		return true;
	}
}
