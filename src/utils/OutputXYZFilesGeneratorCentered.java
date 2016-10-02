package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import model.Particle;
import model.Point;

public class OutputXYZFilesGeneratorCentered {

	private int frameNumber;
	private String path;

	public OutputXYZFilesGeneratorCentered(String directory, String file) {
		frameNumber = 0;
		this.path = directory + file;
		try {
			Files.createDirectories(Paths.get(directory));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Point earthPosition;

	public void printState(List<? extends Particle> particles) {
		List<String> lines = new LinkedList<String>();
		lines.add(String.valueOf(2));
		lines.add("ParticleId xCoordinate yCoordinate xDisplacement yDisplacement Radius R G B Transparency Selection");
		earthPosition = particles.get(1).getPosition();
		for (Particle p : particles) {
			if (p.getId() == 2 || p.getId() == 4) {
				lines.add(getInfoDiff(p, "0 0 1", 0, 0));
			}
		}
		writeFile(lines);
	}

	private String getInfoDiff(Particle p, String color, double transparency, int selection) {
		return p.getId() + " " + String.format("%f", p.getX()-earthPosition.x) + " " + String.format("%f", p.getY()-earthPosition.y) + " " + String.format("%f", p.getXVelocity()) + " " + String.format("%f", p.getYVelocity()) + " "
				+ String.format("%f", p.getRadius()) + " " + color + " " + transparency + " " + selection;
	}

	private void writeFile(List<String> lines) {
		Path file = Paths.get(path + frameNumber + ".xyz");
		frameNumber++;
		try {
			Files.write(file, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
