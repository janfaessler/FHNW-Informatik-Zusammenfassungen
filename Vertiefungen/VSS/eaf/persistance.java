<persistence>
	<persistence-unit name="movierental"
			transaction-type="RESOURCE_LOCAL">
		<class>ch.fhnw.edu.rental.model.Movie</class>
		...
		<properties>
		<property name="hibernate.connection.driver_class"
			value="org.hsqldb.jdbcDriver" />
		<property name="hibernate.connection.url"
			value="jdbc:hsqldb:hsql://localhost/lab-db" />
		<property name="hibernate.connection.username"
			value="sa" />
		<property name="hibernate.connection.password"
			value="" />
		</properties>
	</persistence-unit>
</persistence>