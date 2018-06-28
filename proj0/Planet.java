public class Planet {
	public static final double GRAV_CONSTANT = 6.67e-11;
	
	double xxPos;
	double yyPos;
	double xxVel;
	double yyVel;
	double mass;
	String imgFileName;
	
	public Planet(double xP, double yP, double xV,
				  double yV, double m, String img) {
	
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
					  
	}
	
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}
	
	public double calcDistance(Planet p) {
		double dx = p.xxPos - this.xxPos;
		double dy = p.yyPos - this.yyPos;
		return Math.sqrt((dx * dx) + (dy * dy));
	}
	
	public double calcForceExertedBy(Planet p) {
		double r = calcDistance(p);
		r *= r;
		return (GRAV_CONSTANT * this.mass * p.mass) / r;
	}
	
	public double calcForceExertedByY(Planet p) {
		double dy = p.yyPos - this.yyPos;
		double r = calcDistance(p);
		return calcForceExertedBy(p) * dy / r;
	}
				  
				  
	public double calcForceExertedByX(Planet p) {
		double dx = p.xxPos - this.xxPos;
		double r = calcDistance(p);
		return calcForceExertedBy(p) * dx / r;
	}
	
	public double calcNetForceExertedByX(Planet[] planets) {
		double net = 0;
		for (Planet p : planets) {
			if (!p.equals(this)) {
				net += calcForceExertedByX(p);
			}
		}
		return net;
	}
	
	public double calcNetForceExertedByY(Planet[] planets) {
		double net = 0;
		for (Planet p : planets) {
			if (!p.equals(this)) {
				net += calcForceExertedByY(p);
			}
		}
		return net;
	}
	
	public void update(double dt, double fX, double fY) {
		/* Calculate acceleration using provided x and y forces */
		double aX = fX / mass;
		double aY = fY / mass;
		
		/* Calculate new velocity */
		xxVel = xxVel + dt * aX;
		yyVel = yyVel + dt * aY;
		
		/* Calculate new position */
		xxPos = xxPos + dt * xxVel;
		yyPos = yyPos + dt * yyVel;		
	}
	
	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
	
				  
}
