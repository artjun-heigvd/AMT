package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Category.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Category_ {

	public static final String FILMS = "films";
	public static final String NAME = "name";
	public static final String ID = "id";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Category#films
	 **/
	public static volatile SetAttribute<Category, Film> films;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Category#name
	 **/
	public static volatile SingularAttribute<Category, String> name;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Category#id
	 **/
	public static volatile SingularAttribute<Category, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Category
	 **/
	public static volatile EntityType<Category> class_;

}

