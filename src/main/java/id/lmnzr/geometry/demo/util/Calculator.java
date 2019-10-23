package id.lmnzr.geometry.demo.util;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * The type Calculator.
 */
@Component
public class Calculator {
    /**
     * Evaluate double.
     *
     * @param formula   the formula
     * @param variables the variables
     * @return the double
     * @throws UnknownFunctionOrVariableException the unknown function or variable exception
     */
    public double evaluate(String formula, Map<String,Double> variables) throws UnknownFunctionOrVariableException {
        ExpressionBuilder expressionBuilder = new ExpressionBuilder(formula);
        variables.forEach((variable,value)->expressionBuilder.variable(variable));

        Expression expression = expressionBuilder.build();
        variables.forEach(expression::setVariable);

        return  (double) Math.round(expression.evaluate() * 100) / 100;
    }

}
