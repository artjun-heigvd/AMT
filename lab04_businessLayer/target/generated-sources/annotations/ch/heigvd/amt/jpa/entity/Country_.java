package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Country.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Country_ {

	public static final String COUNTRY = "country";
	public static final String ID = "id";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Country#country
	 **/
	public static volatile SingularAttribute<Country, String> country;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Country#id
	 **/
	public static volatile SingularAttribute<Country, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Country
	 **/
	public static volatile EntityType<Country> class_;

}

