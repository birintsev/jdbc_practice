package ua.edu.sumdu.nc.commands;

import ua.edu.sumdu.nc.dao.EmployeeDAO;

import java.sql.SQLException;

public class DeleteEmployee extends EmployeeCommand {

    private int empno;

    public DeleteEmployee(int empno) {
        this.empno = empno;
    }

    @Override
    public void execute() {
        registerDAO();
        try {
            employeeDAO.deleteEmployee(empno);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}