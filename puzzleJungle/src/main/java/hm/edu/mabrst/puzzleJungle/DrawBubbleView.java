package hm.edu.mabrst.puzzleJungle;

import hm.edu.mabrst.puzzleJungle.app.Bubble;
import hm.edu.mabrst.puzzleJungle.app.BubbleColor;
import hm.edu.mabrst.puzzleJungle.app.Handler;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawBubbleView extends View {

	private float width;
	private float height;
	private float diameter;
	private Paint paint;
	private Handler h;

	public DrawBubbleView(Context context) {
		super(context);
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		setupDisplay();
		setYellow();
		int counter = 1;
		float yvalue = height / 11;
		int countery = 1;
		try {
			for (int j = 0; j < 20; j += 4) {
				for (int i = Math.round(diameter); i < Math.round(width); i = i
						+ 2 * Math.round(diameter)) {
					setBubbleColor(h.getBubble(h.getCoordinates()[j][counter]));
					canvas.drawCircle(i, yvalue * countery - 13, diameter,
							paint);
					counter = counter + 2;

					if (counter >= 15)
						counter = 1;

				}
				countery += 2;
			}
		} catch (Exception e) {
		}

		counter = 2;
		countery = 2;
		double counterx = 0.5;

		try {
			for (int j = 2; j < 10; j += 4) {
				for (int k = 0; k < 13; k += 2) {
					setBubbleColor(h.getBubble(h.getCoordinates()[j][counter]));
					canvas.drawCircle(diameter * 2 * Math.round(counterx),
							yvalue * countery - 13, diameter, paint);
					counter += 2;
					counterx++;

					if (counter >= 15) {
						counterx = 0.5;
						counter = 1;
					}

				}
				countery += 2;
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void setupDisplay() {
		width = this.getWidth();
		height = this.getHeight();
		diameter = (width) / 16;
	}

	private void setBubbleColor(Bubble b) {
		if (b.color == BubbleColor.YELLOW)
			setYellow();
		if (b.color == BubbleColor.BLUE)
			setBlue();
		if (b.color == BubbleColor.GREEN)
			setGreen();
		if (b.color == BubbleColor.RED)
			setRed();
	}

	private void setYellow() {
		paint.setColor(Color.YELLOW);
	}

	private void setGreen() {
		paint.setColor(Color.GREEN);
	}

	private void setBlue() {
		paint.setColor(Color.BLUE);
	}

	private void setRed() {
		paint.setColor(Color.RED);
	}

	public void setH(Handler h) {
		this.h = h;
	}

}
