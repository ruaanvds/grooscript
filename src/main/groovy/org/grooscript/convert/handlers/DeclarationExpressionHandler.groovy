package org.grooscript.convert.handlers

import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.expr.EmptyExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.TupleExpression
import org.codehaus.groovy.ast.expr.VariableExpression

/**
 * User: jorgefrancoleza
 * Date: 25/05/14
 */
class DeclarationExpressionHandler extends BaseHandler {

    void handle(DeclarationExpression expression) {
        //println 'l->'+expression.leftExpression
        //println 'r->'+expression.rightExpression
        //println 'v->'+expression.getVariableExpression()

        if (expression.isMultipleAssignmentDeclaration()) {
            TupleExpression tuple = (TupleExpression)(expression.getLeftExpression())
            def number = 0;
            tuple.expressions.each { Expression expr ->
                //println 'Multiple->'+expr
                if (expr instanceof VariableExpression && expr.name!='_') {
                    context.addToActualScope(expr.name)
                    out.addScript('var ')
                    factory.getConverter('VariableExpression').handle(expr, true)
                    out.addScript(' = ')
                    factory.visitNode(expression.rightExpression)
                    out.addScript(".getAt(${number})")
                    if (number < tuple.expressions.size()) {
                        out.addScript(';')
                    }
                }
                number++
            }
        } else {
            context.addToActualScope(expression.variableExpression.name)

            out.addScript('var ')
            factory.getConverter('VariableExpression').handle(expression.variableExpression, true)

            if (!(expression.rightExpression instanceof EmptyExpression)) {
                out.addScript(' = ')
                factory.visitNode(expression.rightExpression)
            } else {
                out.addScript(' = null')
            }
        }
    }
}
