package model;

import java.util.HashMap;
import java.util.Map;

public class GearPredictorParticle extends Particle {
	
	private double k;
	private double gamma;
	private double deltaR2;
	private Map<Integer, Double> rp;
	private Map<Integer, Double> rc;
	final double[] coef = {3f/16f, 251f/360f, 1f, 11f/18f, 1f/6f, 1f/60f};
	
	public GearPredictorParticle(int id, double k, double gamma, double m){
		super(id, 1, 0, -gamma / (2*m), 0, m, 0); 
		this.gamma = gamma;
		this.k = k;
		rp = new HashMap<Integer, Double>();
		rc = new HashMap<Integer, Double>();
		initialize();
	}
	
	private void initialize(){
		rc.put(0, position.x);
		rc.put(1, velocity.x);
		for(int i=2; i<6; i++){
			rc.put(i, getR(rc.get(i-2), rc.get(i-1))/getMass());
		}
	}
	
	private Double getR(Double r0, Double r1){
		return (-k*r0-gamma*r1)/getMass();
	}

	private Double r(int i){
		return rc.get(i);
	}
	
	public void predict(double dt) {
		for(int i=0; i<6; i++){
			double sum = 0.0;
			double potDt = 1;
			double fact = 1;
			for(int j=i; j<6; j++){
				sum+=r(j)*potDt/fact;
				potDt*=dt;
				fact*=(j-i+1);
			}
			rp.put(i, sum);	
		}
	}

	public void calculateR2(double dt) {
		double f = -k*rp.get(0) - gamma*rp.get(1);
		double a = f/getMass();
		double deltaA = a-rp.get(2);
		deltaR2 = deltaA*dt*dt / 2.0;
	}

	public void correct(double dt) {
		double potDt = 1;
		double fact = 1;
		for(int i=0; i<6; i++){
			double value = rp.get(i)+coef[i]*deltaR2*fact / potDt;
			rc.put(i, value);
			fact *= (i+1);
			potDt *= dt;
		}
	}

	public void update() {
		position.x = r(0);
		velocity.x = r(1);
	}
}

