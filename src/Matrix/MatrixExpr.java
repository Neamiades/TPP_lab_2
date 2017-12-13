package Matrix;

import com.sun.media.sound.InvalidDataException;

/**
 * Created by Neami on 08.12.2017.
 */
public interface MatrixExpr {
    public Matrix value() throws InvalidDataException;
}

class Substract implements MatrixExpr {
    private final Matrix left, right;

    public Substract(Matrix left, Matrix right) {
        this.left = left;
        this.right = right;
    }

    @Override public Matrix value() throws InvalidDataException {
        return Matrix.OpSubtraction(left, right);
    }
}

class Transopnent implements MatrixExpr {
    private final Matrix _mtx;

    public Transopnent(Matrix mtx) {
        _mtx = mtx;
    }

    @Override public Matrix value() throws InvalidDataException {
        return _mtx.GetTransponent();
    }
}

class Invers implements MatrixExpr {
    private final Matrix _mtx;

    public Invers(Matrix mtx) {
        _mtx = mtx;
    }

    @Override public Matrix value() throws InvalidDataException {
        return _mtx.GetReverse();
    }
}

class Multiply implements MatrixExpr {
    private final Matrix _mtx;

    private final Double _var;

    public Multiply(Matrix mtx, Double var) {
        _mtx = mtx;
        _var = var;
    }

    @Override public Matrix value() throws InvalidDataException {
        return _mtx.OpMultiply(_var, _mtx);
    }
}