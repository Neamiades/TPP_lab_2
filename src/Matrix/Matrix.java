package Matrix;

import com.sun.media.sound.InvalidDataException;

public class Matrix
{
	private double[][] _matrix;

	private int _width;

	private int _height;

	private Matrix(int width, int height)
	{
		_width = width;
		_height = height;

		_matrix = new double[_height][_width];
	}

	public Matrix(double[][] matrix)
	{
		_width = matrix[0].length;
		_height = matrix.length;
		_matrix = new double[_height][_width];

		CopyMatrix(matrix, _matrix);
	}

	public Matrix(Matrix matrix)
	{
		_width = matrix._matrix[0].length;
		_height = matrix._matrix.length;
		_matrix = new double[_height][_width];

		CopyMatrix(matrix._matrix, _matrix);
	}

	public Matrix(int width, int height, double... digits) throws InvalidDataException {

		ValidateInputParameters(width, height, digits);

		_width = width;
		_height = height;

		_matrix = new double[_height][_width];

		for (int i = 0, n = 0; i < _height; i++)
			for (int j = 0; j < _width; j++)
				_matrix[i][j] = digits[n++];
	}

	public final int getWidth()
	{
		return _width;
	}

	public final int getHeight()
	{
		return _height;
	}

	public final Matrix GetTransponent()
	{
		Matrix transponentMatrix = new Matrix(getHeight(), getWidth());

		for (int i = 0; i < getHeight(); i++)
			for (int j = 0; j < getWidth(); j++)
				transponentMatrix._matrix[j][i] = _matrix[i][j];

		return transponentMatrix;
	}

	public final Matrix GetReverse() throws InvalidDataException {
		if (getWidth() != getHeight())
			throw new InvalidDataException("Inverse matrix can not be found in a non-square matrix");

		Matrix copyMatrix = new Matrix(_matrix);
		Matrix reverseMatrix = new Matrix(getWidth(), getHeight());

		for (int i = 0; i < getHeight(); i++)
			for (int j = 0; j < getWidth(); j++)
				reverseMatrix._matrix[i][j] = i == j ? 1.0 : 0;

		for (int j = 0; j < getWidth() - 1; j++)
		{
			for (int i = j + 1; i < getHeight(); i++)
			{
				double factor = -copyMatrix._matrix[i][j] / copyMatrix._matrix[j][j];
				for (int k = j; k < getWidth(); k++)
					copyMatrix._matrix[i][k] += copyMatrix._matrix[j][k] * factor;
				for (int k = 0; k < getWidth(); k++)
					reverseMatrix._matrix[i][k] += reverseMatrix._matrix[j][k] * factor;

				if (copyMatrix._matrix[j + 1][j + 1] == 0)
					throw new InvalidDataException("Inverse matrix doesn't exist");
			}
		}

		for (int j = getWidth() - 1; j > -1; j--)
		{
			for (int k = 0; k < getWidth(); k++)
				reverseMatrix._matrix[j][k] /= copyMatrix._matrix[j][j];

			copyMatrix._matrix[j][j] /= copyMatrix._matrix[j][j];
			for (int i = j - 1; i > -1; i--)
			{
				double factor = -copyMatrix._matrix[i][j];
				copyMatrix._matrix[i][j] += copyMatrix._matrix[j][j] * factor;

				for (int k = 0; k < getWidth(); k++)
					reverseMatrix._matrix[i][k] += reverseMatrix._matrix[j][k] * factor;
			}
		}

		return reverseMatrix;
	}

	public final static Matrix OpMultiply(Matrix A, Matrix B) throws InvalidDataException {

		int aRows = A.getHeight();
		int aColumns = A.getWidth();
		int bRows = B.getHeight();
		int bColumns = B.getWidth();

		if (aColumns != bRows)
			throw new InvalidDataException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");

		Matrix C = new Matrix(bColumns, aRows);

		for (int i = 0; i < aRows; i++)
			for (int j = 0; j < bColumns; j++)
				C._matrix[i][j] = 0.0;

		for (int i = 0; i < aRows; i++)
			for (int j = 0; j < bColumns; j++)
				for (int k = 0; k < aColumns; k++)
					C._matrix[i][j] += A._matrix[i][k] * B._matrix[k][j];

		return C;
	}

	public final static Matrix OpMultiply(double k, Matrix matrix)
	{
		Matrix resultMatrix = new Matrix(matrix.getWidth(), matrix.getHeight());

		for (int i = 0; i < matrix.getHeight(); i++)
			for (int j = 0; j < matrix.getWidth(); j++)
				resultMatrix._matrix[i][j] = matrix._matrix[i][j] * k;

		return resultMatrix;
	}

	public final static Matrix OpAddition(Matrix mtx1, Matrix mtx2) throws InvalidDataException {

		if (mtx1.getHeight() != mtx2.getHeight() || mtx1.getWidth() != mtx2.getWidth())
			throw new InvalidDataException("It is impossible to add matrices of different sizes");

		Matrix resultMatrix = new Matrix(mtx1.getWidth(), mtx1.getHeight());

		for (int i = 0; i < mtx1.getHeight(); i++)
			for (int j = 0; j < mtx1.getWidth(); j++)
				resultMatrix._matrix[i][j] = mtx1._matrix[i][j] + mtx2._matrix[i][j];

		return resultMatrix;
	}

	public final double getItem(int i, int j)
	{
		return _matrix[i][j];
	}

	public final void printToConsole()
	{
		for (int i = 0; i < getHeight(); i++)
		{
			for (int j = 0; j < getWidth(); j++)
				System.out.print(String.format("%1$s ", _matrix[i][j]));

			System.out.println();
		}
	}

	public final String toString()
	{
		String result = "[";

		for (int i = 0; i < getHeight(); i++)
		{
			result += "[";
			for (int j = 0; j < getWidth(); j++)
			{
				result += Double.toString(_matrix[i][j]);
				if (j != getWidth() - 1)
					result+= ", ";
			}
			result+= i != getHeight() - 1 ? "], " : "]]";
		}
		return result;
	}

	private final void CopyMatrix(double[][] src, double[][] dst)
	{
		for (int i = 0; i < getHeight(); i++)
			for (int j = 0; j < getWidth(); j++)
				dst[i][j] = src[i][j];
	}

	private final static void ValidateInputParameters(int width, int height, double[] digits) throws InvalidDataException {
		if (digits == null || digits.length != width * height)
			throw new InvalidDataException("Passed digits count is incorrect with passed matrix size");

		if (width < 1 || height < 1)
			throw new InvalidDataException(String.format("Matrix width - %1$s and height - %2$s must be > 0", width, height));
	}
}