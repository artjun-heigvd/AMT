package ch.heigvd.amt.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.time.Instant;

@StaticMetamodel(Payment.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Payment_ {

	public static final String AMOUNT = "amount";
	public static final String STAFF = "staff";
	public static final String ID = "id";
	public static final String PAYMENT_DATE = "paymentDate";
	public static final String CUSTOMER = "customer";
	public static final String RENTAL = "rental";

	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Payment#amount
	 **/
	public static volatile SingularAttribute<Payment, BigDecimal> amount;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Payment#staff
	 **/
	public static volatile SingularAttribute<Payment, Staff> staff;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Payment#id
	 **/
	public static volatile SingularAttribute<Payment, Integer> id;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Payment#paymentDate
	 **/
	public static volatile SingularAttribute<Payment, Instant> paymentDate;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Payment
	 **/
	public static volatile EntityType<Payment> class_;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Payment#customer
	 **/
	public static volatile SingularAttribute<Payment, Customer> customer;
	
	/**
	 * @see ch.heigvd.amt.jpa.entity.Payment#rental
	 **/
	public static volatile SingularAttribute<Payment, Rental> rental;

}

