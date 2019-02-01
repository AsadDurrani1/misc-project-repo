package matrixmodel;
public class MatrixFactory {
	public static Matrix createMatrix(Class<? extends Matrix> classType, Vector[] data) throws MatrixException {
		if (classType.toString().equals("class matrixmodel.Matrix")) {
			return new Matrix(data);
		} else if (classType.toString().equals("class matrixmodel.SquareMatrix")) {
			return new SquareMatrix(data);
		}
		return null;
	}
	
	public static Matrix createMatrix(Class<? extends Matrix> classType, double[][] data) throws MatrixException {
		if (classType.toString().equals("class matrixmodel.Matrix")) {
			return new Matrix(data);
		} else if (classType.toString().equals("class matrixmodel.SquareMatrix")) {
			return new SquareMatrix(data);
		}
		return null;
	}
	
	public static Matrix createZeroMatrix(int m, int n) {
		double[][] arr = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				arr[i][j] = 0;
			}
		}
		try {
			return new Matrix(arr);
		} catch(Exception e) {}
		return null;
	}
	
	public static SquareMatrix createSquareZeroMatrix(int n) {
		double[][] arr = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				arr[i][j] = 0;
			}
		}
		try {
		return new SquareMatrix(arr);
		} catch (MatrixException e) {}
		return null;
	}
	
	public static SquareMatrix createIdentityMatrix(int n) {
		double[][] arr = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					arr[i][j] = 1;
				} else {
					arr[i][j] = 0;
				}
			}
		}
		try {
		return new SquareMatrix(arr);
		} catch (MatrixException e) {}
		return null;
	}
}