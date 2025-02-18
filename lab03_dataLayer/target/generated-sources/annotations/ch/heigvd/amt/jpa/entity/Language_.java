package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Language.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Language_ {

	public static final String NAME = "name";
	public static final String QUERY_LANGUAGE_FIND_BY_NAME = "Language.findByName";
	public static final String ID = "id";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Language#name
	 **/
	public static volatile SingularAttribute<Language, String> name;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Language#id
	 **/
	public static volatile SingularAttribute<Language, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Language
	 **/
	public static volatile EntityType<Language> class_;

}

