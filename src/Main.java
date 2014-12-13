import java.sql.*;
import java.util.List;

import domain.*;
import repositories.IRepository;
import repositories.IRepositoryCatalog;
import repositories.impl.DummyRepositoryCatalog;
import repositories.impl.RepositoryCatalog;
import repositories.impl.UserBuilder;
import repositories.impl.UserRepository;
import unitofwork.IUnitOfWork;
import unitofwork.UnitOfWork;


public class Main {

	public static void main(String[] args) {

		String url="jdbc:hsqldb:hsql://localhost/workdb";
		User jnowak = new User();
		jnowak.setLogin("aurbanow");
		jnowak.setPassword("qwerty");
		
		try {
			
			Connection connection = DriverManager.getConnection(url);
			IUnitOfWork uow = new UnitOfWork(connection);
			/*
			String createTableSql = 
					"CREATE TABLE users("
					+ "id int GENERATED BY DEFAULT AS IDENTITY,"
					+ "login VARCHAR(20),"
					+ "password VARCHAR(20)"
					+ ")";
			Statement createTable = connection.createStatement();
			createTable.executeUpdate(createTableSql);
			*/
			IRepositoryCatalog catalog = new RepositoryCatalog(connection, uow);
			
			
			catalog.getUsers().save(jnowak);
			
			List<User> usersFromDb= catalog.getUsers().getAll();
			
			for(User userFromDb: usersFromDb)
				System.out.println(userFromDb.getId()+" "+userFromDb.getLogin()+" "+userFromDb.getPassword());
			
			User u = catalog.getUsers().get(2);
			u.setPassword("1qaz2wsx");
			catalog.getUsers().update(u);
			catalog.getUsers().delete(usersFromDb.get(0));
		
			for(User userFromDb: catalog.getUsers().getAll())
				System.out.println(userFromDb.getId()+" "+userFromDb.getLogin()+" "+userFromDb.getPassword());
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("koniec");
	}

}
