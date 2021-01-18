package org.apache.shardingsphere.sql.parser.mysql.visitor;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.shardingsphere.sql.parser.api.ASTNode;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementBaseVisitor;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser;
import org.apache.shardingsphere.sql.parser.mysql.lexer.MySQLLexer;
import org.apache.shardingsphere.sql.parser.mysql.parser.MySQLParser;
import org.apache.shardingsphere.sql.parser.mysql.visitor.MySQLVisitor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Test extends MySQLVisitor {

    @Override
    public ASTNode visitRee(MySQLStatementParser.ReeContext ctx) {
        System.out.println("hello ree.");
        return super.visitRee(ctx);
    }

    public static void main(String[] args) throws Exception{
        Test exec = new Test();
        String execString = "select c1,c2 from table0 where c1 = 1 order by c3 ";
//        execString = "ree table1 -> table2";
        InputStream input = null;
        if (execString != null) {
            input = new ByteArrayInputStream(execString.getBytes("UTF-8"));
        }
        MySQLLexer lexer = new MySQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MySQLParser parser = new MySQLParser(tokens);
        ParseTree tree = parser.execute();
        System.out.println(tree.getClass().getName());
        ASTNode astNode = exec.visit(tree);
        System.out.println(astNode.getClass().getName());
//        Integer i = programContext.accept(exec);
//        System.out.println("i:" + i);
        System.out.println("OVER!");
    }

}
