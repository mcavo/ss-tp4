package model;

//TODO: Update name. It's wrong
public class MASParticle extends Particle {
	private double k;
	private double gamma;
	private double w;
	private double b;
	private Point oldPosition;

	public MASParticle(int id, double k, double gamma, double m) {
		super(id, 0, 0, 0, m, 0); // TODO: should be revise
		this.gamma = gamma;
		this.k = k;
		b = gamma / (2*m);
		w = Math.sqrt(k/m - Math.pow(b, 2));
	}
	
	public MASParticle(int id, double x, double y, double velAbs, double m, double r, double k, double gamma) {
		super(id, x, y, velAbs, m, r);
	}
	
	public MASParticle(int id, double x, double y, double vx, double vy, double m, double r, double k, double gamma) {
		this(id, k, gamma, m);
	}
	
	public Point getPosition(double t) {
		return new Point(Math.exp(-b*t)*Math.cos(w*t), 0);
	}
	
	public Point getVelocity(double t) {
		return new Point(-b*getPosition(t).x - w*Math.exp(-b*t)*Math.sin(w*t), 0);
	}
	
	public double getFX(double t) {
		if (t < 0) {
			return 0;
		}
		return -k*getPosition(t - 1).x - gamma*getPosition(t).x;
	}
	
	public double getFY(double t) {
		return 0;
	}
	
	//TODO: instead of Particle should be an Interface general for all particles needed in the System
	public Point getForce(double t, Particle p) {
		if (t < 0) {
			return new Point(0,0);
		}
		return new Point(-k*getPosition(t - 1).x - gamma*getPosition(t).x, 0);
	}
	
	public Point getOldPosition() {
		return this.oldPosition;
	}
	
	public void updatePosition(double x, double y) {
		this.oldPosition = position;
		position = new Point(x, y);
	}
}
