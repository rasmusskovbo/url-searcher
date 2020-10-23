import java.sql.*;
import java.util.ArrayList;

public class JDBCWriter {

    private Connection connection = null;

    public boolean setConnection () {
        final String url = "jdbc:mysql://localhost:3306/urlread";
        boolean isConnected = false;
        try {
            connection = DriverManager.getConnection(url, "rasmus", "x"); // TODO
            isConnected = true;
        } catch (SQLException e) {
            isConnected = false;
            System.out.println("Connection not made: "+ e.getMessage());
        }

        return isConnected;
    }

    public int writeLines(String aUrl, ArrayList<String> aList) {
        int res = 0;
        String insertString = "INSERT INTO urlreads(url, line) values (?, ?);";
        PreparedStatement preparedStatement;
        for (String line: aList) {
            try {
                preparedStatement = connection.prepareStatement(insertString);
                preparedStatement.setString(1, aUrl);
                preparedStatement.setString(2, line);
                int rowcount = preparedStatement.executeUpdate();
                res = res + rowcount;
            } catch (SQLException e) {
                System.out.println("Error on line insert: "+ e.getMessage());
            }
        }
        return res;
    }

    public int searchDB(String aURL, String searchParam) {
        String searchStr = "SELECT count(*) FROM urlreads WHERE url LIKE " + '"' + "%" + aURL + "%" + '"' + " AND line LIKE " + '"' + "%" + searchParam + "%" + '"';
        PreparedStatement preparedStatement;
        int result = -1; // Default -1 if no results found
        try {
            preparedStatement = connection.prepareStatement(searchStr);
            // System.out.println(searchStr);
            ResultSet resSet = preparedStatement.executeQuery(searchStr);
            if (resSet.next()) {
                String str = "" + resSet.getObject(1);
                result = Integer.parseInt(str);
                System.out.println("Total count: " + result);
            }
        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        }
        return result;
    }
}
