package com.example.lifetree;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class TreeView extends View {

	MainActivity mainActivity;
	Node example;

	
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// 드래그시 좌표 저장
	int posX1 = 0, posX2 = 0, posY1 = 0, posY2 = 0;

	// 핀치시 두좌표간의 거리 저장
	float oldDist = 1f;
	float newDist = 1f;

	public TreeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.WHITE);
		mainActivity = (MainActivity) context;
		example = new Node("sample", null);

	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		example.Render(canvas);
		super.onDraw(canvas);
	}
/*
	public boolean onTouchEvent(MotionEvent event) {
		

		return super.onTouchEvent(event);
	}

	
	*/

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int act = event.getAction() & MotionEvent.ACTION_MASK;    
		
		String strMsg = "";

		switch (act) {

		case MotionEvent.ACTION_DOWN: // 첫번째 손가락 터치(드래그 용도)
		{
			posX1 = (int) event.getX();
			posY1 = (int) event.getY();

			Log.d("zoom", "mode=DRAG");
			mode = DRAG;
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (mode == DRAG) { // 드래그 중
				posX2 = (int) event.getX();
				posY2 = (int) event.getY();

				if (Math.abs(posX2 - posX1) > 10
						|| Math.abs(posY2 - posY1) > 10) {
					int posX3 = posX2 - posX1;
					int posY3 = posY2 - posY1;
					posX1 = posX2;
					posY1 = posY2;
					strMsg = "drag";
					example.Move(posX3, posY3);

				}
			} else if (mode == ZOOM) { // 핀치 중
				newDist = spacing(event);

				Log.d("zoom", "newDist=" + newDist);
				Log.d("zoom", "oldDist=" + oldDist);

				if (newDist - oldDist > 20) { // zoom in
					oldDist = newDist;

					strMsg = "zoom in";
				} else if (oldDist - newDist > 20) { // zoom out
					oldDist = newDist;

					strMsg = "zoom out";
				}
			}
			break;
		}
		case MotionEvent.ACTION_UP: // 첫번째 손가락을 떼었을 경우
		case MotionEvent.ACTION_POINTER_UP: // 두번째 손가락을 떼었을 경우
			mode = NONE;
			break;
			
		case MotionEvent.ACTION_POINTER_DOWN:
			// 두번째 손가락 터치(손가락 2개를 인식하였기 때문에 핀치 줌으로 판별)
			mode = ZOOM;

			newDist = spacing(event);
			oldDist = spacing(event);

			Log.d("zoom", "newDist=" + newDist);
			Log.d("zoom", "oldDist=" + oldDist);
			Log.d("zoom", "mode=ZOOM");
			break;
			
		case MotionEvent.ACTION_CANCEL:
		default:
			break;
		}

		this.invalidate();
			    
		return true;
	}
	
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}
	

}
