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

	// �巡�׽� ��ǥ ����
	int posX1 = 0, posX2 = 0, posY1 = 0, posY2 = 0;

	// ��ġ�� ����ǥ���� �Ÿ� ����
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

		case MotionEvent.ACTION_DOWN: // ù��° �հ��� ��ġ(�巡�� �뵵)
		{
			posX1 = (int) event.getX();
			posY1 = (int) event.getY();

			Log.d("zoom", "mode=DRAG");
			mode = DRAG;
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (mode == DRAG) { // �巡�� ��
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
			} else if (mode == ZOOM) { // ��ġ ��
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
		case MotionEvent.ACTION_UP: // ù��° �հ����� ������ ���
		case MotionEvent.ACTION_POINTER_UP: // �ι�° �հ����� ������ ���
			mode = NONE;
			break;
			
		case MotionEvent.ACTION_POINTER_DOWN:
			// �ι�° �հ��� ��ġ(�հ��� 2���� �ν��Ͽ��� ������ ��ġ ������ �Ǻ�)
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
