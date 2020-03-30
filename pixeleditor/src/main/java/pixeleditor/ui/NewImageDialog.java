package pixeleditor.ui;

import javafx.geometry.Dimension2D;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NewImageDialog extends Dialog<Dimension2D> {

    public NewImageDialog(Stage primaryStage) {
        super();
        initOwner(primaryStage);
        
        this.setTitle("New image");
        this.setResizable(true);

        Label widthLabel = new Label("Width: ");
        Label heightLabel = new Label("Height: ");
        
        Spinner widthSpinner = new Spinner<Integer>();
        SpinnerValueFactory widthFactory = new IntegerSpinnerValueFactory(1, 2000, 100);
        widthSpinner.setValueFactory(widthFactory);
        widthSpinner.setEditable(true);
        
        Spinner heightSpinner = new Spinner<Integer>();
        SpinnerValueFactory heightFactory = new IntegerSpinnerValueFactory(1, 2000, 100);       
        heightSpinner.setValueFactory(heightFactory);
        heightSpinner.setEditable(true);

        GridPane grid = new GridPane();
        grid.add(widthLabel, 1, 1);
        grid.add(widthSpinner, 2, 1);
        grid.add(heightLabel, 1, 2);
        grid.add(heightSpinner, 2, 2);
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeCancel);

        setResultConverter(button -> {
            if (button.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                return new Dimension2D((Integer) widthSpinner.getValue(), (Integer) heightSpinner.getValue());
            } else {
                return null;
            }
        });
    }
}
