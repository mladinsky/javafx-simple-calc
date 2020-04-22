package calc;

public class CalcController {
    private StringBuilder leftValue = new StringBuilder("0");
    private StringBuilder rightValue = new StringBuilder();
    private char opValue;
    private String Error = null;

    // TODO: Баг: При добавлении минуса к исходному нулю и дальнейшем добавлении других цифр, этот ноль не удаляется
    void AppendDigit(int digit) {
        if (Error != null) return;

        if (opValue == 0) {
            if (leftValue.charAt(0) == '0' && leftValue.indexOf(".") == -1) {
                if (digit == 0) return;
                leftValue.deleteCharAt(0);
            }
            leftValue.append(digit);
        } else {
            if (rightValue.length() > 0 && rightValue.charAt(0) == '0' && rightValue.indexOf(".") == -1) {
                if (digit == 0) return;
                rightValue.deleteCharAt(0);
            }
            rightValue.append(digit);
        }
    }

    void SetOperand(char operand) {
        if (Error != null) return;

        if (operand != '=' && rightValue.length() == 0) {
            opValue = operand;
        } else {
            MakeBinarySolve();
            opValue = operand == '=' ? 0 : operand;
        }
    }

    String GetScreenText() {
        if (Error != null) {
            return "Ошибка: " + Error;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(leftValue);

        if (opValue != 0) {
            sb.append(' ').append(opValue).append(' ');

            if (rightValue != null) {
                sb.append(rightValue);
            }
        }

        return sb.toString();
    }

    void Reset() {
        leftValue.delete(0, leftValue.length());
        leftValue.append(0);

        rightValue.delete(0, rightValue.length());

        opValue = 0;

        Error = null;
    }

    void RemoveDigit() {
        if (Error != null) return;

        if (opValue == 0) {
            leftValue.deleteCharAt(leftValue.length() - 1);
            if (leftValue.length() == 0) {
                leftValue.append(0);
            }
        } else if (rightValue.length() == 0) {
            opValue = 0;
        } else {
            rightValue.deleteCharAt(rightValue.length() - 1);
        }
    }

    void PlusMinus() {
        if (Error != null) return;

        if (rightValue.length() == 0) {
            if (leftValue.charAt(0) == '-') {
                leftValue.deleteCharAt(0);
            } else {
                leftValue.insert(0, '-');
            }
        } else {
            if (rightValue.charAt(0) == '-') {
                rightValue.deleteCharAt(0);
            } else {
                rightValue.insert(0, '-');
            }
        }
    }

    void MakeBinarySolve() {
        if (Error != null) return;

        if (opValue == 0 || rightValue.length() == 0) return;

        double resVal = 0;

        try {
            double lVal = Double.parseDouble(leftValue.toString());
            double rVal = Double.parseDouble(rightValue.toString());

            switch (opValue) {
                case '+':
                    resVal = lVal + rVal;
                    break;
                case '-':
                    resVal = lVal - rVal;
                    break;
                case '*':
                    resVal = lVal * rVal;
                    break;
                case '/':
                    resVal = lVal / rVal;
                    break;
                case '^':
                    resVal = Math.pow(lVal, rVal);
                    break;
            }
        } catch (Exception ex) {
            Reset();
            Error = ex.getMessage();
            return;
        }

        writeSolveAnswer(resVal);
    }

    void MakeUnarySolve(String op) {
        if (Error != null) return;

        if (rightValue.length() != 0) {
            Reset();
            Error = "Выбрана унарная операция";
            return;
        }

        double resVal = 0;

        try {
            double lVal = Double.parseDouble(leftValue.toString());

            switch (op) {
                case "sin":
                    resVal = Math.sin(lVal);
                    break;
                case "cos":
                    resVal = Math.cos(lVal);
                    break;
                case "sinh":
                    resVal = Math.sinh(lVal);
                    break;
                case "cosh":
                    resVal = Math.cosh(lVal);
                    break;
                case "tan":
                    resVal = Math.tan(lVal);
                    break;
            }
        } catch (Exception ex) {
            Reset();
            Error = ex.getMessage();
            return;
        }

        writeSolveAnswer(resVal);
    }

    private void writeSolveAnswer(Double value) {
        if (value.isNaN()) {
            Reset();
            Error = "NaN";
            return;
        }

        if (value.isInfinite()) {
            Reset();
            Error = "Infinite";
            return;
        }

        leftValue.delete(0, leftValue.length());
        leftValue.append(value);

        rightValue.delete(0, rightValue.length());
    }

    void AddDot() {
        if (Error != null) return;

        if (rightValue.length() == 0) {
            if (leftValue.indexOf(".") != -1) return;
            leftValue.append('.');
        } else {
            if (rightValue.indexOf(".") != -1) return;
            if (rightValue.length() == 0) {
                rightValue.append(0);
            }
            rightValue.append('.');
        }
    }
}
