package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.sql.Timestamp;

@StaticMetamodel(Rental.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Rental_ {

	public static final String RENTAL_DATE = "rentalDate";
	public static final String RETURN_DATE = "returnDate";
	public static final String STAFF = "staff";
	public static final String ID = "id";
	public static final String INVENTORY = "inventory";
	public static final String CUSTOMER = "customer";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Rental#rentalDate
	 **/
	public static volatile SingularAttribute<Rental, Timestamp> rentalDate;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Rental#returnDate
	 **/
	public static volatile SingularAttribute<Rental, Timestamp> returnDate;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Rental#staff
	 **/
	public static volatile SingularAttribute<Rental, Staff> staff;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Rental#id
	 **/
	public static volatile SingularAttribute<Rental, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Rental#inventory
	 **/
	public static volatile SingularAttribute<Rental, Inventory> inventory;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Rental
	 **/
	public static volatile EntityType<Rental> class_;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Rental#customer
	 **/
	public static volatile SingularAttribute<Rental, Customer> customer;

}

