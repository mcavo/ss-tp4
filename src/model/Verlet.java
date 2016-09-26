package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Verlet {

	private List<MASParticle> particles;
	
	public Verlet(List<MASParticle> particles) {
		this.particles = particles;
	}
	
	public void run(double dt) {
		List<Point> forces = new ArrayList<>(Collections.nCopies(particles.size(), new Point(0, 0)));
		for (int i = 0; i < particles.size(); i++) {
			MASParticle p = particles.get(i);
			forces.set(i, Point.sum(forces.get(i), p.getForce(p))); // In case particle has its own forces
			for (int j = i + 1; j < particles.size(); j++) {
				Point force = particles.get(i).getForce(p);
				forces.set(i, Point.sum(forces.get(i), force));
				forces.set(j, Point.sum(forces.get(j), force));
			}
			Point oldPosition = p.getOldPosition();
			updatePosition(p, forces.get(i), dt);
			updateVelocity(p, oldPosition, dt);
		}
	}

	private void updatePosition(MASParticle p, Point force, double dt) {
		double rx = 2*p.getX() - p.getOldPosition().x + force.x*Math.pow(dt, 2)/p.getMass();
		double ry = 2*p.getY() - p.getOldPosition().y + force.y*Math.pow(dt, 2)/p.getMass();
		p.updatePosition(rx, ry);
	}
	
	private void updateVelocity(MASParticle p, Point oldPosition, double dt) {
		double vx = (p.getX() - oldPosition.x)/(2*dt);
		double vy = (p.getY() - oldPosition.y)/(2*dt);
		p.updateVelocity(vx, vy);
	}
}
