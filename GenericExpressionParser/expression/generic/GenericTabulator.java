package expression.generic;

import expression.constParser.*;
import expression.exceptions.ParsingException;
import expression.TripleExpression;
import expression.exceptions.UnsupportedTypeException;
import expression.parser.ExpressionParser;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String data, int x1, int x2, int y1, int y2, int z1, int z2)
            throws ParsingException, UnsupportedTypeException {

        switch (mode) {
            case "i":
                return findTabulator(x1, x2, y1, y2, z1, z2, data, new IntegerConstParser());
            case "d":
                return findTabulator(x1, x2, y1, y2, z1, z2, data, new DoubleConstParser());
            case "bi":
                return findTabulator(x1, x2, y1, y2, z1, z2, data, new BigIntegerConstParser());
            case "u":
                return findTabulator(x1, x2, y1, y2, z1, z2, data, new UncheckedIntegerConstParser());
            case "s":
                return findTabulator(x1, x2, y1, y2, z1, z2, data, new ShortConstParser());
            case "l":
                return findTabulator(x1, x2, y1, y2, z1, z2, data, new LongConstParser());
            default:
                throw new UnsupportedTypeException(mode);
        }
    }

    private <T> Object[][][] findTabulator(int x1, int x2, int y1, int y2, int z1, int z2, String data,
                                           ConstParser<T> constParser) throws ParsingException {

        TripleExpression<T> expression = new ExpressionParser<>(constParser).parse(data);
        int sizeX = x2 - x1 + 1, sizeY = y2 - y1 + 1, sizeZ = z2 - z1 + 1;
        Object[][][] ans = new Object[sizeX][sizeY][sizeZ];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                for (int k = 0; k < sizeZ; k++) {
                    try {
                        ans[i][j][k] = expression.evaluate(
                                constParser.convert(x1 + i),
                                constParser.convert(y1 + j),
                                constParser.convert(z1 + k))
                                .getValue();
                    } catch (ArithmeticException e) {
                        ans[i][j][k] = null;
                    }
                }
            }
        }
        return ans;
    }
}
