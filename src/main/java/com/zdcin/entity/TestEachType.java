package com.zdcin.entity;
// Generated 2017-6-1 18:07:22 by Hibernate Tools 5.2.3.Final

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import lombok.Data;

/**
 * Device generated by hbm2java
 */
@Data
public class TestEachType implements java.io.Serializable {
	public static enum TestEnum {
		A,BB;
	}
	private static final long serialVersionUID = 4945029519336386646L;
	private Integer id;
	private JSONObject json;
	private TestEnum testenum;
	private Point geo;
	private Polygon geoRange;

	public TestEachType() {
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getClass().getName()).append(this.id).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

}