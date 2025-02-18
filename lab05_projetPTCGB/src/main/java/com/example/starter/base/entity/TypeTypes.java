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
 * This class represents the custom type for the Pokemon type.
 * The custom type is used to map the Pokemon type to the database.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public class TypeTypes implements UserType<Type> {

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
    public Class<Type> returnedClass() {
        return Type.class;
    }

    /**
     * Compares two PokemonType objects.
     *
     * @param type1 the first PokemonType object
     * @param type2 the second PokemonType object
     * @return true if the PokemonType objects are equal, false otherwise
     */
    @Override
    public boolean equals(Type type1, Type type2) {
        return type1.equals(type2);
    }

    /**
     * Returns the hash code of the PokemonType object.
     *
     * @param type the PokemonType object
     * @return the hash code of the PokemonType object
     */
    @Override
    public int hashCode(Type type) {
        return type.hashCode();
    }

    /**
     * Retrieves the value of the specified column as a PokemonType object.
     *
     * @param rs the result set from which to retrieve the value
     * @param position the column position in the result set
     * @param session the Hibernate session
     * @param owner the entity that owns the association
     * @return a PokemonType object representing the value of the specified column
     * @throws SQLException if an SQL error occurs while retrieving the value
     */
    @Override
    public Type nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String value = rs.getString(position);
        return value != null ? Type.fromString(value) : null;
    }

    /**
     * Sets the value of the specified column as a PokemonType object.
     *
     * @param st the prepared statement in which to set the value
     * @param value the PokemonType object to set
     * @param index the column position in the prepared statement
     * @param session the Hibernate session
     * @throws SQLException if an SQL error occurs while setting the value
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Type value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            PGobject pgObj = new PGobject();
            pgObj.setType("pokemon_type");
            pgObj.setValue(value.toString());
            st.setObject(index, pgObj);
        }
    }

    /**
     * Returns a deep copy of the PokemonType object.
     *
     * @param type the PokemonType object to copy
     * @return a deep copy of the PokemonType object
     */
    @Override
    public Type deepCopy(Type type) {
        return type;
    }

    /**
     * Determines if the PokemonType object is mutable.
     *
     * @return false, as the PokemonType object is immutable
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     * Disassembles the PokemonType object into a serializable form.
     *
     * @param type the PokemonType object to disassemble
     * @return a serializable form of the PokemonType object
     */
    @Override
    public Serializable disassemble(Type type) {
        return type;
    }

    /**
     * Assembles the PokemonType object from its serializable form.
     *
     * @param cached the serializable form of the PokemonType object
     * @param owner the entity that owns the association
     * @return the PokemonType object
     */
    @Override
    public Type assemble(Serializable cached, Object owner) {
        return (Type) cached;
    }
}
