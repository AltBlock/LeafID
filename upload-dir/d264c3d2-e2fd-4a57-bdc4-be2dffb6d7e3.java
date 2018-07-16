package net.roseindia.dao;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
	
   //list is working as a database
   List<Employee> employees;

   public EmployeeDaoImpl(){
      employees = new ArrayList<Employee>();
      Employee employee1 = new Employee("Robert",0);
      Employee employee2 = new Employee("John",1);
      employees.add(employee1);
      employees.add(employee2);		
   }
   @Override
   public void deleteEmployee(Employee emp) {
      employees.remove(emp.getId());
      System.out.println("Employee: ID " + emp.getId() 
         +", deleted from database");
   }

   //retrieve list of Employees from the database
   @Override
   public List<Employee> getAllEmployee() {
      return employees;
   }

   @Override
   public Employee getEmployee(int id) {
      return employees.get(id);
   }

   @Override
   public void updateEmployee(Employee emp) {
      employees.get(emp.getId()).setName(emp.getName());
      System.out.println("Employee: ID " + emp.getId() 
         +", updated in the database");
   }
}
