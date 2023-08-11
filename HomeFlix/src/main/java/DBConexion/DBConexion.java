package DBConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConexion {

    protected Connection conexion;
    private final String DB_URL= "jdbc:mysql://localhost:3306/homeflix";
    private final String USER = "root";
    private final String PASSWORD = "toor";

    /**
     * La funcion permite establecer una conexion a la base de datos
     * @author Michael Ng
     * @throws SQLException en caso de que haya un error en el SQL
     */
    public void conectar() throws SQLException {
        try {
            conexion= DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * La funcion cerrar la conexion con la base de datos
     * @author Michael Ng
     * @throws SQLException en caso de que haya un error en el SQL
     */
    public void cerrar() throws SQLException {
        if(conexion != null){
            if(!conexion.isClosed()){
                conexion.close();
            }
        }
    }
}
