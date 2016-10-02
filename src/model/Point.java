package model;

import java.util.function.Function;

public class Point {

	static final double EPSILON = 1e-7;
	public double x, y, z;
	
	public Point(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Point(double x, double y){
		this.x=x;
		this.y=y;
		z=0;
	}
	
	public void add(Point p){
		add(p.x, p.y, p.z);
	}
	
	public void add(double x, double y, double z) {
		this.x+=x;
		this.y+=y;
		this.z+=z;
	}

	public void add(double x, double y) {
		this.x+=x;
		this.y+=y;
	}
	
	public void applyFunction(Function<Double, Double> f){
		x = f.apply(x);
		y = f.apply(y);
		z = f.apply(z);
	}
	
	public double abs(){
		return Point.abs(this);
	}
	
	public String toString(){
		return "("+x+", "+y+", "+z+")";
	}
	
	public Point clone() {
		return new Point(x, y, z);
	}
	
	static public Point sum(Point p1, Point p2){
		return new Point(p1.x+p2.x, p1.y+p2.y, p1.z+p2.z);
	}
	
	static public Point sub(Point p1, Point p2){
		return new Point(p1.x-p2.x, p1.y-p2.y, p1.z-p2.z);
	}
	
	static public double abs(Point p){
		return Math.sqrt(abs2(p));
	}
	
	static public double abs2(Point p){
		return p.x*p.x+p.y*p.y+p.z*p.z;
	}

	static public double dist(Point p1, Point p2){
		return Math.sqrt(dist2(p1,p2));
	}
	
	static public double dist2(Point p1, Point p2){
		return abs2(sub(p1, p2));
	}
	
	static public double scalarProd(Point p1, Point p2){
		return p1.x*p2.x+p1.y*p2.y;
	}
	
	static public boolean arePerpendiculars(Point p1, Point p2){
		return Math.abs(scalarProd(p1, p2))<EPSILON;
	}
	
	static public double vectorProd(Point p1, Point p2){
		return p1.x*p2.y-p2.x*p1.y;
	}
	
	static public boolean areParallels(Point p1, Point p2){
		return paralelogramArea(p1, p2)<EPSILON;
	}
	
	static public double paralelogramArea(Point p1, Point p2, Point p3){
		return paralelogramArea(sub(p1,p2), sub(p3,p2));
	}
	
	static public double paralelogramArea(Point p1, Point p2){
		return Math.abs(vectorProd(p1, p2));
	}

	public void normalize() {
		double norm = abs(this);
		applyFunction(x->x/norm);
	}
}