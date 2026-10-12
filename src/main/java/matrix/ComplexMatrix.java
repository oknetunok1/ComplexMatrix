package matrix;

public class ComplexMatrix {
    private final ComplexNumber[][] data;
    private final int rows;
    private final int cols;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new ComplexNumber[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = new ComplexNumber(0, 0);
            }
        }
    }

    public void set(int r, int c, ComplexNumber val) {
        data[r][c] = val;
    }

    public ComplexNumber get(int r, int c) {
        return data[r][c];
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Разные размеры матриц для сложения");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.data[i][j].add(other.data[i][j]));
            }
        }
        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Размерности матриц не совпадают для умножения");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                ComplexNumber sum = new ComplexNumber(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][k].mul(other.data[k][j]));
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, this.data[i][j]);
            }
        }
        return result;
    }

    public ComplexNumber determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Определитель существует только для квадратных матриц");
        }
        return calculateDeterminant(this.data, rows);
    }

    private ComplexNumber calculateDeterminant(ComplexNumber[][] m, int n) {
        if (n == 1) {
            return m[0][0];
        }
        if (n == 2) {
            // ad - bc
            return m[0][0].mul(m[1][1]).sub(m[0][1].mul(m[1][0]));
        }
        ComplexNumber det = new ComplexNumber(0, 0);
        ComplexNumber sign = new ComplexNumber(1, 0);
        ComplexNumber minusOne = new ComplexNumber(-1, 0);

        for (int f = 0; f < n; f++) {
            ComplexNumber[][] temp = new ComplexNumber[n - 1][n - 1];
            for (int i = 1; i < n; i++) {
                int colIndex = 0;
                for (int j = 0; j < n; j++) {
                    if (j == f) continue; // Пропускаем столбец
                    temp[i - 1][colIndex] = m[i][j];
                    colIndex++;
                }
            }
            ComplexNumber term = sign.mul(m[0][f]).mul(calculateDeterminant(temp, n - 1));
            det = det.add(term);
            sign = sign.mul(minusOne); // Чередование знака
        }
        return det;
    }

    public ComplexMatrix inverse() {
        ComplexNumber det = determinant();
        if (Math.abs(det.getRe()) < 1e-9 && Math.abs(det.getIm()) < 1e-9) {
            throw new ArithmeticException("Определитель равен нулю, обратной матрицы не существует");
        }

        ComplexMatrix adj = new ComplexMatrix(rows, cols);
        ComplexNumber minusOne = new ComplexNumber(-1, 0);

        // Поиск матрицы алгебраических дополнений
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ComplexNumber[][] temp = new ComplexNumber[rows - 1][cols - 1];
                int r = 0;
                for (int row = 0; row < rows; row++) {
                    if (row == i) continue;
                    int c = 0;
                    for (int col = 0; col < cols; col++) {
                        if (col == j) continue;
                        temp[r][c] = data[row][col];
                        c++;
                    }
                    r++;
                }
                ComplexNumber minorDet = calculateDeterminant(temp, rows - 1);
                ComplexNumber sign = (i + j) % 2 == 0 ? new ComplexNumber(1, 0) : minusOne;
                adj.set(i, j, sign.mul(minorDet));
            }
        }

        ComplexMatrix transposedAdj = adj.transpose();
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        ComplexNumber one = new ComplexNumber(1, 0);
        ComplexNumber detInv = one.div(det);

        // Умножение транспонированной матрицы на 1/det
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, transposedAdj.get(i, j).mul(detInv));
            }
        }
        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        return this.multiply(other.inverse());
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + "\t\t");
            }
            System.out.println();
        }
    }
}