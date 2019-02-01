package matrixmodel;
public class SquareMatrix extends Matrix {
	public SquareMatrix(Vector[] data) throws MatrixException {
		super(data);
		if (data.length != data[0].length()) {
			throw new MatrixException("Given array does not represent a square matrix.");
		}
	}
	
	public SquareMatrix(double[][] inpData) throws MatrixException {
		super(inpData);
		if (inpData.length != inpData[0].length) {
			throw new MatrixException("Given array does not represent a square matrix.");
		}
	}
	
	public SquareMatrix matrixMultiply(Matrix other) throws MatrixException {
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
		return new SquareMatrix(arr);
	}
	
	public SquareMatrix pow(int n) throws MatrixException {
		if (n < 0) {
			throw new MatrixException("Invalid exponent given.");
		}
		SquareMatrix ret = MatrixFactory.createIdentityMatrix(this.numRows);
		for (int i = 0; i < n; i++) {
			ret = ret.matrixMultiply(this);
		}
		return ret;
	}
	
	public boolean isUpperTriangular() {
		for (int j = 0; j < this.numCols; j++) {
			for (int i = j+1; i < this.numRows; i++) {
				if (this.getEntry(i, j) != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public double getTrace() {
		double ret = 0;
		for (int i = 0; i < this.numRows; i++) {
			ret += this.getEntry(i, i);
		}
		return ret;
	}
	
	public double getProductOfMainDiagonal() {
		double ret = 1;
		for (int i = 0; i < this.numRows; i++) {
			ret *= this.getEntry(i, i);
		}
		return ret;
	}
	
	public boolean isLowerTriangular() {
		for (int i = 0; i < this.numRows; i++) {
			for (int j = i+1; j < this.numRows; j++) {
				if (this.getEntry(i, j) != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isDiagonal() {
		return this.isUpperTriangular() && this.isLowerTriangular();
	}
	
	public boolean isSymmetric() {
		return this.equals(this.getTranspose());
	}
	
	public boolean isSkewSymmetric() {
		return this.scalarMultiply(-1).equals(this.getTranspose());
	}
	
	public double getDeterminant() {
		int i = 0; // current row index
		int j = 0; // current column index
		int k = 0; // pivot row index
		double multiplier = 1;
		double det = 1;
		Matrix m = this.matrixCopy();
		while (j < m.numCols) {
			i = k;
			while (i < m.numRows) {
				if (m.getEntry(i, j) != 0) {
					if (i != k) {
						m.switchRows(i, k);
						det *= -1;
					}
					det *= m.getEntry(k, j);
					m.multiplyRow(k, 1/m.getEntry(k, j));
					i++;
					k++;
					break;
				}
				i++;
			}
			while (i < m.numRows) {
				if (m.getEntry(i, j) != 0) {
					multiplier = -m.getEntry(i, j);
					m.addScalarMultipleOfRows(multiplier, k-1, i);
				}
				i++;
			}
			j++;
		}
		return det*((SquareMatrix) m).getProductOfMainDiagonal();
	}
	
	public SquareMatrix getInverse() throws MatrixException {
		SquareMatrix identity = MatrixFactory.createIdentityMatrix(this.numRows);
		SquareMatrix copy = (SquareMatrix) this.matrixCopy();
		int i = 0;
		int j = 0;
		int k = 0;
		double multiplier = 1;
		boolean columnHasLeadingOne = false;
		while (j < this.numCols) {
			i = k;
			while (i < this.numRows) {
				if (copy.getEntry(i, j) != 0) {
					copy.switchRows(i, k);
					identity.switchRows(i, k);
					multiplier = 1/copy.getEntry(k, j);
					copy.multiplyRow(k, multiplier);
					identity.multiplyRow(k, multiplier);
					columnHasLeadingOne = true;
					k++;
					break;
				}
				i++;
			}
			i = 0;
			while (i < this.numRows) {
				if (copy.getEntry(i, j) != 0 && i != k-1 && columnHasLeadingOne) {
					multiplier = -copy.getEntry(i, j);
					copy.addScalarMultipleOfRows(multiplier, k-1, i);
					identity.addScalarMultipleOfRows(multiplier, k-1, i);
				}
			i++;
			}
		j++;
		}
		if (copy.equals(MatrixFactory.createIdentityMatrix(this.numRows))) {
			return identity;
		}
		return null;
	}
	
	public static void main(String[] args) throws MatrixException {
		double[][] arr = {
				{1,2,3,4},
				{5,6,7,8},
				{2,6,4,8},
				{3,1,1,2},
		};
		double[][] arr1 = {
				{1,1},
				{1,0}
		};
		SquareMatrix m = new SquareMatrix(arr1);
		for (int i = 0; i < 20; i++) {
			System.out.println(m.pow(i));
		}
	}
}