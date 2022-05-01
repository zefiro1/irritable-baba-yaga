package com.irritablebabayaga.modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.*;


public class Alumno {

    private IntegerProperty codigoAlumno;
    private StringProperty nombre;
    private StringProperty apellido;
    private IntegerProperty edad;
    private StringProperty genero;
    private Date fechaIngreso;
    private CentroEstudio centroEstudio;
    private Carrera carrera;

    public Alumno(Integer codigoAlumno, String nombre, String apellido, Integer edad, String genero, Date fechaIngreso, CentroEstudio centroEstudio, Carrera carrera) {
        setCodigoAlumno(codigoAlumno);
        setNombre(nombre);
        setApellido(apellido);
        setEdad(edad);
        setGenero(genero);
        setFechaIngreso(fechaIngreso);
        setCentroEstudio(centroEstudio);
        setCarrera(carrera);
    }

    public int getCodigoAlumno() {
        return codigoAlumno.get();
    }

    public IntegerProperty codigoAlumnoProperty() {
        return codigoAlumno;
    }

    public void setCodigoAlumno(int codigoAlumno) {
        this.codigoAlumno = new SimpleIntegerProperty(codigoAlumno);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getApellido() {
        return apellido.get();
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = new SimpleStringProperty(apellido);
    }

    public int getEdad() {
        return edad.get();
    }

    public IntegerProperty edadProperty() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = new SimpleIntegerProperty(edad);
    }

    public String getGenero() {
        return genero.get();
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = new SimpleStringProperty(genero);
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public CentroEstudio getCentroEstudio() {
        return centroEstudio;
    }

    public void setCentroEstudio(CentroEstudio centroEstudio) {
        this.centroEstudio = centroEstudio;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public int guardarRegistro(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    INSERT INTO tbl_alumnos(nombre,apellido,edad,genero,fecha_ingreso,codigo_carrera, código_centro)
                    VALUES (?,?,?,?,?,?,?)
                    """);
            preparedStatement.setString(1,nombre.get());
            preparedStatement.setString(2,apellido.get());
            preparedStatement.setInt(3,edad.get());
            preparedStatement.setString(4,genero.get());
            preparedStatement.setDate(5,fechaIngreso);
            preparedStatement.setInt(6,carrera.getCodigoCarrera());
            preparedStatement.setInt(7,centroEstudio.getCodigoCentroEstudio());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public int actualizarRegistro(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    UPDATE tbl_alumnos
                        SET nombre = ? ,
                            apellido = ?,
                            edad = ?,
                            genero = ?,
                            fecha_ingreso = ?,
                            codigo_carrera = ?,
                            código_centro = ?
                        WHERE 
                            codigo_alumno = ?
                    """);
            preparedStatement.setString(1,nombre.get());
            preparedStatement.setString(2,apellido.get());
            preparedStatement.setInt(3,edad.get());
            preparedStatement.setString(4,genero.get());
            preparedStatement.setDate(5,fechaIngreso);
            preparedStatement.setInt(6,carrera.getCodigoCarrera());
            preparedStatement.setInt(7,centroEstudio.getCodigoCentroEstudio());
            preparedStatement.setInt(8,codigoAlumno.get());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public int eliminarRegistro(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    DELETE FROM tbl_alumnos
                        WHERE 
                            codigo_alumno = ?
                    """);
            preparedStatement.setInt(1,codigoAlumno.get());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }


    }

    public static void llenarInformacion(Connection connection, ObservableList<Alumno> lista) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery("""
                    SELECT A.codigo_alumno, A.nombre , A.apellido , A.edad , A.genero , A.código_centro , A.codigo_carrera
                    , A.fecha_ingreso, B.nombre_carrera,  B.cantidad_asignaturas , C.nombre_centro_estudios
                    FROM tbl_alumnos A
                    INNER JOIN tbl_carreras B
                    ON (A.codigo_carrera  = B.codigo_carrera)
                    INNER JOIN  tbl_centros_estudio C
                    ON (A.código_centro = C.codigo_centro );
                    """);
            while (resultado.next()) {
                lista.add(new Alumno(resultado.getInt("codigo_alumno"), resultado.getString("nombre"), resultado.getString("apellido"), resultado.getInt("edad"), resultado.getString("genero"), resultado.getDate("fecha_ingreso"), new CentroEstudio(resultado.getInt("código_centro"), resultado.getString("nombre_centro_estudios")), new Carrera(resultado.getInt("codigo_carrera"), resultado.getString("nombre_carrera"), resultado.getInt("cantidad_asignaturas"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
