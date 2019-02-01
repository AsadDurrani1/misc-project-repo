package matrixmodel;
public class Vector {
	private double[] coords;
	
	public Vector(double[] coords) {
		this.coords = coords;
	}
	
	public double getCoord(int i) {
		return this.coords[i];
	}
	
	public void setCoord(int i, double value) {
		this.coords[i] = value;
	}
	
	public int length() {
		return this.coords.length;
	}
	
	public Vector copy() {
		return new Vector(this.coords.clone());
	}
	
	public Vector add(Vector other) throws VectorException {
		if (this.length() != other.length()) {
			throw new VectorException("Cannot add vectors of different lengths.");
		}
		Vector ret = new Vector(new double[this.coords.length]);
		for (int i = 0; i < this.coords.length; i++) {
			ret.setCoord(i, this.getCoord(i) + other.getCoord(i));
		}
		return ret;
	}
	
	public Vector scalarMultiply(double d) {
		Vector ret = new Vector(new double[this.coords.length]);
		for (int i = 0; i < this.coords.length; i++) {
			ret.setCoord(i, d*this.getCoord(i));
		}
		return ret;
	}
	
	public double dot(Vector other) throws VectorException {
		if (this.length() != other.length()) {
			throw new VectorException("Cannot dot vectors of different dimensions.");
		}
		double ret = 0;
		for (int i = 0; i < this.length(); i++) {
			ret += this.getCoord(i) * other.getCoord(i);
		}
		return ret;
	}
	
	public String toString() {
		String ret = "(";
		for (int i = 0; i < this.coords.length-1;i++) {
			ret += this.coords[i] + ", ";
		}
		ret += this.coords[this.coords.length-1] + ")";
		return ret;
	}
	
	public static void main(String[] args) {
		double[] arr = {1,2,3,4};
		Vector v = new Vector(arr);
		v.setCoord(0, 0);
		System.out.println(v);
	}
}