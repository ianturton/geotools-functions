package com.ianturton.functions;

import java.util.List;

import org.geotools.filter.capability.FunctionNameImpl;
import org.geotools.util.Converters;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.opengis.filter.capability.FunctionName;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.ExpressionVisitor;
import org.opengis.filter.expression.Function;
import org.opengis.filter.expression.Literal;

public class LowerLeftFunction implements Function {
  static FunctionName NAME = new FunctionNameImpl("lowerleft", Polygon.class,
      FunctionNameImpl.parameter("polygon", Polygon.class));

  private final List<Expression> parameters;

  private final Literal fallback;

  public LowerLeftFunction(List<Expression> params, Literal fb) {
    if (params == null) {
      throw new NullPointerException("parameters required");
    }
    if (params.size() != 1) {
      throw new IllegalArgumentException("LowerLeft( polygon) requires exactly one parameter");
    }
    parameters = params;
    fallback = fb;
  }
  @Override
  public Object evaluate(Object object) {

    return evaluate(object, Point.class);
  }

  @Override
  public <T> T evaluate(Object object, Class<T> context) {
    Expression polyExp = parameters.get(0);
    Polygon poly = polyExp.evaluate(object, Polygon.class);
    LineString ext = poly.getExteriorRing();
    Coordinate ll = new Coordinate(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    for (Coordinate c : ext.getCoordinates()) {
      if (c.x <= ll.x && c.y <= ll.y) {
        ll = c;
      }
    }
    // System.err.println(poly);
    Point pt = poly.getFactory().createPoint(ll);
    // System.err.println(pt);
    return Converters.convert(pt, context);
  }

  @Override
  public Object accept(ExpressionVisitor visitor, Object extraData) {
    return visitor.visit(this, extraData);
  }

  @Override
  public String getName() {
    return NAME.getName();
  }

  @Override
  public FunctionName getFunctionName() {
    return NAME;
  }

  @Override
  public List<Expression> getParameters() {
    return parameters;
  }

  @Override
  public Literal getFallbackValue() {

    return fallback;
  }

}
