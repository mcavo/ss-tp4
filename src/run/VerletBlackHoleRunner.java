package run;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Particle;
import model.Planet;
import model.Point;
import model.Verlet;
import model.VerletParticle;
import utils.OutputXYZFilesGenerator;
import utils.RandomUtils;

public class VerletBlackHoleRunner {

	public VerletBlackHoleRunner() {
		RandomUtils.setSeed(1235);
		this.run();
	}

	private final double maxTime = 3600.0 * 24 * 500;

	private double time;

	public void run() {
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator(
				"animation/", "planets");

		double dt = 300.0;
		int dtToPrint = 60;
		List<VerletParticle> particles = new ArrayList<VerletParticle>();
		particles.add(new Planet(1, 0, 0, 0, 0, 0, 0, 5E30, 7000000000.0));
		for (int i = 2; i < 200; i++) {
			double x = RandomUtils.getRandomDouble(4e10, 2e11)
					* RandomUtils.getSign();
			double y = RandomUtils.getRandomDouble(4e10, 2e11)
					* RandomUtils.getSign();
			double mod = RandomUtils.getRandomDouble(1e4, 8e4)
					* RandomUtils.getSign();
			Point velocity = new Point(-y, x);
			velocity.normalize();
			velocity.applyFunction(a -> mod * a);
			double m = RandomUtils.getRandomDouble(1e23, 1e24);
			particles.add(new Planet(i, x, y, 0, velocity.x, velocity.y, 0, m,
					700000000));
		}
		Verlet v = new Verlet(particles, dt);
		time = 0;
		int i = 0;
		while (time < maxTime) {
			if (i++ % dtToPrint == 0) {
				System.out.println(time);
				outputXYZFilesGenerator.printState(particles);
			}
			v.run(dt);
			Iterator<VerletParticle> it = particles.iterator();
			VerletParticle bh = it.next();
			double mass = 0.0;
			double radius = 0.0;
			while (it.hasNext()) {
				VerletParticle p = it.next();
				if (Point.dist(bh.getPosition(), p.getPosition())
						- bh.getRadius() - p.getRadius() < 0) {
					it.remove();
					mass+=p.getMass();
					radius+=p.getRadius();
				}
			}
			bh.addMass(mass*100000);
			bh.addRadius(radius/2);
			time += dt;
		}
		outputXYZFilesGenerator.printState(particles);
	}
}
