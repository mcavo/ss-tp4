package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Beeman {

	private List<BeemanParticle> particles;

	public Beeman(List<BeemanParticle> particles) {
		this.particles = particles;
	}

	public void run(double dt) {
		List<Point> forces = new ArrayList<>(Collections.nCopies(particles.size(), new Point(0, 0)));
		for (int i = 0; i < particles.size(); i++) {
			BeemanParticle p = particles.get(i);
			 
			// In case particle has its own forces
			forces.set(i, Point.sum(forces.get(i), p.getForce(p)));

			Point currentA = calculateAcceleration(p.getForce(p), p.getMass());
			Point passedA = calculateAcceleration(p.getCurrentForces(), p.getMass());
			updatePosition(p, dt, currentA, passedA);
			Point futureA = calculateAcceleration(p.getForce(p), p.getMass());
			updateVelocity(p, currentA, passedA, futureA, dt);
			p.updateForces(forces.get(i));
		}
	}

	private void updatePosition(BeemanParticle p, double dt, Point currentA, Point passedA) {
		double rx = p.getX() + p.getXVelocity() * dt + (2.0 / 3.0) * currentA.x * Math.pow(dt, 2)
				- (1.0 / 6.0) * passedA.x * Math.pow(dt, 2);
		double ry = p.getY() + p.getYVelocity() * dt + 2.0 / 3.0 * currentA.y * Math.pow(dt, 2)
				- 1.0 / 6.0 * passedA.y * Math.pow(dt, 2);
		p.updatePosition(rx, ry);
	}

	private void updateVelocity(BeemanParticle p, Point currentA, Point passedA, Point futureA, double dt) {
		double vx = p.getXVelocity() + (1.0 / 3.0) * futureA.x * dt + (5.0 / 6.0) * currentA.x * dt - (1.0 / 6.0) * passedA.x * dt;
		double vy = p.getYVelocity() + 1.0 / 3.0 * futureA.y * dt + 5.0 / 6.0 * currentA.y * dt - 1.0 / 6.0 * passedA.y * dt;
		p.updateVelocity(vx, vy);
	}

	private Point calculateAcceleration(Point F, double m) {
		return new Point(F.x / m, F.y / m);
	}
}
