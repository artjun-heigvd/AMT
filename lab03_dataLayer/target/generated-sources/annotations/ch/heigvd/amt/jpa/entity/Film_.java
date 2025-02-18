package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@StaticMetamodel(Film.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Film_ {

	public static final String RENTAL_RATE = "rentalRate";
	public static final String RENTAL_DURATION = "rentalDuration";
	public static final String LENGTH = "length";
	public static final String RATING = "rating";
	public static final String DESCRIPTION = "description";
	public static final String REPLACEMENT_COST = "replacementCost";
	public static final String LANGUAGE = "language";
	public static final String TITLE = "title";
	public static final String ORIGINAL_LANGUAGE = "originalLanguage";
	public static final String ACTORS = "actors";
	public static final String SPECIAL_FEATURES = "specialFeatures";
	public static final String ID = "id";
	public static final String CATEGORIES = "categories";
	public static final String RELEASE_YEAR = "releaseYear";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#rentalRate
	 **/
	public static volatile SingularAttribute<Film, BigDecimal> rentalRate;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#rentalDuration
	 **/
	public static volatile SingularAttribute<Film, Short> rentalDuration;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#length
	 **/
	public static volatile SingularAttribute<Film, Short> length;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#rating
	 **/
	public static volatile SingularAttribute<Film, Rating> rating;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#description
	 **/
	public static volatile SingularAttribute<Film, String> description;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#replacementCost
	 **/
	public static volatile SingularAttribute<Film, BigDecimal> replacementCost;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#language
	 **/
	public static volatile SingularAttribute<Film, Language> language;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#title
	 **/
	public static volatile SingularAttribute<Film, String> title;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#originalLanguage
	 **/
	public static volatile SingularAttribute<Film, Language> originalLanguage;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#actors
	 **/
	public static volatile SetAttribute<Film, Actor> actors;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#specialFeatures
	 **/
	public static volatile SingularAttribute<Film, String[]> specialFeatures;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#id
	 **/
	public static volatile SingularAttribute<Film, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#categories
	 **/
	public static volatile SetAttribute<Film, Category> categories;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film
	 **/
	public static volatile EntityType<Film> class_;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Film#releaseYear
	 **/
	public static volatile SingularAttribute<Film, Integer> releaseYear;

}

