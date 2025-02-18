package ch.heigvd.amt.jpa.entity;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This class represents the custom type for the MPAA rating.
 * The custom type is used to map the MPAA rating to the database.
 *
 * @author Arthur Junod
 * @author Edwin Häffner
 * @author Eva Ray
 * @author Rachel Tranchida
 */

public class RatingType implements UserType<Rating> {

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
    public Class<Rating> returnedClass() {
        return Rating.class;
    }

    /**
     * Compares two Rating objects.
     *
     * @param rating the first Rating object
     * @param j1     the second Rating object
     * @return true if the Rating objects are equal, false otherwise
     */
    @Override
    public boolean equals(Rating rating, Rating j1) {
        return rating.equals(j1);
    }

    /**
     * Returns the hash code of the Rating object.
     *
     * @param rating the Rating object
     * @return the hash code of the Rating object
     */
    @Override
    public int hashCode(Rating rating) {
        return rating.hashCode();
    }

    /**
     * Retrieves the value of the specified column as a Rating object.
     * This method is called by Hibernate to extract the value from the result set.
     *
     * @param rs       the result set from which to retrieve the value
     * @param position the column position in the result set
     * @param session  the Hibernate session
     * @param owner    the entity that owns the association
     * @return a Rating object representing the value of the specified column, or null if the column value is SQL NULL
     * @throws SQLException if an SQL error occurs while retrieving the value
     */
    @Override
    public Rating nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String value = rs.getString(position);
        return value != null ? Rating.fromString(value) : null;
    }

    /**
     * Sets the value of the specified column as a Rating object.
     * This method is called by Hibernate to set the value in the prepared statement.
     *
     * @param st                               the prepared statement in which to set the value
     * @param value                            the Rating object to set
     * @param index                            the column position in the prepared statement
     * @param sharedSessionContractImplementor the Hibernate session
     * @throws SQLException if an SQL error occurs while setting the value
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Rating value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            PGobject pgObj = new PGobject();
            pgObj.setType("mpaa_rating");
            pgObj.setValue(value.toString());
            st.setObject(index, pgObj);
        }
    }

    /**
     * Returns a deep copy of the Rating object.
     *
     * @param rating the Rating object to copy
     * @return a deep copy of the Rating object
     */
    @Override
    public Rating deepCopy(Rating rating) {
        return rating;
    }

    /**
     * Determines if the Rating object is mutable.
     *
     * @return false, as the Rating object is immutable
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     * Disassembles the Rating object into a serializable form.
     * This method is called by Hibernate when caching the Rating object.
     *
     * @param rating the Rating object to disassemble
     * @return a serializable form of the Rating object
     */
    @Override
    public Serializable disassemble(Rating rating) {
        return rating;
    }

    /**
     * Assembles the Rating object from its serializable form.
     * This method is called by Hibernate when retrieving the Rating object from the cache.
     *
     * @param cached the serializable form of the Rating object
     * @param owner  the entity that owns the association
     * @return the Rating object
     */
    @Override
    public Rating assemble(Serializable cached, Object owner) {
        return (Rating) cached;
    }


}