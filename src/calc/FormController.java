package calc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;


public class FormController {
    public TextArea calcScreen;
    private CalcController calcController = new CalcController();

    private void updateScreen() {
        calcScreen.setText(calcController.GetScreenText());
    }

    @FXML
    private void onClickDigit(ActionEvent event) {
        Button btn = (Button) event.getSource();

        calcController.AppendDigit(btn.getText().charAt(0) - '0');
        updateScreen();
    }

    @FXML
    private void onClickClear(ActionEvent event) {
        calcController.Reset();
        updateScreen();
    }

    @FXML
    private void onClickOperand(ActionEvent event) {
        Button btn = (Button) event.getSource();

        calcController.SetOperand(btn.getText().charAt(0));
        updateScreen();
    }

    @FXML
    private void onClickRemove(ActionEvent event) {
        calcController.RemoveDigit();
        updateScreen();
    }

    @FXML
    private void onClickPlusMinus(ActionEvent event) {
        calcController.PlusMinus();
        updateScreen();
    }

    @FXML
    private void onClickUnaryOperand(ActionEvent event) {
        Button btn = (Button) event.getSource();

        calcController.MakeUnarySolve(btn.getText());
        updateScreen();
    }

    @FXML
    private void onClickDot(ActionEvent event) {
        calcController.AddDot();
        updateScreen();
    }

    @FXML
    private void onKeyPress(KeyEvent event) {
        String text = event.getText();
        if (text.length() != 0 && Character.isDigit(text.charAt(0))) {
            calcController.AppendDigit(text.charAt(0) - '0');
            updateScreen();
            return;
        }

        switch (event.getCode()) {
            case MULTIPLY:
                calcController.SetOperand('*');
                break;
            case DIVIDE:
            case SLASH:
                calcController.SetOperand('/');
                break;
            case PLUS:
            case ADD:
                calcController.SetOperand('+');
                break;
            case MINUS:
            case SUBTRACT:
                calcController.SetOperand('-');
                break;
            case CIRCUMFLEX:
                calcController.SetOperand('^');
                break;
            case EQUALS:
            case ENTER:
                calcController.MakeBinarySolve();
                break;
            case DECIMAL:
                calcController.AddDot();
                break;
            case BACK_SPACE:
                calcController.RemoveDigit();
                break;
            default:
                return;
        }

        updateScreen();
    }
}
