package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Actor.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Actor_ {

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String ID = "id";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Actor#firstName
	 **/
	public static volatile SingularAttribute<Actor, String> firstName;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Actor#lastName
	 **/
	public static volatile SingularAttribute<Actor, String> lastName;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Actor#id
	 **/
	public static volatile SingularAttribute<Actor, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Actor
	 **/
	public static volatile EntityType<Actor> class_;

}

