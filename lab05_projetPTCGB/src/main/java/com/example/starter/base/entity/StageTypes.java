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
 * This class represents the custom type for the Pokemon card stage.
 * The custom type is used to map the Pokemon card stage to the database.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public class StageTypes implements UserType<Stage> {

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
    public Class<Stage> returnedClass() {
        return Stage.class;
    }

    /**
     * Compares two Stage objects.
     *
     * @param stage1 the first Stage object
     * @param stage2 the second Stage object
     * @return true if the Stage objects are equal, false otherwise
     */
    @Override
    public boolean equals(Stage stage1, Stage stage2) {
        return stage1.equals(stage2);
    }

    /**
     * Returns the hash code of the Stage object.
     *
     * @param stage the Stage object
     * @return the hash code of the Stage object
     */
    @Override
    public int hashCode(Stage stage) {
        return stage.hashCode();
    }

    /**
     * Retrieves the value of the specified column as a Stage object.
     *
     * @param rs the result set from which to retrieve the value
     * @param position the column position in the result set
     * @param session the Hibernate session
     * @param owner the entity that owns the association
     * @return a Stage object representing the value of the specified column
     * @throws SQLException if an SQL error occurs while retrieving the value
     */
    @Override
    public Stage nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String value = rs.getString(position);
        return value != null ? Stage.fromString(value) : null;
    }

    /**
     * Sets the value of the specified column as a Stage object.
     *
     * @param st the prepared statement in which to set the value
     * @param value the Stage object to set
     * @param index the column position in the prepared statement
     * @param session the Hibernate session
     * @throws SQLException if an SQL error occurs while setting the value
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Stage value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            PGobject pgObj = new PGobject();
            pgObj.setType("card_stage");
            pgObj.setValue(value.toString());
            st.setObject(index, pgObj);
        }
    }

    /**
     * Returns a deep copy of the Stage object.
     *
     * @param stage the Stage object to copy
     * @return a deep copy of the Stage object
     */
    @Override
    public Stage deepCopy(Stage stage) {
        return stage;
    }

    /**
     * Determines if the Stage object is mutable.
     *
     * @return false, as the Stage object is immutable
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     * Disassembles the Stage object into a serializable form.
     *
     * @param stage the Stage object to disassemble
     * @return a serializable form of the Stage object
     */
    @Override
    public Serializable disassemble(Stage stage) {
        return stage;
    }

    /**
     * Assembles the Stage object from its serializable form.
     *
     * @param cached the serializable form of the Stage object
     * @param owner the entity that owns the association
     * @return the Stage object
     */
    @Override
    public Stage assemble(Serializable cached, Object owner) {
        return (Stage) cached;
    }
}
