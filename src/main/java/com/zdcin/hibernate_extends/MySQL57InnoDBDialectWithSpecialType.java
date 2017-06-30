package com.zdcin.hibernate_extends;

import org.hibernate.dialect.MySQL57InnoDBDialect;

import com.mysql_hibernate_special_type.hibernate_extends.JsonType;
import com.mysql_hibernate_special_type.hibernate_extends.PointType;
import com.mysql_hibernate_special_type.hibernate_extends.PolygonType;

public class MySQL57InnoDBDialectWithSpecialType extends MySQL57InnoDBDialect {
	public MySQL57InnoDBDialectWithSpecialType() {
		super();

		int startIndex = 5001;
		registerHibernateType(startIndex++, MyTestEnumType.class.getName());
		registerHibernateType(startIndex++, PointType.class.getName());
		registerHibernateType(startIndex++, PolygonType.class.getName());
		registerHibernateType(startIndex++, JsonType.class.getName());
		
	}
}