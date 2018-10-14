package com.aoeai.tools.mybatis.utils;

import com.aoeai.tools.mybatis.service.Prasing_Test;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Sql解析
 */
public class SqlParsing {

    public static void main(String[] args) throws JSQLParserException {
        String sql = "select a, b from user where name = '名字' and age > 2 or sex = 1";
        Statement statement = CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);


        List<String> items = selectItems(sql);
        List<String> tables = selectTables(sql);
        String where = Prasing_Test.test_select_where(sql);


        System.out.println(sql);
    }


    /**
     * select字段
     * @param sql
     * @return
     * @throws JSQLParserException
     */
    public static List<String> selectItems(String sql)
            throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectitems = plain.getSelectItems();
        List<String> str_items = new ArrayList<>();

        if (selectitems != null) {
            for (int i = 0; i < selectitems.size(); i++) {
                str_items.add(selectitems.get(i).toString());
            }
        }

        return str_items;
    }

    /**
     * select语句中出现的所有表
     * @param sql
     * @return
     * @throws JSQLParserException
     */
    public static List<String> selectTables(String sql)
            throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

        List<String> tableList = tablesNamesFinder
                .getTableList(selectStatement);

        return tableList;
    }


}
