package com.ianturton.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.geotools.feature.NameImpl;
import org.geotools.filter.FunctionFactory;
import org.opengis.feature.type.Name;
import org.opengis.filter.capability.FunctionName;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.Function;
import org.opengis.filter.expression.Literal;

public class LowerLeftFunctionFactory implements FunctionFactory {

  @Override
  public List<FunctionName> getFunctionNames() {
    List<FunctionName> funcList = new ArrayList<>();
    funcList.add(LowerLeftFunction.NAME);
    return Collections.unmodifiableList(funcList);
  }

  @Override
  public Function function(String name, List<Expression> args, Literal fallback) {
    return function(new NameImpl(name), args, fallback);
  }

  @Override
  public Function function(Name name, List<Expression> args, Literal fallback) {
    if (LowerLeftFunction.NAME.getFunctionName().equals(name)) {
      return new LowerLeftFunction(args, fallback);
    }
    return null;
  }

}
