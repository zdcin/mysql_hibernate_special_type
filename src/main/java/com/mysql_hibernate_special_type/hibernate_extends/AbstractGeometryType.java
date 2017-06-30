package com.mysql_hibernate_special_type.hibernate_extends;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ByteOrderValues;
import com.vividsolutions.jts.io.InputStreamInStream;
import com.vividsolutions.jts.io.OutputStreamOutStream;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;

public abstract class AbstractGeometryType<T extends Geometry> implements UserType {

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.OTHER };
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, final String[] names, final SharedSessionContractImplementor session,
			final Object owner) throws HibernateException, SQLException {
		final byte[] cellContent = rs.getBytes(names[0]);
		if (cellContent == null) {
			return null;
		}
		try {
			// return new WKBReader().read(cellContent);
			InputStream is = new ByteArrayInputStream(cellContent);
			byte[] buf = new byte[4];
			is.read(buf);
			int SRID = ByteOrderValues.getInt(buf, ByteOrderValues.LITTLE_ENDIAN);
			WKBReader reader = new WKBReader();
			Geometry ret = reader.read(new InputStreamInStream(is));
			ret.setSRID(SRID);

			return (Geometry) ret;
		} catch (final Exception ex) {
			throw new RuntimeException("Failed to convert String to Invoice: " + ex.getMessage(), ex);
		}
	}

	@Override
	public void nullSafeSet(final PreparedStatement ps, final Object point, final int idx,
			final SharedSessionContractImplementor session) throws HibernateException, SQLException {
		if (point == null) {
			ps.setNull(idx, Types.BINARY);
			return;
		}
		try {
			// WKBWriter w = new WKBWriter();
			// ps.setObject(idx, w.write((Geometry) value), Types.BINARY);
			Geometry geom = (Geometry) point;

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int SRID = geom.getSRID();

			try {
				byte[] buf = new byte[4];
				ByteOrderValues.putInt(SRID, buf, ByteOrderValues.LITTLE_ENDIAN);
				bos.write(buf);

				WKBWriter writer = new WKBWriter(2, ByteOrderValues.LITTLE_ENDIAN);
				writer.write(geom, new OutputStreamOutStream(bos));
			} catch (IOException e) {
				// should be impossible
				e.printStackTrace();
			}

			ps.setObject(idx, bos.toByteArray(), Types.BINARY);
		} catch (final Exception ex) {
			throw new RuntimeException("Failed to convert Invoice to String: " + ex.getMessage(), ex);
		}
	}

	@Override
	public Object deepCopy(final Object value) throws HibernateException {
		if (value == null) {
			return null;
		}
		Geometry p = (Geometry) value;
		return p.clone();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		if (value == null) {
			return null;
		}
		return (Serializable) this.deepCopy(value);
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		if (cached == null) {
			return null;
		}
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