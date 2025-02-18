package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Store.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Store_ {

	public static final String ADDRESS = "address";
	public static final String MANAGER = "manager";
	public static final String ID = "id";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Store#address
	 **/
	public static volatile SingularAttribute<Store, Address> address;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Store#manager
	 **/
	public static volatile SingularAttribute<Store, Staff> manager;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Store#id
	 **/
	public static volatile SingularAttribute<Store, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Store
	 **/
	public static volatile EntityType<Store> class_;

}

