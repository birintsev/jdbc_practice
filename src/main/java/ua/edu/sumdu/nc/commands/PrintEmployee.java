package ua.edu.sumdu.nc.commands;

import ua.edu.sumdu.nc.dao.EmployeeDAO;

public class PrintEmployee extends EmployeeCommand {

    private int empno;

    public PrintEmployee(int empno) {
        this.empno = empno;
    }

    @Override
    public void execute() {
        registerDAO();
        try {
            employeeDAO.getEmployee(empno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
