package org.apache.shardingsphere.sql.parse.mysql.test;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.shardingsphere.sql.parser.api.ASTNode;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser;
import org.apache.shardingsphere.sql.parser.mysql.lexer.MySQLLexer;
import org.apache.shardingsphere.sql.parser.mysql.parser.MySQLParser;
import org.apache.shardingsphere.sql.parser.mysql.visitor.MySQLVisitor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/***
 *  mysql ree语法检测
 */
public class TestHLMysqlReeProgram extends MySQLVisitor {

    @Override
    public ASTNode visitRee(MySQLStatementParser.ReeContext ctx) {
        System.out.println("hello ree.");
        return super.visitRee(ctx);
    }

    @Override
    public ASTNode visitInsertSelectClause(MySQLStatementParser.InsertSelectClauseContext ctx) {
        return super.visitInsertSelectClause(ctx);
    }

    @Override
    public ASTNode visitSelect(MySQLStatementParser.SelectContext ctx) {
        System.out.println("visitSelect");
        return super.visitSelect(ctx);
    }

    @Override
    public ASTNode visitSelectClause(MySQLStatementParser.SelectClauseContext ctx) {
        System.out.println("visitSelectClause");
        return super.visitSelectClause(ctx);
    }

    @Override
    public ASTNode visitSelectSpecification(MySQLStatementParser.SelectSpecificationContext ctx) {
        return super.visitSelectSpecification(ctx);
    }

    @Override
    public ASTNode visitSelectLinesInto_(MySQLStatementParser.SelectLinesInto_Context ctx) {
        return super.visitSelectLinesInto_(ctx);
    }

    @Override
    public ASTNode visitSelectFieldsInto_(MySQLStatementParser.SelectFieldsInto_Context ctx) {
        return super.visitSelectFieldsInto_(ctx);
    }

    @Override
    public ASTNode visitSelectIntoExpression_(MySQLStatementParser.SelectIntoExpression_Context ctx) {
        return super.visitSelectIntoExpression_(ctx);
    }

    public static void main(String[] args) throws Exception{
        TestHLMysqlReeProgram exec = new TestHLMysqlReeProgram();
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
        ASTNode astNode = exec.visit(tree);
        System.out.println("astNode:" + astNode.getClass().getName());
//        Integer i = programContext.accept(exec);
//        System.out.println("i:" + i);
        System.out.println("OVER!");
    }

}
