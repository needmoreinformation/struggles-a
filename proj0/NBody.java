public class NBody {
	public static double readRadius(String dataFilePath) {
		/* Start reading the file */
		In file = new In(dataFilePath);
		
		/* Look for line containing given data file path */
		file.readInt(); /* Read number of planets and disregard */
		return file.readDouble();
	}
	
	public static Planet[] readPlanets(String dataFilePath) {
		In file = new In(dataFilePath);
		
		Planet[] planets = new Planet[file.readInt()];
		double r = file.readDouble();
		
		for (int i = 0; i < planets.length; i++) {
			planets[i] = new Planet(file.readDouble(), file.readDouble(), file.readDouble(), file.readDouble(), file.readDouble(), file.readString());			
		}
				
		return planets;
	}
	

	public static void main(String[] args) {
		/* Collecting all needed input */
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		
		Planet[] planets = readPlanets(filename);
		Double scale = readRadius(filename);
		
		/* Drawing the background */
		StdDraw.setScale(-scale, scale);
		StdDraw.picture(0, 0, "images/starfield.jpg");
				
		/* Drawing all of the planets */
		for (Planet p : planets) {
			p.draw();
		}
		
		double time = 0;
		
		
		
		while (time <= T) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for (int i = 0; i < planets.length; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);				
			}
			
			for (int i = 0; i < planets.length; i++) {				
				planets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg");
			/* Drawing all of the planets */
			for (Planet p : planets) {
				p.draw();
			}

			StdDraw.show(10);
			time += dt;
		}
		
		
		/* Printing the universe */
		StdOut.println(planets.length);
		StdOut.printf("%.2e\n", scale);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
			
		}
		
		System.exit(0);
	}
}
