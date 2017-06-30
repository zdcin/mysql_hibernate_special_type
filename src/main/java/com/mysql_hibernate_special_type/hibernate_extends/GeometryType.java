package com.mysql_hibernate_special_type.hibernate_extends;

import com.vividsolutions.jts.geom.Geometry;

public class GeometryType extends AbstractGeometryType<Geometry>{

	@Override
	public Class<Geometry> returnedClass() {
		return Geometry.class;
	}
	
}
