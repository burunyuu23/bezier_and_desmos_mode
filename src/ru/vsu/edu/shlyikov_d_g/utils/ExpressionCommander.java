package ru.vsu.edu.shlyikov_d_g.utils;

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

    public boolean isSqrt(){
        return func.contains("sqrt");
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