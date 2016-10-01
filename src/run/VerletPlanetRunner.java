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

	private final double maxTime = 3600.0*24*400;

	private double time;

	public void run() {
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator(
				"animation/", "planets");

		double dt = 3600.0;
		int dtToPrint = 3;
		List<Planet> particles = new ArrayList<Planet>();
		particles.add(new Planet(1, 0, 0, 0, 0, 0, 0, 1.988E30, 6957000000.0)); // Sun (size*10)
		particles.add(new Planet(2, 1.391734353396533E11, -0.571059040560652E11, 0.0, 10801.963811159256, 27565.215006898345, -1.128630342716, 5.972E24, 637100000.0)); // Earth (size*100)
		particles.add(new Planet(3, 0.831483493435295E11, -1.914579540822006E11, 0.0, 23637.912321314047, 11429.021426712032, -0.029527542055, 6.4185E23, 338990000.0)); // Mars (size*100)
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
