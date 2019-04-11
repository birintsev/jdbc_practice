package ua.edu.sumdu.nc.commands;

import ua.edu.sumdu.nc.dao.EmployeeDAO;

import java.sql.Connection;

public abstract class EmployeeCommand {

    protected EmployeeDAO employeeDAO;

    protected void registerDAO() {
        try {
            employeeDAO = EmployeeDAO.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void execute();
}
