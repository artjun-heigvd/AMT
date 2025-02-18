package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.sql.Date;

@StaticMetamodel(Customer.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Customer_ {

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String ADDRESS = "address";
	public static final String ACTIVE = "active";
	public static final String ID = "id";
	public static final String STORE = "store";
	public static final String EMAIL = "email";
	public static final String CREATE_DATE = "createDate";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Customer#firstName
	 **/
	public static volatile SingularAttribute<Customer, String> firstName;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Customer#lastName
	 **/
	public static volatile SingularAttribute<Customer, String> lastName;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Customer#address
	 **/
	public static volatile SingularAttribute<Customer, Address> address;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Customer#active
	 **/
	public static volatile SingularAttribute<Customer, Integer> active;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Customer#id
	 **/
	public static volatile SingularAttribute<Customer, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Customer#store
	 **/
	public static volatile SingularAttribute<Customer, Store> store;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Customer
	 **/
	public static volatile EntityType<Customer> class_;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Customer#email
	 **/
	public static volatile SingularAttribute<Customer, String> email;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Customer#createDate
	 **/
	public static volatile SingularAttribute<Customer, Date> createDate;

}

