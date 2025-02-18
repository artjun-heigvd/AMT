package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(City.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class City_ {

	public static final String COUNTRY = "country";
	public static final String CITY = "city";
	public static final String ID = "id";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.City#country
	 **/
	public static volatile SingularAttribute<City, Country> country;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.City#city
	 **/
	public static volatile SingularAttribute<City, String> city;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.City#id
	 **/
	public static volatile SingularAttribute<City, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.City
	 **/
	public static volatile EntityType<City> class_;

}

