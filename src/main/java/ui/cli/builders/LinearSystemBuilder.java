package builders;

import exceptions.DataException;
import model.Matrix;
import java.math.BigDecimal;
import java.util.Scanner;

public class LinearSystemBuilder implements Builder<LinearSystemBuilder> {
    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public Matrix build(int n) throws DataException {
        final BigDecimal[][] matrix = new BigDecimal[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("\033[1A\033[K");
                System.out.println("x\u2081 * ");
                final String s = SCANNER.nextLine();
                try {
                    
                }
            }
            for (int j = 0; j < n; j++) {
                System.out.print(String.format("x%d * %f", j + 1, matrix[i][j].doubleValue()));
            }
        }
    }
}
