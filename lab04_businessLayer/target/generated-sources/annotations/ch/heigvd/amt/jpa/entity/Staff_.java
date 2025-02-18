package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Staff.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Staff_ {

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PASSWORD = "password";
	public static final String ADDRESS = "address";
	public static final String ROLES = "roles";
	public static final String ACTIVE = "active";
	public static final String ID = "id";
	public static final String STORE = "store";
	public static final String PICTURE = "picture";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#firstName
	 **/
	public static volatile SingularAttribute<Staff, String> firstName;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#lastName
	 **/
	public static volatile SingularAttribute<Staff, String> lastName;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#password
	 **/
	public static volatile SingularAttribute<Staff, String> password;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#address
	 **/
	public static volatile SingularAttribute<Staff, Address> address;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#roles
	 **/
	public static volatile SetAttribute<Staff, Role> roles;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#active
	 **/
	public static volatile SingularAttribute<Staff, Boolean> active;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#id
	 **/
	public static volatile SingularAttribute<Staff, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#store
	 **/
	public static volatile SingularAttribute<Staff, Store> store;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff
	 **/
	public static volatile EntityType<Staff> class_;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#picture
	 **/
	public static volatile SingularAttribute<Staff, byte[]> picture;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#email
	 **/
	public static volatile SingularAttribute<Staff, String> email;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Staff#username
	 **/
	public static volatile SingularAttribute<Staff, String> username;

}

