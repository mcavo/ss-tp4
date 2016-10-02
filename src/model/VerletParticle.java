package model;

public abstract class VerletParticle extends Particle {

	private Point oldPosition;

	
	public VerletParticle(int id, double x, double y, double z, double vx,
			double vy, double vz, double m, double r) {
		super(id, x, y, z, vx, vy, vz, m, r);
	}

	public VerletParticle(int id, double x, double y, double vx, double vy,
			double m, double r) {
		super(id, x, y, vx, vy, m, r);
	}
	
	public abstract Point getOwnForce();

	public abstract Point getForce(Particle p);

	public Point getOldPosition() {
		return this.oldPosition;
	}

	public void updatePosition(double x, double y) {
		this.oldPosition = position;
		position = new Point(x, y);
	}
	
	public void updatePosition(double x, double y, double z) {
		this.oldPosition = position;
		position = new Point(x, y, z);
	}

	public void updateOldPosition(Point force, double dt) {
		double x = position.x+(-dt)*velocity.x+(dt*dt)*force.x/(2*getMass());
		double y = position.y+(-dt)*velocity.y+(dt*dt)*force.y/(2*getMass());
		double z = position.z+(-dt)*velocity.z+(dt*dt)*force.z/(2*getMass());
		oldPosition = new Point(x, y, z);
	}

	
}
