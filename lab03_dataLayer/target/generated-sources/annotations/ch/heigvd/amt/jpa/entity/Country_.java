package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Country.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Country_ {

	public static final String ID = "id";
	public static final String COUNTRY_NAME = "countryName";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Country#id
	 **/
	public static volatile SingularAttribute<Country, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Country#countryName
	 **/
	public static volatile SingularAttribute<Country, String> countryName;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Country
	 **/
	public static volatile EntityType<Country> class_;

}

