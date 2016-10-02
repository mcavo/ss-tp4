package model;

public class BeemanParticle extends Particle {
	protected double k;
	protected double gamma;
	private Point oldPosition;
	private Point currentForces;

	public BeemanParticle(int id, double k, double gamma, double m) {
		super(id, 1, 0, -gamma / (2*m), 0, m, 1E-3); // TODO: should be revise
		this.gamma = gamma;
		this.k = k;
		this.oldPosition = position;
		this.currentForces = getForce(this);
	}
	     
	public Point getForce(Particle p) {
		// The position as well as the velocity are from t - dt
		return new Point(-k*position.x - gamma*getXVelocity(), 0);
	}
	
	public Point getOldPosition() {
		return this.oldPosition;
	}
	
	public void updatePosition(double x, double y) {
		this.oldPosition = position;
		position = new Point(x, y);
	}
	
	public void updateForces(Point currentFroces) {
		this.currentForces = currentFroces;
	}

	public Point getCurrentForces() {
		return this.currentForces;
	}
}