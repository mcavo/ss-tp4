package model;

public class Planet extends VerletParticle{

	private static final double G = 6.693e-11;
	
	public Planet(int id, double x, double y, double z, double vx, double vy, double vz, double m, double r){
		super(id, x, y, z, vx, vy, vz, m, r);
	}

	@Override
	public Point getForce(Particle p) {
		double dist = Point.dist(position, p.position);
		double mod = G*getMass()*p.getMass() / (dist*dist);
		Point sub = Point.sub(p.position, position);
		sub.normalize();
		sub.applyFunction(x->x*mod);
		return sub;
	}

	@Override
	public Point getOwnForce() {
		return new Point(0,0);
	}
	
}
