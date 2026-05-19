import com.telemetry.TelemetryApp;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        // Launch the JavaFX Application instead of the old Swing UI
        Application.launch(TelemetryApp.class, args);
    }
}