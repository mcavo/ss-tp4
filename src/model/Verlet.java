package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx.Snapshot;

public class Verlet {

	private List<VerletParticle> particles;
	private double dt;
	
	
	public Verlet(List<VerletParticle> particles, double dt) {
		this.particles = particles;
		this.dt=dt;
		estimateOldPosition();
	}
	
	private void estimateOldPosition() {
		Map<Integer, Point> forces = new HashMap<Integer, Point>();
		for (int i = 0; i < particles.size(); i++) {
			VerletParticle p = particles.get(i);
			forces.put(i, Point.sum(forces.getOrDefault(i, new Point(0,0)), p.getOwnForce()));
			for (int j = i + 1; j < particles.size(); j++) {
				Point force = p.getForce(particles.get(j));
				Point invForce = force.clone();
				invForce.applyFunction(x->(-1)*x);
				forces.put(i, Point.sum(forces.getOrDefault(i, new Point(0,0)), force));
				forces.put(j, Point.sum(forces.getOrDefault(j, new Point(0,0)), invForce));
			}
			p.updateOldPosition(forces.get(i), dt);
		}
	}

	public void run(double dt) {
		Map<Integer, Point> forces = new HashMap<Integer, Point>();
		for (int i = 0; i < particles.size(); i++) {
			VerletParticle p = particles.get(i);
			forces.put(i, Point.sum(forces.getOrDefault(i, new Point(0,0)), p.getOwnForce()));
			for (int j = i + 1; j < particles.size(); j++) {
				Point force = p.getForce(particles.get(j));
				Point invForce = force.clone();
				invForce.applyFunction(x->(-1)*x);
				forces.put(i, Point.sum(forces.getOrDefault(i, new Point(0,0)), force));
				forces.put(j, Point.sum(forces.getOrDefault(j, new Point(0,0)), invForce));
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
		double vz = (p.position.z - oldPosition.z)/(2*dt);
		p.updateVelocity(vx, vy, vz);
	}

	public void addSpaceShip() {
		
		Particle sun = particles.get(0);
		Particle earth = particles.get(1);
		Point linealDireccion = Point.sub(earth.position, sun.position);
		linealDireccion.normalize();
		linealDireccion.applyFunction(x->(1500000+earth.getRadius())*x);
		Point position = Point.sum(earth.position, linealDireccion);
		linealDireccion = earth.velocity.clone();
		linealDireccion.normalize();
		linealDireccion.applyFunction(x->(15120)*x);
		Point velocity = Point.sum(earth.velocity, linealDireccion);
		VerletParticle spaceship = new Planet(4, position.x, position.y, position.z, velocity.x, velocity.y, velocity.z, 2E5, 0);
		Point force = new Point(0,0);
		for (int i = 0; i < particles.size(); i++) {
			force=Point.sum(force, spaceship.getForce(particles.get(i)));
		}
		spaceship.updateOldPosition(force, dt);
		particles.add(spaceship);
	}
}
