package htracer;

public class Point2 {

	public float x;
	public float y;
	
	public Point2(float a) {
		x = y = a;
	}
	
	public Point2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Point2(Point2 b) {
		set(b);
	}
	
	public void set(Point2 v) {
		x = v.x;
		y = v.y;
	}
	
	public Point2 neg() {
		return new Point2(-x, -y);
	}

	public Point2 mul(float a) {
		return new Point2(x*a, y*a);
	}
	
	public float lenSq(Point2 b) {
		return x*x + y*y;
	}
	
	public float len(Point2 b) {
		return (float)Math.sqrt(lenSq(b));
	}

}
