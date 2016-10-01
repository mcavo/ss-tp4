package run;

import java.util.ArrayList;
import java.util.List;

import model.GearPredictor;
import model.GearPredictorParticle;
import model.MASParticle;
import utils.OutputFileGenerator;
import utils.OutputXYZFilesGenerator;

public class GearPredictorRunner {

	public GearPredictorRunner(){
		this.run();
	}
	
	private final double maxTime = 5.0;
	
	private double time;
	
	public void run() {
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator("animation/", "state");
		OutputFileGenerator outputFileGenerator = new OutputFileGenerator("animation/", "gear");
		List<GearPredictorParticle> particles = new ArrayList<GearPredictorParticle>();
		particles.add(new GearPredictorParticle(1, 1E4, 100, 70));
		GearPredictor gp = new GearPredictor(particles.get(0));
		time = 0;
		double dt = 1E-3;
		while (time < maxTime) {
			outputXYZFilesGenerator.printState(particles);
			outputFileGenerator.printParticlesPosition(particles);
			gp.run(dt);
			time+=dt;
		}
		outputXYZFilesGenerator.printState(particles);
		outputFileGenerator.printParticlesPosition(particles);
		outputFileGenerator.writeFile();
	}
}
