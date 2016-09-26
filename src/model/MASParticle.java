package model;

//TODO: Update name. It's wrong
public class MASParticle extends Particle {
	private double k;
	private double gamma;
	private Point oldPosition;

	public MASParticle(int id, double k, double gamma, double m) {
		super(id, 1, 0, -gamma / (2*m), 0, m, 1E-3); // TODO: should be revise
		this.gamma = gamma;
		this.k = k;
		this.oldPosition = position;
	}
	     
	public Point getForce(Particle p) {
		// The position as well as the velocity are from t - dt
		System.out.println("elastico: " + -k*oldPosition.x + "roce: " + gamma*getXVelocity());
		return new Point(-k*oldPosition.x - gamma*getXVelocity(), 0);
	}
	
	public Point getOldPosition() {
		return this.oldPosition;
	}
	
	public void updatePosition(double x, double y) {
		this.oldPosition = position;
		position = new Point(x, y);
//		System.out.println(position);
	}

}
