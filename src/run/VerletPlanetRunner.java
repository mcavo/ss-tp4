package run;

import java.util.ArrayList;
import java.util.List;

import model.MASParticle;
import model.Planet;
import model.Verlet;
import utils.OutputFileGenerator;
import utils.OutputXYZFilesGenerator;

public class VerletPlanetRunner {

	public VerletPlanetRunner() {
		this.run();
	}

	public static Statistics stats;

	private final double maxTime = 3600.0*24*180;

	private double time;

	public void run() {
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator(
				"animation2/", "planets");
		
		List<Planet> particles = new ArrayList<Planet>();
		particles.add(new Planet(1, 0, 0, 0, 0, 0, 0, 1.988E30, 695700000.0)); // Sun
		particles.add(new Planet(2, 1.391734353396533E11, -0.571059040560652E11, 0.0, 10801.963811159256, 27565.215006898345, -1.128630342716, 5.972E24, 6371000.0)); // Earth
		Verlet v = new Verlet(particles);
		time = 0;
		double dt = 3600.0;
		while (time < maxTime) {
			outputXYZFilesGenerator.printState(particles);
			v.run(dt);
			time += dt;
		}
		outputXYZFilesGenerator.printState(particles);
		
	}
}
