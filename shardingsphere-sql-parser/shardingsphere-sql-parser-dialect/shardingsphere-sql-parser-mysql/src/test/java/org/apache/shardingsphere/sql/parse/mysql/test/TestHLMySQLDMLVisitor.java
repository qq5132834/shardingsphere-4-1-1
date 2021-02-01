package org.apache.shardingsphere.sql.parse.mysql.test;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.shardingsphere.sql.parser.api.ASTNode;
import org.apache.shardingsphere.sql.parser.api.visitor.statement.DMLVisitor;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.*;
import org.apache.shardingsphere.sql.parser.mysql.lexer.MySQLLexer;
import org.apache.shardingsphere.sql.parser.mysql.parser.MySQLParser;
import org.apache.shardingsphere.sql.parser.mysql.visitor.MySQLVisitor;
import org.apache.shardingsphere.sql.parser.mysql.visitor.impl.MySQLDMLVisitor;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.JoinSpecificationSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.JoinedTableSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.TableFactorSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.TableReferenceSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.assignment.AssignmentSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.assignment.InsertValuesSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.assignment.SetAssignmentSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.column.ColumnSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.column.InsertColumnsSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.column.OnDuplicateKeyColumnsSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.expr.ExpressionSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.expr.complex.CommonExpressionSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.expr.simple.LiteralExpressionSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.expr.subquery.SubqueryExpressionSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.expr.subquery.SubquerySegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.item.*;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.order.GroupBySegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.order.OrderBySegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.order.item.OrderByItemSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.pagination.PaginationValueSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.pagination.limit.LimitSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.pagination.limit.NumberLiteralLimitValueSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.pagination.limit.ParameterMarkerLimitValueSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.predicate.*;
import org.apache.shardingsphere.sql.parser.sql.segment.generic.AliasSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.generic.OwnerSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.generic.table.SimpleTableSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.generic.table.SubqueryTableSegment;
import org.apache.shardingsphere.sql.parser.sql.statement.dml.*;
import org.apache.shardingsphere.sql.parser.sql.value.collection.CollectionValue;
import org.apache.shardingsphere.sql.parser.sql.value.identifier.IdentifierValue;
import org.apache.shardingsphere.sql.parser.sql.value.literal.impl.BooleanLiteralValue;
import org.apache.shardingsphere.sql.parser.sql.value.literal.impl.NumberLiteralValue;
import org.apache.shardingsphere.sql.parser.sql.value.literal.impl.StringLiteralValue;
import org.apache.shardingsphere.sql.parser.sql.value.parametermarker.ParameterMarkerValue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * MySQL 语法解析
 *
 * [MySQL的语法解析过程]
 */
public final class TestHLMySQLDMLVisitor  {

    public static void main(String[] args) throws IOException {
        MySQLDMLVisitor mySQLDMLVisitor = new MySQLDMLVisitor();
//        String execString = "select c1,c2 from table0 where c1 = 1 order by c3 ";
//        String execString = "select c1,c2 from table0 where c1 = 1 group by c1, c2 order by c1 desc ";
        String execString = "select c1,c2 from table0 where c1 = 1";
//        String execString = "select c1,c2 from table0 where c1 = 1 group by c1, c2";
//        execString = "ree table1 -> table2";
        InputStream input = null;
        if (execString != null) {
            input = new ByteArrayInputStream(execString.getBytes("UTF-8"));
        }
        MySQLLexer lexer = new MySQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MySQLParser parser = new MySQLParser(tokens);
        ParseTree tree = parser.execute();
        System.out.println("tree:" + tree.getClass().getName());
        ASTNode astNode = mySQLDMLVisitor.visit(tree);
        System.out.println("astNode:" + astNode.getClass().getName());
//        Integer i = programContext.accept(exec);
//        System.out.println("i:" + i);
        System.out.println("OVER!");
    }

}
