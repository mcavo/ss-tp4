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
		for (int i = 0; i < particles.size() - 1; i++) {
			MASParticle p = particles.get(i);
			for (int j = i + 1; j < particles.size() - 2; j++) {
				Point force = particles.get(i).getForce(dt, p);
				forces.add(i, Point.sum(forces.get(i), force));
				forces.add(j, Point.sum(forces.get(i), force));
			}
			updatePosition(p, forces.get(i), dt);
		}
	}

	private void updatePosition(MASParticle p, Point force, double dt) {
		double rx = 2*p.getX() - 2*p.getOldPosition().x + force.x*Math.pow(dt, 2)/p.getMass();
		double ry = 2*p.getX() - 2*p.getOldPosition().y + force.y*Math.pow(dt, 2)/p.getMass();
		p.updatePosition(rx, ry);
	}
}
