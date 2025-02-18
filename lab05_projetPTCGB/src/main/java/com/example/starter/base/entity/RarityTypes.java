package com.example.starter.base.entity;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This class represents the custom type for the Pokemon card rarity.
 * The custom type is used to map the Pokemon card rarity to the database.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public class RarityTypes implements UserType<Rarity> {

    /**
     * Returns the SQL type code for the custom type.
     *
     * @return the SQL type code for the custom type
     */
    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    /**
     * Returns the class of the custom type.
     *
     * @return the class of the custom type
     */
    @Override
    public Class<Rarity> returnedClass() {
        return Rarity.class;
    }

    /**
     * Compares two Rarity objects.
     *
     * @param rarity1 the first Rarity object
     * @param rarity2 the second Rarity object
     * @return true if the Rarity objects are equal, false otherwise
     */
    @Override
    public boolean equals(Rarity rarity1, Rarity rarity2) {
        return rarity1.equals(rarity2);
    }

    /**
     * Returns the hash code of the Rarity object.
     *
     * @param rarity the Rarity object
     * @return the hash code of the Rarity object
     */
    @Override
    public int hashCode(Rarity rarity) {
        return rarity.hashCode();
    }

    /**
     * Retrieves the value of the specified column as a Rarity object.
     *
     * @param rs       the result set from which to retrieve the value
     * @param position the column position in the result set
     * @param session  the Hibernate session
     * @param owner    the entity that owns the association
     * @return a Rarity object representing the value of the specified column
     * @throws SQLException if an SQL error occurs while retrieving the value
     */
    @Override
    public Rarity nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String value = rs.getString(position);
        return value != null ? Rarity.fromString(value) : null;
    }

    /**
     * Sets the value of the specified column as a Rarity object.
     *
     * @param st      the prepared statement in which to set the value
     * @param value   the Rarity object to set
     * @param index   the column position in the prepared statement
     * @param session the Hibernate session
     * @throws SQLException if an SQL error occurs while setting the value
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Rarity value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            PGobject pgObj = new PGobject();
            pgObj.setType("card_rarity");
            pgObj.setValue(value.toString());
            st.setObject(index, pgObj);
        }
    }

    /**
     * Returns a deep copy of the Rarity object.
     *
     * @param rarity the Rarity object to copy
     * @return a deep copy of the Rarity object
     */
    @Override
    public Rarity deepCopy(Rarity rarity) {
        return rarity;
    }

    /**
     * Determines if the Rarity object is mutable.
     *
     * @return false, as the Rarity object is immutable
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     * Disassembles the Rarity object into a serializable form.
     *
     * @param rarity the Rarity object to disassemble
     * @return a serializable form of the Rarity object
     */
    @Override
    public Serializable disassemble(Rarity rarity) {
        return rarity;
    }

    /**
     * Assembles the Rarity object from its serializable form.
     *
     * @param cached the serializable form of the Rarity object
     * @param owner  the entity that owns the association
     * @return the Rarity object
     */
    @Override
    public Rarity assemble(Serializable cached, Object owner) {
        return (Rarity) cached;
    }
}
