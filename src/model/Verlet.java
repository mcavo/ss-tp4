package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Verlet {

	private List<? extends VerletParticle> particles;
	
	public Verlet(List<? extends VerletParticle> particles) {
		this.particles = particles;
	}
	
	public void run(double dt) {
		Map<Integer, Point> forces = new HashMap<Integer, Point>();
		for (int i = 0; i < particles.size(); i++) {
			VerletParticle p = particles.get(i);
			for (int j = i + 1; j < particles.size(); j++) {
				VerletParticle pj = particles.get(j);
				forces.put(i, Point.sum(forces.getOrDefault(i, new Point(0,0)), p.getForce(pj)));
				forces.put(j, Point.sum(forces.getOrDefault(j, new Point(0,0)), pj.getForce(p)));
			}
			Point oldPosition = p.getOldPosition();
			updatePosition(p, forces.get(i), dt);
			updateVelocity(p, oldPosition, dt);
		}
	}

	private void updatePosition(VerletParticle p, Point force, double dt) {
		double rx = 2*p.position.x - p.getOldPosition().x + force.x*Math.pow(dt, 2)/p.getMass();
		double ry = 2*p.position.y - p.getOldPosition().y + force.y*Math.pow(dt, 2)/p.getMass();
		double rz = 2*p.position.z - p.getOldPosition().z + force.z*Math.pow(dt, 2)/p.getMass();
		p.updatePosition(rx, ry, rz);
	}
	
	private void updateVelocity(VerletParticle p, Point oldPosition, double dt) {
		double vx = (p.position.x - oldPosition.x)/(2*dt);
		double vy = (p.position.y - oldPosition.y)/(2*dt);
		double vz = (p.position.z - oldPosition.y)/(2*dt);
		p.updateVelocity(vx, vy, vz);
	}
}
