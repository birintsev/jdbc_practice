package ua.edu.sumdu.nc;

import ua.edu.sumdu.nc.entities.Employee;
import ua.edu.sumdu.nc.entities.exceptions.JDBCPracticeException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class EmployeeDAO {

    private static final Map<String, Class> pool = new HashMap<>();

    {
        pool.put("ua.edu.sumdu.nc.EmployeeDAOImpl", EmployeeDAOImpl.class);
    }

    public static EmployeeDAO getInstance() throws Exception {
        Exception e = null;
        Class clazz;
        for (Map.Entry<String, Class> classEntry : pool.entrySet()) {
            clazz = classEntry.getValue();
            try {
                return (EmployeeDAO) clazz.newInstance();
            } catch (Exception e1) {
                e = e1;
            }
        }
        throw e == null ? new NoSuchElementException() : new InvocationTargetException(e);
    }

    public abstract Collection<Employee> getAllEmployees() throws SQLException;
    public abstract void addEmployee(Employee employee) throws SQLException;
    public abstract void deleteEmployee(int empno) throws SQLException;
    public abstract Employee getEmployee(int empno) throws SQLException;


}
