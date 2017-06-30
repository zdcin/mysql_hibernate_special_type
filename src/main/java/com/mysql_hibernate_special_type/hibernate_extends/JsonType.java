package com.mysql_hibernate_special_type.hibernate_extends;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.json.JSONObject;

public class JsonType implements UserType {

	@Override
	public int[] sqlTypes() {
//		return new int[] { Types.LONGVARCHAR, Types.JAVA_OBJECT, Types.VARCHAR };
		return new int[] {Types.JAVA_OBJECT };
	}

	@Override
	public Class<JSONObject> returnedClass() {
		return JSONObject.class;
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, final String[] names, final SharedSessionContractImplementor session,
			final Object owner) throws HibernateException, SQLException {
		final String cellContent = rs.getString(names[0]);
		//System.out.println("cellContent:" + cellContent);
		if (cellContent == null) {
			return null;
		}
		try {
			return new JSONObject(cellContent);
		} catch (final Exception ex) {
			throw new RuntimeException("Failed to convert String to Invoice: " + ex.getMessage(), ex);
		}
	}

	@Override
	public void nullSafeSet(final PreparedStatement ps, final Object value, final int idx,
			final SharedSessionContractImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			ps.setNull(idx, Types.VARCHAR);
			return;
		}
		try {
			ps.setObject(idx, value.toString(), Types.VARCHAR);
		} catch (final Exception ex) {
			throw new RuntimeException("Failed to convert Invoice to String: " + ex.getMessage(), ex);
		}
	}

	@Override
	public Object deepCopy(final Object value) throws HibernateException {
		if (value == null) {
			return null;
		}

		return new JSONObject(value.toString());
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) this.deepCopy(value).toString();
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return this.deepCopy(new JSONObject((String) cached));
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return this.deepCopy(original);
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return Objects.equals(x, y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return Objects.hashCode(x);
	}


}