package com.ianturton.functions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.factory.CommonFactoryFinder;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.expression.Expression;

class LowerLeftFunctionTest {

  @Test
  void testEvaluateObjectClassOfT() throws IOException {
    FilterFactory ff = CommonFactoryFinder.getFilterFactory();
    Expression expr = ff.function("lowerleft", ff.property("the_geom"));
    System.out.println(expr);

    FileDataStore ds = FileDataStoreFinder.getDataStore(new File("/home/ian/Data/states/states.shp"));
    File out = new File("ll.txt");
    FileWriter fw = new FileWriter(out);
    try (SimpleFeatureIterator it = ds.getFeatureSource().getFeatures().features()) {
      while (it.hasNext()) {
        SimpleFeature type = it.next();
        fw.write(expr.evaluate(type) + "\n");
      }
    }
    fw.close();
  }

}
