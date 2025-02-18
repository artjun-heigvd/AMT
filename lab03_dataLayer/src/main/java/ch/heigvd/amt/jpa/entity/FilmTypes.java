package ch.heigvd.amt.jpa.entity;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.Arrays;

/**
 * Custom Hibernate UserType for handling SQL ARRAY type with String[] in Java.
 * This implementation allows mapping between PostgreSQL text[] arrays and Java String arrays.
 *
 * @author Junod Arthur
 * @author Tranchida Rachel
 * @author Häffner Edwin
 * @author Ray Eva
 */
public class FilmTypes implements UserType<String[]> {

    /**
     * Returns the SQL type code for the array type.
     *
     * @return the SQL type code for the array type
     */
    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    /**
     * Returns the class of the array type.
     *
     * @return the class of the array type
     */
    @Override
    public Class returnedClass() {
        return String[].class;
    }

    /**
     * Compares two arrays of Strings.
     *
     * @param strings the first array of Strings
     * @param j1      the second array of Strings
     * @return true if the arrays are equal, false otherwise
     */
    @Override
    public boolean equals(String[] strings, String[] j1) {
        return Arrays.equals(j1, strings);
    }

    /**
     * Returns the hash code of the array of Strings.
     *
     * @param strings the array of Strings
     * @return the hash code of the array of Strings
     */
    @Override
    public int hashCode(String[] strings) {
        return Arrays.hashCode(strings);
    }


    /**
     * Retrieves the value of the specified column as an array of Strings.
     * This method is called by Hibernate to extract the value from the result set.
     *
     * @param rs       the result set from which to retrieve the value
     * @param position the column position in the result set
     * @param session  the Hibernate session
     * @param owner    the entity that owns the association
     * @return an array of Strings representing the value of the specified column, or null if the column value is SQL NULL
     * @throws SQLException if an SQL error occurs while retrieving the value
     */
    @Override
    public String[] nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session,
                                Object owner) throws SQLException {
        Array array = rs.getArray(position);
        return array != null ? (String[]) array.getArray() : null;
    }

    /**
     * Sets the value of the specified column as an array of Strings.
     * This method is called by Hibernate to set the value in the prepared statement.
     *
     * @param st      the prepared statement in which to set the value
     * @param value   the array of Strings to set
     * @param index   the column position in the prepared statement
     * @param session the Hibernate session
     * @throws SQLException if an SQL error occurs while setting the value
     */
    @Override
    public void nullSafeSet(PreparedStatement st, String[] value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if (st != null) {
            if (value != null) {
                Array array = session.getJdbcConnectionAccess().obtainConnection()
                        .createArrayOf("text", value);
                st.setArray(index, array);
            } else {
                st.setNull(index, Types.ARRAY);
            }
        }
    }

    /**
     * Returns a deep copy of the array of Strings.
     *
     * @param strings the array of Strings to copy
     * @return a deep copy of the array of Strings
     */
    @Override
    public String[] deepCopy(String[] strings) {
        return new String[0];
    }

    /**
     * Determines if the array of Strings is mutable.
     *
     * @return false, as the array of Strings is immutable
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     * Disassembles the array of Strings into a serializable form.
     * This method is called by Hibernate when caching the array.
     *
     * @param strings the array of Strings to disassemble
     * @return a serializable form of the array of Strings
     */
    @Override
    public Serializable disassemble(String[] strings) {
        return null;
    }

    /**
     * Assembles the array of Strings from its serializable form.
     * This method is called by Hibernate when retrieving the array from the cache.
     *
     * @param serializable the serializable form of the array of Strings
     * @param owner        the entity that owns the association
     * @return the array of Strings
     */
    @Override
    public String[] assemble(Serializable serializable, Object owner) {
        return new String[0];
    }
}


