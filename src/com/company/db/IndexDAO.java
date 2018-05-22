package com.company.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class IndexDAO {


    public static void persistIndex(Map<String, List<String>> index)
    {
        Connection conn = null;
        boolean wasError = false;
        try {

            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
            conn.setAutoCommit(false);

            String sql = "INSERT INTO INDEX (WORD, LINK) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);

            for (String word : index.keySet())
            {
                for (String link : index.get(word))
                {
                    st.setString(1, word);
                    st.setString(2,link);
                    st.executeUpdate();
                }
            }

        } catch (SQLException ex) {
            wasError = true;
            ex.printStackTrace();

            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //System.out.println(ex.getErrorCode() + ex.getSQLState());

        }

        finally {
            if (conn != null)
            {
                try {
                    if (wasError==false)
                        conn.commit();
                    conn.close();
                } catch (SQLException ignore) {

                }
            }
        }


    }

}
