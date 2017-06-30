package com.mysql_hibernate_special_type.hibernate_extends;

import com.vividsolutions.jts.geom.Point;

public class PointType extends AbstractGeometryType<Point> {

	@Override
	public Class<Point> returnedClass() {
		return Point.class;
	}
	
}