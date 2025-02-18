package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Address.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Address_ {

	public static final String ADDRESS = "address";
	public static final String ADDRESS2 = "address2";
	public static final String CITY = "city";
	public static final String PHONE = "phone";
	public static final String DISTRICT = "district";
	public static final String POSTAL_CODE = "postalCode";
	public static final String ID = "id";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Address#address
	 **/
	public static volatile SingularAttribute<Address, String> address;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Address#address2
	 **/
	public static volatile SingularAttribute<Address, String> address2;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Address#city
	 **/
	public static volatile SingularAttribute<Address, City> city;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Address#phone
	 **/
	public static volatile SingularAttribute<Address, String> phone;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Address#district
	 **/
	public static volatile SingularAttribute<Address, String> district;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Address#postalCode
	 **/
	public static volatile SingularAttribute<Address, String> postalCode;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Address#id
	 **/
	public static volatile SingularAttribute<Address, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Address
	 **/
	public static volatile EntityType<Address> class_;

}

