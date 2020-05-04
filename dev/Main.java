import java.sql.*;

public class Main {
    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:DB/example.db")){
            System.out.println("Open DB");

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Product WHERE barcode = 1");
            ResultSet res = pstmt.executeQuery();

            System.out.println("barcode: " + res.getInt("barcode") + " name: " + res.getString("name"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
