package lib;

import org.junit.*;
import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static junit.framework.Assert.assertEquals;

/**
 * Created by manalit on 1/25/14.
 */
public class OrderMgmtTest {
    static Connection connection;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        String url = "jdbc:mariadb://localhost:3306";
        connection = DriverManager.getConnection(url, "manalit", "sidhivinayak");
        Statement statement = connection.createStatement();
        String createCustomerQuery = "create schema orderMgmt";
        boolean result = statement.execute(createCustomerQuery);
        Statement statement1 = connection.createStatement();
        String createCustomerQuery1 = "CREATE TABLE orderMgmt.product (\n" +
                "\tprod_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "\tprod_name VARCHAR(30),\n" +
                "\tunit_price FLOAT\n" +
                ");";
        boolean result1 = statement1.execute(createCustomerQuery1);
        Statement statement2 = connection.createStatement();
        String createCustomerQuery2 = "CREATE TABLE orderMgmt.customer (\n" +
                "\tcust_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "\tcust_name VARCHAR(30),\n" +
                "\taddress VARCHAR(30),\n" +
                "\tcity VARCHAR(30),\n" +
                "\tstate VARCHAR(30),\n" +
                "\tcontact BIGINT\n" +
                ");";
        boolean result2 = statement2.execute(createCustomerQuery2);
        Statement statement3 = connection.createStatement();
        String createCustomerQuery3 = "CREATE TABLE orderMgmt.orderInfo (\n" +
                "\torder_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "\tcust_id INT,\n" +
                "\tdate_of_order DATETIME,\n" +
                "\tdelivery_date DATETIME \n" +
                ");";
        boolean result3 = statement3.execute(createCustomerQuery3);
        Statement statement4 = connection.createStatement();
        String createCustomerQuery4 = "ALTER TABLE orderMgmt.orderInfo \n" +
                "\tADD CONSTRAINT orderInfo_custID_fk FOREIGN KEY(cust_id)\n" +
                "\tREFERENCES customer(cust_id);";
        boolean result4 = statement4.execute(createCustomerQuery4);
        Statement statement5 = connection.createStatement();
        String createCustomerQuery5 = "CREATE TABLE orderMgmt.orderItems (\n" +
                "\torder_item_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "\torder_id INT,\n" +
                "\tprod_id INT,\n" +
                "\tquantity INT,\n" +
                "\titem_price FLOAT\n" +
                ");";
        boolean result5 = statement5.execute(createCustomerQuery5);
        Statement statement6 = connection.createStatement();
        String createCustomerQuery6 = "ALTER TABLE orderMgmt.orderItems \n" +
                "\tADD CONSTRAINT orderIems_orderId_fk FOREIGN KEY(order_id)\n" +
                "\tREFERENCES orderInfo(order_id);";
        boolean result6 = statement6.execute(createCustomerQuery6);
        Statement statement7 = connection.createStatement();
        String createCustomerQuery7 = "ALTER TABLE orderMgmt.orderItems \n" +
                "\tADD CONSTRAINT orderIems_prodId_fk FOREIGN KEY(prod_id)\n" +
                "\tREFERENCES product(prod_id);";
        boolean result7 = statement7.execute(createCustomerQuery7);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Statement statement = connection.createStatement();
        String createCustomerQuery = "drop schema orderMgmt;";
        boolean result = statement.execute(createCustomerQuery);

    }

    @Before
    public void setUp() throws Exception {


    }
    @After
    public void tearDown() throws Exception {



    }
    @Test
    public void testInsertQuery() throws Exception {
        Statement statement = connection.createStatement();
        String createCustomerQuery = "INSERT INTO orderMgmt.product(prod_name,unit_price) VALUES('Pen',10);";
        int result = statement.executeUpdate(createCustomerQuery);
        Statement statement1 = connection.createStatement();
        String createCustomerQuery1 = "SELECT * FROM orderMgmt.product;";
        ResultSet result1 = statement1.executeQuery(createCustomerQuery1);
        while (result1.next()) {
            assertEquals("Pen" , result1.getString(2));
            assertEquals(10 , result1.getInt(3));
        }
    }
    @Test
    public void testDeleteQuery() throws Exception {
        Statement statement = connection.createStatement();
        String createCustomerQuery3 = "delete from orderMgmt.product where prod_name = 'Pen';";
        int result3 = statement.executeUpdate(createCustomerQuery3);
        String createCustomerQuery4 = "SELECT * FROM orderMgmt.product;";
        ResultSet result4 = statement.executeQuery(createCustomerQuery4);
        assertEquals(0 , result4.getRow());
    }

}
