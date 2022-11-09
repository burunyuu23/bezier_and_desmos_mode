package ru.vsu.edu.shlyikov_d_g.parser;

import net.objecthunter.exp4j.*;

public class ExpressionCommander {
    private String func;
    private final Expression expression;

    public ExpressionCommander(String func) {
        this.func = func;
        this.expression = new ExpressionBuilder(func)
                .variable("x")
                .build();
    }

    public double solve(double x) {
        try{
            expression.setVariable("x", x);
            return expression.evaluate();
        } catch (Throwable e){
            if (e instanceof ArithmeticException && "Division by zero!".equals((e.getMessage()))){
                return Double.MAX_VALUE;
            }
            else{
                return Double.NaN;
            }
        }
    }
}