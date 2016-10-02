package run;

import java.util.ArrayList;
import java.util.List;

import model.Planet;
import model.Point;
import model.Verlet;
import model.VerletParticle;
import utils.OutputXYZFilesGeneratorCentered;

public class VerletPlanetMoonRunner {
	
	public VerletPlanetMoonRunner() {
		this.run();
	}

	private final double maxTime = 3600.0*24*900;

	private double time;

	public void run() {
		OutputXYZFilesGeneratorCentered outputXYZFilesGenerator = new OutputXYZFilesGeneratorCentered(
				"animation/", "moon");

		double dt = 300.0;
		int dtToPrint = 60;
		List<VerletParticle> particles = new ArrayList<VerletParticle>();
		
		
		VerletParticle sun = new Planet(1, 0, 0, 0, 0, 0, 0, 1.988E30, 695700000.0);
		VerletParticle earth = new Planet(2, 1.391734353396533E11, -0.571059040560652E11, 0.0, 10801.963811159256, 27565.215006898345, -1.128630342716, 5.972E24, 6371000.0);
		VerletParticle mars = new Planet(3, 0.831483493435295E11, -1.914579540822006E11, 0.0, 23637.912321314047, 11429.021426712032, -0.029527542055, 6.4185E23, 3389900.0);
		particles.add(sun);
		particles.add(earth);
		particles.add(mars);
		
		double dist = 384400000;
		Point position = new Point(earth.getX()+dist, earth.getY());
		Point velocity = Point.sum(new Point(0.0, 1023.05), earth.getVelocity());
		VerletParticle moon = new Planet(4, position.x, position.y, 0, velocity.x, velocity.y,0, 7.349e22, 3474000);
		particles.add(moon);
		Verlet v = new Verlet(particles, dt);
		time = 0;
		int i = 0;
		while (time < maxTime) {
			if(i++%dtToPrint==0){
				outputXYZFilesGenerator.printState(particles);
			}
			v.run(dt);
			time += dt;
		}
		outputXYZFilesGenerator.printState(particles);
	}
}

