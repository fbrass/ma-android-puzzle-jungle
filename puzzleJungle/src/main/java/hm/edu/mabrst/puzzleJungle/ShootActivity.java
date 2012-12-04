package hm.edu.mabrst.puzzleJungle;

import hm.edu.mabrst.puzzleJungle.app.IHandler;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

public class ShootActivity extends Activity {

	private IHandler handler;
	
	public ShootActivity(IHandler h){
		this.handler=h;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ShootView shootView = new ShootView(this);
		setContentView(shootView);
		shootView.requestFocus();
		shootView.setOnClickListener(new View.OnClickListener(){
			
			
		
			public boolean onTouch(View v, MotionEvent event){
				return true;
			}

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			};
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_shoot, menu);
		return true;
	}

}
