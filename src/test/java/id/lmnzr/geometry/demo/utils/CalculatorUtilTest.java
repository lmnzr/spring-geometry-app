package id.lmnzr.geometry.demo.utils;


import id.lmnzr.geometry.demo.util.Calculator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorUtilTest {
    private Calculator calculator = new Calculator();

    @Test
    void evaluateFormulaAdditionTest() {
        String formula = "a + b";
        Map<String,Double> variables = new HashMap<>();
        variables.put("a",5678D);
        variables.put("b",1234D);

        double result = calculator.evaluate(formula,variables);
        assertEquals(6912D,result);
    }

    @Test
    void evaluateFormulaSubstractTest() {
        String formula = "a - b";
        Map<String,Double> variables = new HashMap<>();
        variables.put("a",5678D);
        variables.put("b",1234D);

        double result = calculator.evaluate(formula,variables);
        assertEquals(4444D,result);
    }

    @Test
    void evaluateFormulaMultiplyTest() {
        String formula = "a * b";
        Map<String,Double> variables = new HashMap<>();
        variables.put("a",5678D);
        variables.put("b",1234D);

        double result = calculator.evaluate(formula,variables);
        assertEquals(7006652D,result);
    }

    @Test
    void evaluateFormulaDivisionTest() {
        String formula = "a / b";
        Map<String,Double> variables = new HashMap<>();
        variables.put("a",5678D);
        variables.put("b",1234D);

        double result = calculator.evaluate(formula,variables);
        assertEquals(4.60D,result);
    }

    @Test
    void evaluateFormulaSquaredTest() {
        String formula = "a^2";
        Map<String,Double> variables = new HashMap<>();
        variables.put("a",5678D);

        double result = calculator.evaluate(formula,variables);
        assertEquals(32239684D,result);
    }

    @Test
    void evaluateFormulaTimesPiTest() {
        String formula = "a * pi";
        Map<String,Double> variables = new HashMap<>();
        variables.put("a",5678D);

        double result = calculator.evaluate(formula,variables);
        assertEquals(17837.96D,result);
    }

    @Test
    void evaluateFormulaComplexTest() {
        String formula = "(((a + b)^2)-c)/d + pi";
        Map<String,Double> variables = new HashMap<>();
        variables.put("a",1D);
        variables.put("b",2D);
        variables.put("c",3D);
        variables.put("d",4D);
        double result = calculator.evaluate(formula,variables);
        assertEquals(4.64,result);
    }
}
