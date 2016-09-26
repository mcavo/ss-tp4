package run;

import java.util.ArrayList;
import java.util.List;

import model.MASParticle;
import model.Verlet;
import utils.OutputFileGenerator;
import utils.OutputXYZFilesGenerator;
import utils.RandomUtils;

public class VerletRunner {
	
	public static void main(String[] args) {
 		new VerletRunner(60, 23456, true, 300);
	}
	
	public VerletRunner(int fps, int seed, boolean print, int N) {
		super();
		deltaTime = 1.0 / fps;
		printOutput = print; 
		this.seed = seed;
		RandomUtils.setSeed(seed);
		this.N = N;
		this.run();
	}
	
	public static Statistics stats;

	private final double deltaTime;
	private final boolean printOutput;
	private final int N;
	private final int seed;
	private final double maxTime = 5.0;
	
	private double time;

	public void run() {
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator("animation/", "state");
		OutputFileGenerator outputFileGenerator = new OutputFileGenerator("animation/", "verlet");
		List<MASParticle> particles = new ArrayList<MASParticle>();
		particles.add(new MASParticle(1, 1E4, 100, 70));
		Verlet v = new Verlet(particles);
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