package residuo_interfaz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL ="jdbc:mysql://localhost:3306/residuos";

    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}