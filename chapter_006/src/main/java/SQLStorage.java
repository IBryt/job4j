
import org.slf4j.*;
import java.sql.*;

import static java.lang.String.format;

public class SQLStorage {
    private static final Logger log = LoggerFactory.getLogger(SQLStorage.class);

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/java_a_from_z";
        String username = "postgres";
        String password = "password";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
//            PreparedStatement st = conn.prepareStatement("select\n" +
//                    "\tc.name as car,\n" +
//                    "\tb.name as body,\n" +
//                    "\te.name as engine,\n" +
//                    "\tt.name as transmission \n" +
//                    "from car as c\n" +
//                    "\tleft outer join car_body as b \n" +
//                    "\t\ton b.id = c.car_body_id\n" +
//                    "\tleft outer join engine as e \n" +
//                    "\t\ton e.id = c.engine_id\n" +
//                    "\tleft outer join transmission as t\n" +
//                    "\t\ton  t.id = c.transmission_id where c.name in(?, ?)");
//            st.setString(1,"honda");
//            st.setString(2,"toyota");
//            ResultSet rs = st.executeQuery();
//            while (rs.next()) {
//                System.out.println(format("%s %s %s %s",
//                        rs.getString("car"),
//                        rs.getString("body"),
//                        rs.getString("engine"),
//                        rs.getString("transmission"))
//                );
//            }
            PreparedStatement st = conn.prepareStatement("INSERT INTO transmission(name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, "qwe transmission2");
            st.executeUpdate();
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println(generatedKeys.getInt(1));
                }
            }
            st.close();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
