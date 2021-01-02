import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class VisualizerGUI extends Application {

    private int cWidth = 500;
    private int cHeight = 500;
    private Canvas canvas = new Canvas(cWidth, cHeight);

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pathfinding Visualizer");

        // Code to test drawing on canvas in application
        canvas.getGraphicsContext2D().fillRect(0,0, 50, 50);
        canvas.getGraphicsContext2D().strokeRect(0,0, cWidth, cHeight);

        

        final HBox rootPanel = new HBox();
        rootPanel.setAlignment(Pos.CENTER);

        rootPanel.getChildren().add(canvas);

        primaryStage.setScene(new Scene(rootPanel, 1000, 500));
        primaryStage.show();
    }
}
