package com.employee.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.employee.model.Employee;

public class EmployeeDAO {
	
	private String jdbcUrl="jdbc:mysql://localhost:3306/employee";
	private String jdbcUsername="root";
	private String jdbcPassword="root";
	
	private static final String INSERT_EMP_SQL="insert into emp(name,dept,salary) values(?,?,?);";
	private static final String SELECT_ALL_EMP="select * from emp";
	private static final String SELECT_EMP_BY_ID="select * from emp where id=?";
	private static final String DELETE_EMP_SQL="delete from emp where id=?";
	private static final String UPDATE_EMP_SQL="update emp set name=?,dept=?,salary=? where id=?";
	
	protected Connection getConnection() {
		Connection connection=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	//create emp
	public void inserEmployee(Employee employee) {
		Connection connection=getConnection();
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(INSERT_EMP_SQL);
			preparedStatement.setString(1, employee.getEmpName());
			preparedStatement.setString(2, employee.getEmpDept());
			preparedStatement.setDouble(3, employee.getEmpSal());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//delete emp
	public boolean deleteEmployee(int id) throws SQLException {
		boolean empDeleted;
		try(Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(DELETE_EMP_SQL);) {
			preparedStatement.setInt(1, id);
			empDeleted= preparedStatement.executeUpdate()>0;			
		} 
		return empDeleted;
	}
	
	//update emp
	public boolean updateEmployee(Employee employee) throws SQLException {
		boolean empUpdated;
		try(Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_EMP_SQL);) {
			preparedStatement.setString(1, employee.getEmpName());
			preparedStatement.setString(2, employee.getEmpDept());
			preparedStatement.setDouble(3, employee.getEmpSal());
			preparedStatement.setInt(4, employee.getEmpId());
			empUpdated=preparedStatement.executeUpdate()>0;
		} 
		return empUpdated;
	}
	//display single emp
	public Employee selectEmp(int id) throws SQLException {
		Employee employee=null;
		try(Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(SELECT_EMP_BY_ID);) {
			preparedStatement.setInt(1,id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				String name=resultSet.getString("name");
				String dept=resultSet.getString("dept");
				double salary=resultSet.getDouble("salary");
				employee = new Employee(id, name, dept, salary);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return employee;
	}
	
	//display all emp
	public List<Employee> selectAllEmp(){
		List<Employee> employees=new ArrayList<>();
		try(Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL_EMP);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				String dept=resultSet.getString("dept");
				double salary=resultSet.getDouble("salary");
				employees.add(new Employee(id, name, dept, salary));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return employees;
	}
}
