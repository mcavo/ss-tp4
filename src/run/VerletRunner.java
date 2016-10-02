package run;

import java.util.ArrayList;
import java.util.List;

import model.MASParticle;
import model.Verlet;
import model.VerletParticle;
import utils.OutputFileGenerator;
import utils.OutputXYZFilesGenerator;

public class VerletRunner {
	
	public VerletRunner() {
		super();
		this.run();
	}
	
	public static Statistics stats;

	private final double maxTime = 5.0;
	
	private double time;

	public void run() {

		double dt = 1E-3;
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator("animation/", "state");
		OutputFileGenerator outputFileGenerator = new OutputFileGenerator("animation/", "verlet");
		List<VerletParticle> particles = new ArrayList<VerletParticle>();
		particles.add(new MASParticle(1, 1E4, 100, 70));
		Verlet v = new Verlet(particles, dt);
		time = 0;
		while (time < maxTime) {
			outputXYZFilesGenerator.printState(particles);
			outputFileGenerator.printParticlesPosition(particles);
			v.run(dt);
			time+=dt;
		}
		outputXYZFilesGenerator.printState(particles);
		outputFileGenerator.printParticlesPosition(particles);
		outputFileGenerator.writeFile();
	}
	
}