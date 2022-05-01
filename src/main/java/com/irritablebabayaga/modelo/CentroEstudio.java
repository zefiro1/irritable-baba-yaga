package com.irritablebabayaga.modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CentroEstudio {
    private IntegerProperty codigoCentroEstudio;
    private StringProperty nombreCentroEstudio;

    public CentroEstudio(Integer codigoCentroEstudio, String nombreCentroEstudio) {
        setCodigoCentroEstudio(codigoCentroEstudio);
        setNombreCentroEstudio(nombreCentroEstudio);
    }

    public int getCodigoCentroEstudio() {
        return codigoCentroEstudio.get();
    }

    public IntegerProperty codigoCentroEstudioProperty() {
        return codigoCentroEstudio;
    }

    public void setCodigoCentroEstudio(int codigoCentroEstudio) {
        this.codigoCentroEstudio = new SimpleIntegerProperty(codigoCentroEstudio);
    }

    public String getNombreCentroEstudio() {
        return nombreCentroEstudio.get();
    }

    public StringProperty nombreCentroEstudioProperty() {
        return nombreCentroEstudio;
    }

    public void setNombreCentroEstudio(String nombreCentroEstudio) {
        this.nombreCentroEstudio = new SimpleStringProperty(nombreCentroEstudio);
    }

    public static void llenarInformacion(Connection connection, ObservableList<CentroEstudio> lista) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery("""
                    SELECT codigo_centro, nombre_centro_estudios FROM tbl_centros_estudio
                    """);
            while (resultado.next()) {
                lista.add(new CentroEstudio(resultado.getInt("codigo_centro"), resultado.getString("nombre_centro_estudios")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     * @apiNote In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * The string output is not necessarily stable over time or across
     * JVM invocations.
     * @implSpec The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     */
    @Override
    public String toString() {
        return nombreCentroEstudio.get();
    }
}
