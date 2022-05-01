package com.irritablebabayaga;

import com.irritablebabayaga.modelo.Alumno;
import com.irritablebabayaga.modelo.Carrera;
import com.irritablebabayaga.modelo.CentroEstudio;
import com.irritablebabayaga.modelo.Conexion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class FormularioAlumnosController implements Initializable {

    //Columnas
    @FXML
    private TableColumn<Alumno, String> clmnNombre;
    @FXML
    private TableColumn<Alumno, String> clmnApellido;
    @FXML
    private TableColumn<Alumno, Number> clmnEdad;
    @FXML
    private TableColumn<Alumno, String> clmnGenero;
    @FXML
    private TableColumn<Alumno, Date> clmnFechaIngreso;
    @FXML
    private TableColumn<Alumno, CentroEstudio> clmnCentroEstudio;
    @FXML
    private TableColumn<Alumno, Carrera> clmnCarrera;


    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtEdad;
    @FXML
    private RadioButton rbtFemenino;
    @FXML
    private RadioButton rbtMasculino;
    @FXML
    private DatePicker dtpkrFecha;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;

    @FXML
    private ComboBox<Carrera> cmbCarrerra;
    @FXML
    private ComboBox<CentroEstudio> cmbCentroEstudio;
    @FXML
    private TableView<Alumno> alumnoTableView;


    private ObservableList<Carrera> listaCarreras;
    private ObservableList<CentroEstudio> listaCentroEstudios;
    private ObservableList<Alumno> listaAlumnos;

    private Conexion conexion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        conexion = new Conexion();
        conexion.establecerConexion();

        listaCarreras = FXCollections.observableArrayList();
        listaCentroEstudios = FXCollections.observableArrayList();
        listaAlumnos = FXCollections.observableArrayList();

        Carrera.llenarInformacion(conexion.getConnection(), listaCarreras);
        CentroEstudio.llenarInformacion(conexion.getConnection(), listaCentroEstudios);
        Alumno.llenarInformacion(conexion.getConnection(), listaAlumnos);

        cmbCarrerra.setItems(listaCarreras);
        cmbCentroEstudio.setItems(listaCentroEstudios);

        alumnoTableView.setItems(listaAlumnos);

        clmnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        clmnEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        clmnGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        clmnFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
        clmnCentroEstudio.setCellValueFactory(new PropertyValueFactory<>("centroEstudio"));
        clmnCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));

        gestionarEventos();

        conexion.cerrarConexcion();


    }

    public void gestionarEventos() {
        alumnoTableView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Alumno>() {
                    @Override
                    public void changed(ObservableValue<? extends Alumno> observableValue, Alumno alumno, Alumno t1) {
                        if (t1 != null) {
                            txtCodigo.setText(String.valueOf(t1.getCodigoAlumno()));
                            txtNombre.setText(t1.getNombre());
                            txtApellido.setText(t1.getApellido());
                            txtEdad.setText(String.valueOf(t1.getEdad()));
                            if (t1.getGenero().equals("F")) {
                                rbtFemenino.setSelected(true);
                                rbtMasculino.setSelected(false);
                            }
                            if (t1.getGenero().equals("M")) {
                                rbtFemenino.setSelected(false);
                                rbtMasculino.setSelected(true);
                            }
                            dtpkrFecha.setValue(t1.getFechaIngreso().toLocalDate());
                            cmbCarrerra.setValue(t1.getCarrera());
                            cmbCentroEstudio.setValue(t1.getCentroEstudio());

                            btnGuardar.setDisable(true);
                            btnEliminar.setDisable(false);
                            btnActualizar.setDisable(false);
                        }
                    }
                }
        );

    }

    @FXML
    public void guardarRegistro(){
        Alumno a = new Alumno(0,
                txtNombre.getText(),
                txtApellido.getText(),
                Integer.valueOf(txtEdad.getText()),
                rbtFemenino.isSelected() ?"F":"M",
                java.sql.Date.valueOf(dtpkrFecha.getValue()),
                cmbCentroEstudio.getSelectionModel().getSelectedItem(),
                cmbCarrerra.getSelectionModel().getSelectedItem());
        conexion.establecerConexion();
        int resultado = a.guardarRegistro(conexion.getConnection());
        conexion.cerrarConexcion();

        if(resultado == 1){
            listaAlumnos.add(a);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setContentText("El registro ha sido agregado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }

    @FXML
    public void limpiarComponentes(){
        txtCodigo.setText(null);
        txtNombre.setText(null);
        txtApellido.setText(null);
        txtEdad.setText(null);
        rbtFemenino.setSelected(false);
        rbtMasculino.setSelected(false);
        dtpkrFecha.setValue(null);
        cmbCarrerra.setValue(null);
        cmbCentroEstudio.setValue(null);

        btnGuardar.setDisable(false);
        btnEliminar.setDisable(true);
        btnActualizar.setDisable(true);
    }

    @FXML
    public  void actualizarRegistro(){
        Alumno a = new Alumno(
                Integer.valueOf(txtCodigo.getText()),
                txtNombre.getText(),
                txtApellido.getText(),
                Integer.valueOf(txtEdad.getText()),
                rbtFemenino.isSelected() ?"F":"M",
                java.sql.Date.valueOf(dtpkrFecha.getValue()),
                cmbCentroEstudio.getSelectionModel().getSelectedItem(),
                cmbCarrerra.getSelectionModel().getSelectedItem());
        conexion.establecerConexion();
        int  resultado = a.actualizarRegistro(conexion.getConnection());
        conexion.cerrarConexcion();

        if(resultado == 1){
            listaAlumnos.set(alumnoTableView.getSelectionModel().getSelectedIndex(),a);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setContentText("El registro ha sido actualizado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }

    @FXML
    public void eliminarRegistro(){
        conexion.establecerConexion();
        int resultado = alumnoTableView.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexcion();
        if(resultado == 1){
            listaAlumnos.remove(alumnoTableView.getSelectionModel().getSelectedIndex());
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }
}