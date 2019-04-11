package ua.edu.sumdu.nc.dao;

import ua.edu.sumdu.nc.entities.Employee;
import ua.edu.sumdu.nc.entities.exceptions.JDBCPracticeException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDAOImpl extends EmployeeDAO {

    protected Connection connect() throws JDBCPracticeException, SQLException {
        return DriverManager.getConnection(
                System.getProperty("connection-url"),
                System.getProperty("user-name"),
                System.getProperty("password")
                );
    }

    @Override
    public Collection<Employee> getAllEmployees() throws JDBCPracticeException {
        try (Connection connection = connect()) {
            return parseEmployees(connection.prepareStatement("SELECT * FROM EMP").executeQuery());
        } catch (SQLException e) {
            throw new JDBCPracticeException(e);
        }
    }

    @Override
    public void addEmployee(Employee employee) throws JDBCPracticeException {
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO EMP (ename, job, mgr, hiredate, sal, comm, deptno) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1,employee.getEname());
            preparedStatement.setString(2,employee.getJob());
            preparedStatement.setInt(3,employee.getMgr());
            preparedStatement.setDate(4, Date.valueOf(employee.getHiredate()));
            preparedStatement.setDouble(5, employee.getSal());
            preparedStatement.setDouble(6, employee.getComm());
            preparedStatement.setInt(7, employee.getDepno());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new JDBCPracticeException(e);
        }
    }

    @Override
    public void deleteEmployee(int empno) throws SQLException {
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM EMP WHERE EMPNO = ?");
            preparedStatement.setInt(1, empno);
            preparedStatement.execute();
        }
    }

    @Override
    public Employee getEmployee(int empno) throws JDBCPracticeException {
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM emp WHERE empno = ?");
            preparedStatement.setInt(1, empno);
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
                                resultSet.getString("ename"),
                                resultSet.getString("job"),
                                resultSet.getInt("depno"),
                                resultSet.getInt("mgr"),
                                resultSet.getDouble("sal"),
                                resultSet.getDouble("comm"),
                                new java.sql.Date(resultSet.getDate("hiredate").getTime()).toLocalDate()));
            }
        } catch (SQLException e) {
            throw new JDBCPracticeException(e);
        }
        return list;
    }
}