package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Inventory.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Inventory_ {

	public static final String ID = "id";
	public static final String FILM = "film";
	public static final String STORE = "store";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Inventory#id
	 **/
	public static volatile SingularAttribute<Inventory, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Inventory#film
	 **/
	public static volatile SingularAttribute<Inventory, Film> film;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Inventory#store
	 **/
	public static volatile SingularAttribute<Inventory, Store> store;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Inventory
	 **/
	public static volatile EntityType<Inventory> class_;

}

