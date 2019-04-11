package ua.edu.sumdu.nc.commands;

import ua.edu.sumdu.nc.entities.Employee;

import java.sql.SQLException;

public class PrintAllEmployees extends EmployeeCommand {
    @Override
    public void execute() {
        registerDAO();
        try {
            for (Employee employee : employeeDAO.getAllEmployees()) {
                System.out.println(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}