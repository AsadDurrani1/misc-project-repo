package matrixmodel;
/** "Nobody can be told what the Matrix is, you have to see it for yourself." - Morpheus
 * 
 * @author Asad Durrani
 *
 */
public class Matrix {
	public final int numRows;
	public final int numCols;
	protected Vector[] data;
	
	public Matrix(Vector[] data) throws MatrixException{
		if (data.length == 0) {
			throw new MatrixException("Number of rows in matrix must be a positive integer.");
		}
		for (Vector v: data) {
			if (v.length() == 0) {
				throw new MatrixException("Length of rows of Matrix must be a positive integer.");
			} else if (v.length() != data[0].length()) {
				throw new MatrixException("Rows of Matrix must be of the same length.");
			}
		}
		// No errors
		this.data = data;
		this.numRows = this.data.length;
		this.numCols = this.data[0].length();
	}
	
	public Matrix(double[][] inpData) throws MatrixException {
		if (inpData.length == 0) {
			throw new MatrixException("Number of rows in matrix must be a positive integer.");
		}
		for (double[] arr: inpData) {
			if (arr.length == 0) {
				throw new MatrixException("Length of rows of Matrix must be a positive integer.");
			} else if (arr.length != inpData[0].length) {
				throw new MatrixException("Rows of Matrix must be of the same length.");
			}
		}
		// No errors
		this.data = new Vector[inpData.length];
		for (int i = 0; i < inpData.length; i++) {
			Vector v = new Vector(inpData[i]);
			this.data[i] = v;
		}
		this.numRows = inpData.length;
		this.numCols = inpData[0].length;
	}
	
	public Vector[] getData() {
		return this.data;
	}
	
	public Vector getRow(int i) {
		return this.data[i];
	}
	
	public Vector getColumn(int j) {
		double[] arr = new double[this.numRows];
		for (int i = 0; i < this.numRows; i++) {
			arr[i] = this.getEntry(i, j);
		}
		return new Vector(arr);
	}
	
	public void setRow(int i, Vector v) throws MatrixException {
		if (v.length() != this.getRow(i).length()) {
			throw new MatrixException("Row has invalid length.");
		}
		this.data[i] = v;
	}
	
	public double getEntry(int row, int column) {
		return this.getRow(row).getCoord(column);
	}
	
	public void setIndex(int row, int column, double value) {
		this.getRow(row).setCoord(column, value);
	}
	
	public Matrix add(Matrix other) throws MatrixException {
		if (this.numRows != other.numRows || this.numCols != other.numCols) {
			throw new MatrixException("Cannot add matrices of different dimensions.");
		}
		Vector[] vectorArray = new Vector[this.numRows];
		for (int i = 0; i < this.numRows; i++) {
			try {
				vectorArray[i] = this.getRow(i).add(other.getRow(i));
			} catch (VectorException e) {}
		}	
		return MatrixFactory.createMatrix(this.getClass(), vectorArray);
	}

	public Matrix scalarMultiply(double d) {
		Vector[] vectorArray = new Vector[this.numRows];
		for (int i = 0; i < this.numRows; i++) {
			vectorArray[i] = this.getRow(i).scalarMultiply(d);
		}
		try {
			return MatrixFactory.createMatrix(this.getClass(), vectorArray);
		} catch (MatrixException e) {}
		return null;
	}
	
	public Matrix subtract(Matrix other) throws MatrixException {
		Matrix m2Neg = other.scalarMultiply(-1);
		return this.add(m2Neg);
	}
	
	public Matrix matrixMultiply(Matrix other) throws MatrixException {
		if (this.numCols != other.numRows) {
			throw new MatrixException("Matrices incompatible for multiplication.");
		}
		double[][] arr = new double[this.numRows][other.numCols];
		for (int i = 0; i < this.numRows; i++) {
			for (int j = 0; j < other.numCols; j++) {
				try {
				arr[i][j] = this.getRow(i).dot(other.getColumn(j));
				} catch (VectorException e) {}
			}
		}
		return new Matrix(arr);
	}
	
