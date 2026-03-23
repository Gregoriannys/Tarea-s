

import javafx.application.Platform;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;


import java.io.*;
import java.util.Optional;

public class MainController {

    @FXML private TableView<Producto> tabla;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, String> colCategoria;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colCantidad;

    @FXML private ProgressBar progressBar;
    @FXML private Label lblEstado;

    private ObservableList<Producto> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colCategoria.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCategoria()));
        colPrecio.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPrecio()));
        colCantidad.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getCantidad()));

        tabla.setItems(lista);

        cargarArchivo(); // carga automatica
    }

    //Botones

    @FXML
    private void agregarProducto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tarea2.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Nuevo Producto");

            FormularioController controller = loader.getController();
            controller.setMainController(this);

            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarDesdeFormulario(Producto p) {
        lista.add(p);
    }

    @FXML
    private void eliminarProducto() {
        Producto seleccionado = tabla.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar producto?");
            Optional<ButtonType> res = alert.showAndWait();

            if (res.isPresent() && res.get() == ButtonType.OK) {
                lista.remove(seleccionado);
            }
        }
    }

    @FXML
    private void limpiarLista() {
        lista.clear();
    }

    //Archivos

    @FXML
    private void guardarArchivo() {

        if (lista.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Lista vacía").show();
            return;
        }

        Thread hilo = new Thread(() -> {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("inventario.txt"))) {

                int total = lista.size();
                int i = 0;

                for (Producto p : lista) {

                    bw.write(p.getNombre() + "," + p.getCategoria() + "," +
                            p.getPrecio() + "," + p.getCantidad());
                    bw.newLine();

                    i++;
                    double progreso = (double) i / total;

                    Platform.runLater(() -> {
                        progressBar.setProgress(progreso);
                        lblEstado.setText("Guardando...");
                    });

                    Thread.sleep(200);
                }

                Platform.runLater(() ->
                        lblEstado.setText("Guardado correctamente"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        hilo.setDaemon(true);
        hilo.start();
    }

    @FXML
    private void cargarArchivo() {

        Thread hilo = new Thread(() -> {

            File file = new File("inventario.txt");

            if (!file.exists()) {
                Platform.runLater(() ->
                        lblEstado.setText("No se encontro inventario.txt"));
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                lista.clear();
                String linea;
                int count = 0;

                while ((linea = br.readLine()) != null) {

                    String[] datos = linea.split(",");

                    Producto p = new Producto(
                            datos[0],
                            datos[1],
                            Double.parseDouble(datos[2]),
                            Integer.parseInt(datos[3])
                    );

                    lista.add(p);

                    count++;
                    double progreso = count / 10.0;

                    Platform.runLater(() -> {
                        progressBar.setProgress(progreso);
                        lblEstado.setText("Cargando...");
                    });

                    Thread.sleep(200);
                }

                Platform.runLater(() ->
                        lblEstado.setText("Carga completa"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        hilo.setDaemon(true);
        hilo.start();
    }

    //Salir
    @FXML
    private void salirApp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Salir?");
        Optional<ButtonType> res = alert.showAndWait();

        if (res.isPresent() && res.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}