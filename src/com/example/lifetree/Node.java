package com.example.lifetree;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Node {	
	int px,py;
	int size;
	
	String title;
	Node parent;
	Node[] childeren;
	int childAmount;
	int achievementRate;
	
	Node(String NodeTitle, Node NodeParent){
		px = 10;
		py = 10;
		size = 50;
		this.title = NodeTitle;
		this.parent = NodeParent;
		childAmount = 0;
		achievementRate = 0;
	}
	
	public void Move(int x, int y){
		px = px + x;
		py = py + y;
	}
	
	public void zoomIn(){
		
	}
	
	public void zoomOut(){
		
	}
	
	public void Render(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		canvas.drawRect(px, py, px+size, py+size, paint);
	}
}
