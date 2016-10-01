package model;

public abstract class VerletParticle extends Particle {

	private Point oldPosition;
	private Point currentForces;

	public VerletParticle(int id, double x, double y, double z, double vx,
			double vy, double vz, double m, double r) {
		super(id, x, y, z, vx, vy, vz, m, r); // TODO: should be revise
		this.oldPosition = position;
		this.currentForces = getForce(this);
	}

	public VerletParticle(int id, double x, double y, double vx, double vy,
			double m, double r) {
		super(id, x, y, vx, vy, m, r); // TODO: should be revise
		this.oldPosition = position;
		this.currentForces = getForce(this);
	}

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

	public void updateForces(Point currentFroces) {
		this.currentForces = currentFroces;
	}

	public Point getCurrentForces() {
		return this.currentForces;
	}
}
