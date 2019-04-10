package ua.edu.sumdu.nc;

import ua.edu.sumdu.nc.entities.Employee;
import ua.edu.sumdu.nc.entities.exceptions.JDBCPracticeException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDAOImpl extends EmployeeDAO {

    private File xmlConfig;

    public EmployeeDAOImpl(File xmlConfig) {
        this.xmlConfig = xmlConfig;
    }

    private Connection connect() throws JDBCPracticeException, SQLException {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            return DriverManager.getConnection(
                        xPath.evaluate("/datasources/datasource/connection-url", xmlConfig)
                        , xPath.evaluate("/datasources/datasource/user-name", xmlConfig)
                        , xPath.evaluate("/datasources/datasource/password", xmlConfig));
        } catch (XPathExpressionException e) {
            throw new JDBCPracticeException(e);
        }
    }

    @Override
    public Collection<Employee> getAllEmployees() throws JDBCPracticeException {

    }

    @Override
    public void addEmployee(Employee employee) throws JDBCPracticeException {

    }

    @Override
    public void deleteEmployee(int empno) throws SQLException {
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement
        }
    }

    @Override
    public Employee getEmployee(int empno) throws JDBCPracticeException {
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM emp WHERE empno = ?");
            preparedStatement.setInt(0, empno);
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseEmployees(resultSet).get(0);
        } catch (SQLException e) {
            throw new JDBCPracticeException(e);
        }
    }

    private List<Employee> parseEmployees(ResultSet resultSet) {
        List<Employee> list = new LinkedList<>();
        try {
            while (resultSet.next()) {
                list.add(
                        new Employee(
                                resultSet.getString("firstname"),
                                resultSet.getString("lastname"),
                                resultSet.getString("job"),
                                resultSet.getInt("depno"),
                                resultSet.getInt("mgr"),
                                resultSet.getDouble("sal"),
                                resultSet.getDouble("comm")));
            }
        } catch (SQLException e) {
            throw new JDBCPracticeException(e);
        }
        return list;
    }


}
