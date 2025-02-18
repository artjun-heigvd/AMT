package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Role_ {

	public static final String ROLE = "role";
	public static final String ID = "id";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Role#role
	 **/
	public static volatile SingularAttribute<Role, String> role;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Role#id
	 **/
	public static volatile SingularAttribute<Role, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Role
	 **/
	public static volatile EntityType<Role> class_;

}

