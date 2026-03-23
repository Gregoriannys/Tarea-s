
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FormularioController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtCantidad;

    private MainController mainController;

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    @FXML
    private void guardar() {

        if (txtNombre.getText().isEmpty() ||
            txtCategoria.getText().isEmpty() ||
            txtPrecio.getText().isEmpty() ||
            txtCantidad.getText().isEmpty()) {

            new Alert(Alert.AlertType.WARNING, "Campos vacios").show();
            return;
        }

        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());

            Producto p = new Producto(
                    txtNombre.getText(),
                    txtCategoria.getText(),
                    precio,
                    cantidad
            );

            mainController.agregarDesdeFormulario(p);

            new Alert(Alert.AlertType.INFORMATION, "Producto agregado").show();

            cerrarVentana();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Precio o cantidad invalidos").show();
        }
    }

    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }
}