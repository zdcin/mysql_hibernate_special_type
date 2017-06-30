package com.mysql_hibernate_special_type.hibernate_extends;

import com.vividsolutions.jts.geom.Polygon;

public class PolygonType  extends AbstractGeometryType<Polygon> {

	@Override
	public Class<Polygon> returnedClass() {
		return Polygon.class;
	}
	
}