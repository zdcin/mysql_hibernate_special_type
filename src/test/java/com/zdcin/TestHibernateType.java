package com.zdcin;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.zdcin.Context;
import com.zdcin.entity.TestEachType;
import com.zdcin.entity.TestEachType.TestEnum;

/**
 * @author leo
 *
 */
public class TestHibernateType {

	public static void main(String[] args) {
		Transaction tr1;

		tr1 = Context.getHSession().beginTransaction();
		// 加载一个OID为1的对象
		TestEachType c = Context.getHSession().load(TestEachType.class, 1);
		System.out.println("json:" + c.getJson());
		System.out.println("enum:" + c.getTestenum());
		System.out.println("geo:" + c.getGeo());
		System.out.println("geo_range:" + c.getGeoRange());

		tr1.commit();
		tr1 = Context.getHSession().beginTransaction();
		c.setTestenum(TestEnum.A);
		c.getJson().put("xxx", 1);
		
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
		Coordinate coord = new Coordinate(109.013388, 32.7155201);
		Point point = geometryFactory.createPoint(coord);
		c.setGeo(point);
		
		WKTReader reader = new WKTReader(geometryFactory);
		try {
			Polygon polygon = (Polygon) reader.read("POLYGON((20 10, 30 1234, 40 10, 30 57, 20 10))");
			c.setGeoRange(polygon);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Context.getHSession().update(c);
		tr1.commit();
	}
}
