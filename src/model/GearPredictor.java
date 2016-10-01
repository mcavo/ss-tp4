package model;


public class GearPredictor {

	private GearPredictorParticle particle;
	
	public GearPredictor(GearPredictorParticle particle) {
		this.particle = particle;
	}

	public void run(double dt) {
		particle.predict(dt);
		particle.calculateR2(dt);
		particle.correct(dt);
		particle.update();
	}
}
