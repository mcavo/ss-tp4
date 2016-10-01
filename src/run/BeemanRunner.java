package run;

import java.util.ArrayList;
import java.util.List;

import model.Beeman;
import model.MASParticle;
import utils.OutputFileGenerator;
import utils.OutputXYZFilesGenerator;
import utils.RandomUtils;

public class BeemanRunner {
	
	public BeemanRunner(boolean print) {
		super();
		printOutput = print;
		this.run();
	}
	
	public static Statistics stats;

	private final boolean printOutput;
	private final double maxTime = 5.0;
	
	private double time;

	public void run() {
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator("animation/", "state");
		OutputFileGenerator outputFileGenerator = new OutputFileGenerator("animation/", "beeman");
		List<MASParticle> particles = new ArrayList<MASParticle>();
		particles.add(new MASParticle(1, 1E4, 100, 70));
		Beeman v = new Beeman(particles);
		time = 0;
		double dt = 1E-3;
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
