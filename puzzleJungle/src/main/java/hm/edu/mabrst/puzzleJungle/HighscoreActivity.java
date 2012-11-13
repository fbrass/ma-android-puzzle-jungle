package hm.edu.mabrst.puzzleJungle;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HighscoreActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_highscore, menu);
        return true;
    }
}
