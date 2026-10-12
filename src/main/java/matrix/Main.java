package matrix;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nВыберите операцию:");
            System.out.println("1. Сложение матриц");
            System.out.println("2. Умножение матриц");
            System.out.println("3. Деление матриц (A * B^-1)");
            System.out.println("4. Транспонирование");
            System.out.println("5. Вычислить определитель");
            System.out.println("0. Выход");
            System.out.print("Ввод: ");

            int choice = scanner.nextInt();
            if (choice == 0) break;

            try {
                if (choice == 4 || choice == 5) {
                    System.out.println("--- Ввод матрицы ---");
                    ComplexMatrix m = readMatrix(scanner);
                    if (choice == 4) {
                        System.out.println("Результат транспонирования:");
                        m.transpose().print();
                    } else {
                        System.out.println("Определитель: " + m.determinant());
                    }
                } else if (choice >= 1 && choice <= 3) {
                    System.out.println("--- Ввод первой матрицы ---");
                    ComplexMatrix m1 = readMatrix(scanner);
                    System.out.println("--- Ввод второй матрицы ---");
                    ComplexMatrix m2 = readMatrix(scanner);

                    System.out.println("Результат:");
                    if (choice == 1) m1.add(m2).print();
                    if (choice == 2) m1.multiply(m2).print();
                    if (choice == 3) m1.divide(m2).print();
                }
            } catch (Exception e) {
                System.out.println("Ошибка при выполнении операции: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static ComplexMatrix readMatrix(Scanner scanner) {
        System.out.print("Количество строк: ");
        int rows = scanner.nextInt();
        System.out.print("Количество столбцов: ");
        int cols = scanner.nextInt();

        ComplexMatrix matrix = new ComplexMatrix(rows, cols);
        System.out.println("Вводите элементы матрицы (действительная часть [пробел] мнимая часть):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Элемент [" + i + "][" + j + "]: ");
                double re = scanner.nextDouble();
                double im = scanner.nextDouble();
                matrix.set(i, j, new ComplexNumber(re, im));
            }
        }
        return matrix;
    }
}