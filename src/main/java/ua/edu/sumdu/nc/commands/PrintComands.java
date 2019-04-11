package ua.edu.sumdu.nc.commands;

public class PrintComands extends EmployeeCommand {
    @Override
    public void execute() {
        StringBuilder stringBuilder = new StringBuilder("<");
        for (int i = 0; i < jline.TerminalFactory.get().getWidth() - 2; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append(">");
        System.out.println(stringBuilder);
        System.out.println("-h\t-\thelp");
        System.out.println("-exit\t-\tfinish the work");
        System.out.println("-scan <xml file path>\t-\t(re) configure the xml");
        System.out.println('|' + stringBuilder.substring(1, stringBuilder.length() - 2) + '|');
        System.out.println("-sa\t-\tselect all the employees");
        System.out.println("-se <id>\t-\tselect the employee specified by id");
        System.out.println("-de <id>\t-\tdelete the employee specified by id");
        System.out.println("-ae " +
                "<name> " +
                "<job> " +
                "<department number> " +
                "<manager id> " +
                "<salary> " +
                "<commissions> " +
                "<hire date YYYY-MM-dd>\t" +
                "-\tdelete the employee specified by id");
        System.out.println('|' + stringBuilder.substring(1, stringBuilder.length() - 2) + '|');
        System.out.println(stringBuilder);
    }
}