	public boolean equals(Matrix other) {
		if (this.numRows != other.numRows || this.numCols != other.numCols) {
			return false;
		}
		for (int i = 0; i < this.numRows; i++) {
			for (int j = 0; j < this.numCols; j++) {
				if (this.getEntry(i, j) != other.getEntry(i, j)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void switchRows(int i, int j) {
		Vector temp = this.data[i];
		try {
			this.setRow(i, this.getRow(j));
			this.setRow(j, temp);
		} catch (MatrixException e) {}
	}
	
	public Matrix getTranspose() {
		Vector[] vectorArray = new Vector[this.numCols];
		for (int i = 0; i < this.numCols; i++) {
			Vector v = new Vector(new double[this.numRows]);
			for (int j = 0; j < this.numRows; j++) {
				v.setCoord(j, this.getEntry(j, i));
			}
			vectorArray[i] = v;
		}
		try {
			return new Matrix(vectorArray);
		} catch (MatrixException e) {}
		return null;
	}
	
	public void multiplyRow(int i, double d){
		try {
			this.setRow(i, this.getRow(i).scalarMultiply(d));
		} catch (Exception e) {}
	}
	
	public void addScalarMultipleOfRows(double d, int i, int j) {
		try {
			this.setRow(j, this.getRow(j).add(this.getRow(i).scalarMultiply(d)));
		} catch (MatrixException e) {} 
		catch (VectorException e) {}
	}
	
	public Matrix REF() {
		int i = 0; // current row index
		int j = 0; // current column index
		int k = 0; // pivot row index
		double multiplier = 1;
		Matrix ret = this.matrixCopy();
		while (j < ret.numCols) {
			i = k;
			while (i < ret.numRows) {
				if (ret.getEntry(i, j) != 0) {
					ret.switchRows(i, k);
					ret.multiplyRow(k, 1/ret.getEntry(k, j));
					i++;
					k++;
					break;
				}
				i++;
			}
			while (i < ret.numRows) {
				if (ret.getEntry(i, j) != 0) {
					multiplier = -ret.getEntry(i, j);
					ret.addScalarMultipleOfRows(multiplier, k-1, i);
				}
				i++;
			}
			j++;
		}
		return ret;
	}
	
	public Matrix RREF() {
		int i = 0; // current row index
		int j = 0; // current column index
		int k = 0; // pivot row index
		boolean columnHasLeadingOne = false;
		Matrix ret = this.matrixCopy();
		while (j < ret.numCols) {
			columnHasLeadingOne = false;
			i = k;
			while (i < ret.numRows) {
				if (ret.getEntry(i, j) != 0) {
					ret.switchRows(i, k);
					ret.multiplyRow(k, 1/ret.getEntry(k, j));
					columnHasLeadingOne = true;
					k++;
					break;
				}
				i++;
			}
			i = 0;
			while (i < ret.numRows) {
				if (ret.getEntry(i, j) != 0 && i != k-1 && columnHasLeadingOne) {
					ret.addScalarMultipleOfRows(-ret.getEntry(i, j), k-1, i);
				}
				i++;
			}
			j++;
		}
		return ret;
	}
		
	public int getRank() {
		Matrix m = this.RREF();
		int rank = 0;
		int i = 0;
		int j = 0;
		while (i < this.numRows) {
			j = 0;
			while (j < this.numCols) {
				if (m.getEntry(i, j) == 1) {
					rank++;
					break;
				}
				j++;
			}
			i++;
		}
		return rank;
	}

	public Matrix matrixCopy() {
		Vector[] arr = new Vector[this.numRows];
		for (int i = 0; i < this.numRows; i++) {
			arr[i] = this.getRow(i).copy();
		}
		try {
			return MatrixFactory.createMatrix(this.getClass(), arr);
		} catch (MatrixException e) {}
		return null;
	}
	
	public SquareMatrix toSquareMatrix() throws MatrixException {
		return new SquareMatrix(this.matrixCopy().getData());
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < this.numRows; i++) {
			for (int j = 0; j < this.numCols; j++) {
				if ((int)this.getEntry(i, j) == this.getEntry(i, j)) {
				s += "(" + (int)this.getEntry(i, j) + ")";
				} else {
					s += "(" + this.getEntry(i, j) + ")";
				}
				if ((j+1) % this.numCols == 0) {
					s += "\n";
				}
			}
		}
		return s;
	}
	
	public static void main(String[] args) throws MatrixException, VectorException {
		double[][] arr1 = {
				{1,0,-1},
				{0,1,1},
				{0,0,0}
		};
		double[][] arr2 = {
				{4,2,3,1},
				{2,3,5,9},
				{7,-1,4,4}
		};
		SquareMatrix m = new SquareMatrix(arr1);
		System.out.println(m.RREF());
	}
}