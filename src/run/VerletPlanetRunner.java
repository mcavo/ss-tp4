package run;

import java.util.ArrayList;
import java.util.List;

import model.MASParticle;
import model.Planet;
import model.Point;
import model.Verlet;
import model.VerletParticle;
import utils.OutputFileGenerator;
import utils.OutputXYZFilesGenerator;

public class VerletPlanetRunner {

	public VerletPlanetRunner() {
		this.run();
	}

	public static Statistics stats;

	private final double maxTime = 3600.0*24*900;

	private double time;

	public void run() {
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator(
				"animation/", "planets");

		double dt = 300.0;
		int dtToPrint = 60;
		List<VerletParticle> particles = new ArrayList<VerletParticle>();
		particles.add(new Planet(1, 0, 0, 0, 0, 0, 0, 1.988E30, 695700000.0));
		particles.add(new Planet(2, 1.391734353396533E11, -0.571059040560652E11, 0.0, 10801.963811159256, 27565.215006898345, -1.128630342716, 5.972E24, 6371000.0));
		particles.add(new Planet(3, 0.831483493435295E11, -1.914579540822006E11, 0.0, 23637.912321314047, 11429.021426712032, -0.029527542055, 6.4185E23, 3389900.0));
		Verlet v = new Verlet(particles, dt);
		time = 0;
		int i = 0;
		boolean spaceshipCreated = false;
		double min = Double.MAX_VALUE;
		double minz = Double.MAX_VALUE;
		while (time < maxTime) {
			if(time >= 50276700 && !spaceshipCreated){
				System.out.println("time releasing spaceship: "+String.format("%.0f",time));
				v.addSpaceShipTangencial();
				//v.addCustomSpaceShip();
				spaceshipCreated=true;
			}
			if(i++%dtToPrint==0){
				outputXYZFilesGenerator.printState(particles);
			}
			if(particles.size()>3){
				if(min>Point.dist(particles.get(2).getPosition(), particles.get(3).getPosition())-particles.get(2).getRadius()){
					min=Point.dist(particles.get(2).getPosition(), particles.get(3).getPosition())-particles.get(2).getRadius();
					minz=Math.abs(particles.get(2).getPosition().z - particles.get(3).getPosition().z)-particles.get(2).getRadius();
				}
				if(Point.dist(particles.get(2).getPosition(), particles.get(3).getPosition())-particles.get(2).getRadius()<0){
					System.out.println("time colliding Mars: "+String.format("%.0f",time));
					System.out.println("spaceship speed: "+String.format("%.2f", particles.get(3).getVelocity().abs()));
					particles.remove(3);
				}
			}
			v.run(dt);
			time += dt;
		}
		outputXYZFilesGenerator.printState(particles);
		System.out.println("min "+min);
		//System.out.println("minz "+minz);
	}
}
