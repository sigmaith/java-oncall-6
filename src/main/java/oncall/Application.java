package oncall;

import oncall.controller.AssignController;
import oncall.view.InputView;
import oncall.view.OutputView;

public class Application {
    public static void main(String[] args) {
        AssignController assignController = new AssignController(new InputView(), new OutputView());
        assignController.run();
    }
}
