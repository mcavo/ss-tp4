package model;


//TODO: Update name. It's wrong
public class MASParticle extends VerletParticle {
	
	protected double k;
	protected double gamma;

	public MASParticle(int id, double k, double gamma, double m) {
		super(id, 1, 0, -gamma / (2*m), 0, m, 1E-3); // TODO: should be revise
		this.gamma = gamma;
		this.k = k;
	}
	     
	public Point getForce(Particle p) {
		return new Point(0,0);
	}

	@Override
	public Point getOwnForce() {
		// The position as well as the velocity are from t - dt
		return new Point(-k*position.x - gamma*getXVelocity(), 0);
	}
}
