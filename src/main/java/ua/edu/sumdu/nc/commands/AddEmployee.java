package ua.edu.sumdu.nc.commands;

import ua.edu.sumdu.nc.entities.Employee;

import java.sql.SQLException;

public class AddEmployee extends EmployeeCommand {

    private Employee employee;

    public AddEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void execute() {
        registerDAO();
        try {
            employeeDAO.addEmployee(employee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}