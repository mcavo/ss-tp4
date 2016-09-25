package run;

import java.util.ArrayList;
import java.util.List;

import model.MASParticle;
import model.Particle;
import model.Verlet;
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
	private final double maxTime = 40.0;
	
	private double time;

	public void run() {
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator("animation/", "state");
		List<MASParticle> particles = new ArrayList<MASParticle>();
		Verlet v = new Verlet(particles);
		particles.add(new MASParticle(1, 1E4, 100, 70));
		time = 0;
		double dt = 1E-3;
		if (printOutput) {
			outputXYZFilesGenerator.printState(particles);	
		}
		while (time < maxTime) {
			v.run(time);
			outputXYZFilesGenerator.printState(particles);
			time+=dt;
		}
	}

//	public List<Particle> createParticles(int N, boolean centerBigParticle) {
//		List<Particle> particles = new ArrayList<Particle>();
//		// The particle with id 1 is the one with a big mass
//		int id = 1;
//		Particle bigParticle;
//		if (centerBigParticle) {
//			bigParticle = new Particle(id++, L/2, L/2, 0, bigMass, bigRadius);
//		} else {
//			bigParticle = new Particle(id++, RandomUtils.getRandomDouble(bigRadius, L - bigRadius),
//					RandomUtils.getRandomDouble(bigRadius, L - bigRadius), 0, bigMass, bigRadius);	
//		}
//		particles.add(bigParticle);
//		
//		while(particles.size() < N) {
//			Particle smallParticle = new Particle(id, RandomUtils.getRandomDouble(smallRadius, L - smallRadius),
//					RandomUtils.getRandomDouble(smallRadius, L - smallRadius),
//					RandomUtils.getRandomDouble(minV, maxV), smallMass, smallRadius);
//			boolean areOverlapped = false;
//			for (Particle p : particles) {
//				if (Particle.areOverlapped(smallParticle, p)) {
//					areOverlapped = true;
//					break;
//				}
//			}
//			if (!areOverlapped) {
//				id++;
//				particles.add(smallParticle);	
//			}
//		}
//		return particles;
//	}
//	
//	public List<Particle> createParticles() {
//		List<Particle> particles = new ArrayList<Particle>();
//		// The particle with id 1 is the one with a big mass
//		int id = 1;
//		Particle bigParticle = new Particle(id++, RandomUtils.getRandomDouble(bigRadius, L - bigRadius),
//				RandomUtils.getRandomDouble(bigRadius, L - bigRadius), 0, 0, bigMass, bigRadius);
//		particles.add(bigParticle);
//		int errors = 0;
//		while (errors < maxErrors) {
//			Particle smallParticle = new Particle(id, RandomUtils.getRandomDouble(smallRadius, L - smallRadius),
//					RandomUtils.getRandomDouble(smallRadius, L - smallRadius), RandomUtils.getRandomDouble(minV, maxV),
//					RandomUtils.getRandomDouble(minV, maxV), smallMass, smallRadius);
//			boolean areOverlapped = false;
//			for (Particle p : particles) {
//				if (Particle.areOverlapped(smallParticle, p)) {
//					areOverlapped = true;
//					break;
//				}
//			}
//			if (areOverlapped) {
//				errors++;
//			} else {
//				errors = 0;
//				id++;
//				particles.add(smallParticle);
//			}
//		}
//		return particles;
//	}
//
//	public double calculateK(List<Particle> particles) {
//		double K = 0.0;
//		for (Particle p : particles) {
//			K += p.getMass() * Math.pow(p.getSpeed(), 2);
//		}
//		return 0.5*K;
//	}

}
