package cc.oit.bsmes.generator;

import cc.oit.bsmes.generator.model.Column;
import cc.oit.bsmes.generator.model.ColumnType;
import cc.oit.bsmes.generator.model.Table;
import cc.oit.bsmes.generator.parser.CreateTableLexer;
import cc.oit.bsmes.generator.parser.CreateTableListenerImpl;
import cc.oit.bsmes.generator.parser.CreateTableParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by 羽霓 on 2014/6/9.
 */
public class BeanFilesGenerator {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String contextPath;
    private String moduleName;
    private String javaPath;
    private String jsPath;
    private String jspPath;
    private String rootPackage;
    private List<Table> tables;

    public BeanFilesGenerator(String moduleName) {
        this.moduleName = moduleName;
        String path = BeanFilesGenerator.class.getResource("/").getPath();
        this.javaPath = path.replace("target/classes/", "src/main/java/cc/oit/bsmes/" + moduleName);
        this.jsPath = path.replace("BaoShengMES-Core/target/classes/", "BaoShengMES-Static/WebContent/app/" + moduleName);
        this.jspPath = path.replace("BaoShengMES-Core/target/classes/", "BaoShengMES-Web/WebContent/WEB-INF/views/" + moduleName);
        this.contextPath = path.replace("target/classes/", "");
        logger.debug("javaPath:{}, jsPath:{}", javaPath, jsPath);
        this.rootPackage = "cc.oit.bsmes." + moduleName;
    }

    public void generate() throws IOException {
        parseSql();
        generateJavaFiles();
        generateJsFiles();
        generateJspFiles();
    }

    public void parseSql() throws IOException {
        CharStream input = new ANTLRFileStream(javaPath + "/sql");
        CreateTableLexer lexer = new CreateTableLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CreateTableParser parser = new CreateTableParser(tokens);
        ParseTree tree = parser.sql();

        ParseTreeWalker walker = new ParseTreeWalker();
        CreateTableListenerImpl extractor = new CreateTableListenerImpl();
        walker.walk(extractor, tree);//initiatewalkoftreewithlistener
        tables = extractor.getTables();
    }

    public void generateJavaFiles() throws IOException {
        ensureDirExists(javaPath);

        generateModel();
        generateDAO();
        generateMapper();
        generateService();
        generateServiceImpl();
        generateController();
    }

    public void generateJsFiles() throws IOException {
        ensureDirExists(jsPath);
        generateJsLocale();

        for (Table table : tables) {
            String modelName = table.getModelName();
            String dirPath = jsPath + "/" + StringUtils.uncapitalize(modelName);
            ensureDirExists(dirPath);
            generateJsModel(dirPath, table);
            generateJsStore(dirPath, table);
            generateJsView(dirPath, table);
            generateJsController(dirPath, table);
        }
    }

    public void generateJspFiles() throws IOException {
        ensureDirExists(jspPath);

        for (Table table : tables) {
            String modelName = table.getModelName();
            String dirPath = jspPath + "/" + StringUtils.uncapitalize(modelName);
            ensureDirExists(dirPath);

            File file = new File(dirPath + "/centerTab.jsp");
            if (file.exists()) {
                continue;
            }

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write("<%@page isELIgnored=\"false\"%>");
            fileWriter.newLine();
            fileWriter.write("<%@taglib uri=\"http://java.sun.com/jsp/jstl/fmt\" prefix=\"fmt\" %>");
            fileWriter.newLine();
            fileWriter.write("<widget title='<fmt:message key=\"${moduleName}.${submoduleName}.centerTitle\"/>' ");
            fileWriter.write("xtype=\"" + StringUtils.uncapitalize(modelName) + "List\" controller=\"" + modelName + "Controller\" closable=\"false\"> </widget>");
            fileWriter.flush();
            fileWriter.close();
            svnAdd(file);
        }
    }

    public void generateModel() throws IOException {
        String dirPath = javaPath + "/model";
        String packageName = rootPackage + ".model";
        ensureDirExists(dirPath);
        for (Table table : tables) {
            File file = new File(dirPath + "/" + table.getModelName() + ".java");
            if (file.exists()) {
                continue;
            }

            Collection<Column> columns = table.getColumns().values();

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write("package " + packageName + ";");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("import cc.oit.bsmes.common.model.Base;");
            fileWriter.newLine();
            fileWriter.write("import lombok.Data;");
            fileWriter.newLine();
            fileWriter.write("import lombok.EqualsAndHashCode;");
            fileWriter.newLine();
            fileWriter.write("import lombok.ToString;");
            fileWriter.newLine();
            fileWriter.write("import javax.persistence.Table;");
            fileWriter.newLine();
            fileWriter.write("import java.util.*;");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("/**");
            fileWriter.newLine();
            fileWriter.write(" * " + table.getComment());
            fileWriter.newLine();
            fileWriter.write(" * @author chanedi");
            fileWriter.newLine();
            fileWriter.write(" */");
            fileWriter.newLine();
            fileWriter.write("@Data");
            fileWriter.newLine();
            fileWriter.write("@EqualsAndHashCode(callSuper = false)");
            fileWriter.newLine();
            fileWriter.write("@ToString(callSuper = true)");
            fileWriter.newLine();
            fileWriter.write("@Table(name = \"" + table.getName() + "\")");
            fileWriter.newLine();
            fileWriter.write("public class " + table.getModelName() + " extends Base {");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("\tprivate static final long serialVersionUID = " + UUID.randomUUID().hashCode() + "L;");
            fileWriter.newLine();
            fileWriter.newLine();
            for (Column column : columns) {
                if (isColumnInBase(column.getModelName())) {
                    continue;
                }
                fileWriter.write("\tprivate " + column.getColumnType().getJavaType() + " " + column.getModelName() + "; ");
                if (column.getComment() != null) {
                    fileWriter.write("//" + column.getComment());
                }
                fileWriter.newLine();
            }
            fileWriter.newLine();
            fileWriter.write("}");
            fileWriter.flush();
            fileWriter.close();
            svnAdd(file);
        }
    }

    public void generateDAO() throws IOException {
        String dirPath = javaPath + "/dao";
        String packageName = rootPackage + ".dao";
        ensureDirExists(dirPath);
        for (Table table : tables) {
            String modelName = table.getModelName();
            File file = new File(dirPath + "/" + modelName + "DAO.java");
            if (file.exists()) {
                continue;
            }

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write("package " + packageName + ";");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("import cc.oit.bsmes.common.dao.BaseDAO;");
            fileWriter.newLine();
            fileWriter.write("import " + rootPackage + ".model." + modelName + ";");
            fileWriter.newLine();
            fileWriter.write("import java.util.*;");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("/**");
            fileWriter.newLine();
            fileWriter.write(" * @author chanedi");
            fileWriter.newLine();
            fileWriter.write(" */");
            fileWriter.newLine();
            fileWriter.write("public interface " + modelName + "DAO extends BaseDAO<" + modelName + "> {");
            fileWriter.newLine();
            fileWriter.write("}");
            fileWriter.flush();
            fileWriter.close();
            svnAdd(file);
        }
    }

    public void generateMapper() throws IOException {
        String dirPath = contextPath + "/src/main/resources/cc/META-INF/mybatis/oracle/" + moduleName;
        String packageName = rootPackage + ".dao";
        ensureDirExists(dirPath);
        for (Table table : tables) {
            String modelName = table.getModelName();
            File file = new File(dirPath + "/" + modelName + "Mapper.xml");
            if (file.exists()) {
                continue;
            }

            char prefix = modelName.toLowerCase().charAt(0);
            Collection<Column> columns = table.getColumns().values();

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> ");
            fileWriter.newLine();
            fileWriter.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            fileWriter.newLine();
            fileWriter.write("<mapper namespace=\"" + packageName + "." + modelName + "DAO\">");
            fileWriter.newLine();
            fileWriter.write("\t<resultMap type=\"" + rootPackage + ".model." + modelName + "\" id=\"getMap\">");
            fileWriter.newLine();
            fileWriter.write("\t</resultMap>");
            fileWriter.newLine();
            fileWriter.write("\t<sql id=\"queryColumns\">");
            fileWriter.newLine();
            Iterator<Column> it = columns.iterator();
            while (it.hasNext()) {
                Column column = it.next();
                fileWriter.write("\t\t" + prefix + "." + column.getName());
                if (it.hasNext()) {
                    fileWriter.write(",");
                }
                fileWriter.newLine();
            }
            fileWriter.write("\t</sql>");
            fileWriter.newLine();
            fileWriter.write("</mapper>");
            fileWriter.flush();
            fileWriter.close();
            svnAdd(file);
        }
    }

    public void generateService() throws IOException {
        String dirPath = javaPath + "/service";
        String packageName = rootPackage + ".service";
        ensureDirExists(dirPath);
        for (Table table : tables) {
            String modelName = table.getModelName();
            File file = new File(dirPath + "/" + modelName + "Service.java");
            if (file.exists()) {
                continue;
            }

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write("package " + packageName + ";");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("import cc.oit.bsmes.common.service.BaseService;");
            fileWriter.newLine();
            fileWriter.write("import " + rootPackage + ".model." + modelName + ";");
            fileWriter.newLine();
            fileWriter.write("import java.util.*;");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("/**");
            fileWriter.newLine();
            fileWriter.write(" * @author chanedi");
            fileWriter.newLine();
            fileWriter.write(" */");
            fileWriter.newLine();
            fileWriter.write("public interface " + modelName + "Service extends BaseService<" + modelName + "> {");
            fileWriter.newLine();
            fileWriter.write("}");
            fileWriter.flush();
            fileWriter.close();
            svnAdd(file);
        }
    }

    public void generateServiceImpl() throws IOException {
        String dirPath = javaPath + "/service/impl";
        String packageName = rootPackage + ".service.impl";
        ensureDirExists(dirPath);
        for (Table table : tables) {
            String modelName = table.getModelName();
            File file = new File(dirPath + "/" + modelName + "ServiceImpl.java");
            if (file.exists()) {
                continue;
            }

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write("package " + packageName + ";");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("import cc.oit.bsmes.common.service.impl.BaseServiceImpl;");
            fileWriter.newLine();
            fileWriter.write("import " + rootPackage + ".service." + modelName + "Service;");
            fileWriter.newLine();
            fileWriter.write("import " + rootPackage + ".model." + modelName + ";");
            fileWriter.newLine();
            fileWriter.write("import org.springframework.stereotype.Service;");
            fileWriter.newLine();
            fileWriter.write("import java.util.*;");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("/**");
            fileWriter.newLine();
            fileWriter.write(" * @author chanedi");
            fileWriter.newLine();
            fileWriter.write(" */");
            fileWriter.newLine();
            fileWriter.write("@Service");
            fileWriter.newLine();
            fileWriter.write("public class " + modelName + "ServiceImpl extends BaseServiceImpl<" + modelName + "> implements " + modelName + "Service {");
            fileWriter.newLine();
            fileWriter.write("}");
            fileWriter.flush();
            fileWriter.close();
            svnAdd(file);
        }
    }

    public void generateController() throws IOException {
        String dirPath = javaPath + "/action";
        String packageName = rootPackage + ".action";
        ensureDirExists(dirPath);
        for (Table table : tables) {
            String modelName = table.getModelName();
            File file = new File(dirPath + "/" + modelName + "Controller.java");
            if (file.exists()) {
                continue;
            }

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write("package " + packageName + ";");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("import org.springframework.stereotype.Controller;");
            fileWriter.newLine();
            fileWriter.write("import org.springframework.web.bind.annotation.RequestMapping;");
            fileWriter.newLine();
            fileWriter.newLine();
            fileWriter.write("/**");
            fileWriter.newLine();
            fileWriter.write(" * @author chanedi");
            fileWriter.newLine();
            fileWriter.write(" */");
            fileWriter.newLine();
            fileWriter.write("@Controller");
            fileWriter.newLine();
            fileWriter.write("@RequestMapping(\"/fac/" + StringUtils.uncapitalize(modelName) + "\")");
            fileWriter.newLine();
            fileWriter.write("public class " + modelName + "Controller {");
            fileWriter.newLine();
            fileWriter.write("}");
            fileWriter.flush();
            fileWriter.close();
            svnAdd(file);
        }
    }

    public void generateJsLocale() throws IOException {
        String dirPath = jsPath + "/locale";
        ensureDirExists(dirPath);

        for (Table table : tables) {
            String modelName = table.getModelName();
            File file = new File(dirPath + "/app-lang-zh_CN-" + modelName + ".js");
            if (file.exists()) {
                continue;
            }

            Collection<Column> columns = table.getColumns().values();

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write("Ext.define('Oit.app.locale.Message',{");
            fileWriter.newLine();
            fileWriter.write("\toverride: 'Oit.locale.Message',");
            fileWriter.newLine();
            fileWriter.write("\tstatics: {");
            fileWriter.newLine();
            fileWriter.write("\t\t" + moduleName + " :{");
            fileWriter.newLine();
            fileWriter.write("\t\t\t" + StringUtils.uncapitalize(modelName) + ":{");
            fileWriter.newLine();
            for (Column column : columns) {
                if (isColumnInCommon(column.getModelName())) {
                    continue;
                }
                fileWriter.write("\t\t\t\t" + column.getModelName() + ":'" + column.getComment() + "',");
                fileWriter.newLine();
            }
            fileWriter.write("\t\t\t\taddForm:{");
            fileWriter.newLine();
            fileWriter.write("\t\t\t\t\ttitle:'TODO'");
            fileWriter.newLine();
            fileWriter.write("\t\t\t\t},");
            fileWriter.newLine();
            fileWriter.write("\t\t\t\teditForm:{");
            fileWriter.newLine();
            fileWriter.write("\t\t\t\t\ttitle:'TODO'");
            fileWriter.newLine();
            fileWriter.write("\t\t\t\t}");
            fileWriter.newLine();

            fileWriter.write("\t\t\t}");
            fileWriter.newLine();
            fileWriter.write("\t\t}");
            fileWriter.newLine();
            fileWriter.write("\t}");
            fileWriter.newLine();
            fileWriter.write("});");
            fileWriter.flush();
            fileWriter.close();
        }
    }

    public void generateJsModel(String rootPath, Table table) throws IOException {
        String modelName = table.getModelName();
        Collection<Column> columns = table.getColumns().values();

        String dirPath = rootPath + "/model";
        String packageName = "bsmes.model.";
        ensureDirExists(dirPath);
        File file = new File(dirPath + "/" + modelName + ".js");
        if (file.exists()) {
            return;
        }

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
        fileWriter.write("Ext.define('" + packageName + modelName + "',{");
        fileWriter.newLine();
        fileWriter.write("\textend : 'Ext.data.Model',");
        fileWriter.newLine();
        fileWriter.write("\tfields : [");
        Iterator<Column> it = columns.iterator();
        while (it.hasNext()) {
            Column column = it.next();
            fileWriter.write("{");
            fileWriter.newLine();
            fileWriter.write("\t\tname : '" + column.getModelName() + "',");
            fileWriter.newLine();
            fileWriter.write("\t\ttype : '" + column.getColumnType().getJsType() + "'");
            fileWriter.newLine();
            fileWriter.write("\t}");
            if (it.hasNext()) {
                fileWriter.write(",");
            }
        }

        fileWriter.write("]");
        fileWriter.newLine();
        fileWriter.write("});");
        fileWriter.flush();
        fileWriter.close();
        svnAdd(file);
    }

    public void generateJsStore(String rootPath, Table table) throws IOException {
        String modelName = table.getModelName();

        String dirPath = rootPath + "/store";
        String packageName = "bsmes.store.";
        ensureDirExists(dirPath);
        File file = new File(dirPath + "/" + modelName + "Store.js");
        if (file.exists()) {
            return;
        }

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
        fileWriter.write("Ext.define('" + packageName + modelName + "Store',{");
        fileWriter.newLine();
        fileWriter.write("\textend : 'Oit.app.data.GridStore',");
        fileWriter.newLine();
        fileWriter.write("\tmodel:'bsmes.model." + modelName + "',");
        fileWriter.newLine();
        fileWriter.write("\tsorters : [ {");
        fileWriter.newLine();
        fileWriter.write("\t\tproperty : 'id',");
        fileWriter.newLine();
        fileWriter.write("\t\tdirection : 'ASC'");
        fileWriter.newLine();
        fileWriter.write("\t} ],");
        fileWriter.newLine();
        fileWriter.write("\tproxy : {");
        fileWriter.newLine();
        fileWriter.write("\t\turl : '" + StringUtils.uncapitalize(modelName) + "',");
        fileWriter.newLine();
        fileWriter.write("\t\textraParams : Oit.url.urlParams()");
        fileWriter.newLine();
        fileWriter.write("\t}");
        fileWriter.newLine();
        fileWriter.write("});");
        fileWriter.flush();
        fileWriter.close();
        svnAdd(file);
    }

    public void generateJsView(String rootPath, Table table) throws IOException {
        String modelName = table.getModelName();
        String dirPath = rootPath + "/view";
        String packageName = "bsmes.view.";
        String msgPackage = "Oit.msg." + moduleName + "." + StringUtils.uncapitalize(modelName) + ".";
        ensureDirExists(dirPath);

        generateJsViewList(table, dirPath, packageName, msgPackage);
        generateJsViewEdit(table, dirPath, packageName, msgPackage);
        generateJsViewAdd(table, dirPath, packageName, msgPackage);
    }

    public void generateJsViewList(Table table, String dirPath, String packageName, String msgPackage) throws IOException {
        String modelName = table.getModelName();
        File file = new File(dirPath + "/" + modelName + "List.js");
        if (file.exists()) {
            return;
        }

        Collection<Column> columns = table.getColumns().values();
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
        fileWriter.write("Ext.define('" + packageName + modelName + "List',{");
        fileWriter.newLine();
        fileWriter.write("\textend : 'Oit.app.view.FilterGrid',");
        fileWriter.newLine();
        fileWriter.write("\talias : 'widget." + StringUtils.uncapitalize(modelName) + "List',");
        fileWriter.newLine();
        fileWriter.write("\tstore : '" + modelName + "Store',");
        fileWriter.newLine();
        fileWriter.write("\tcolumns : [");
        Iterator<Column> it = columns.iterator();
        while (it.hasNext()) {
            Column column = it.next();
            if (isColumnId(column.getModelName())) {
                continue;
            }
            String cMsgPackage = msgPackage;
            if (isColumnInCommon(column.getModelName())) {
                cMsgPackage = "Oit.msg.";
            }
            fileWriter.write("{");
            fileWriter.newLine();
            fileWriter.write("\t\ttext : " + cMsgPackage + column.getModelName() + ",");
            fileWriter.newLine();
            fileWriter.write("\t\tdataIndex : '" + column.getModelName() + "',");
            fileWriter.newLine();
            if (column.getColumnType() == ColumnType.DATE) {
                fileWriter.write("\t\txtype : 'datecolumn',");
                fileWriter.newLine();
            }
            fileWriter.write("\t\tfilter : {");
            fileWriter.newLine();
            fileWriter.write("\t\t\ttype : '" + column.getColumnType().getJsFilter() + "'");
            fileWriter.newLine();
            fileWriter.write("\t\t}");
            fileWriter.newLine();
            fileWriter.write("\t} ");
            if (it.hasNext()) {
                fileWriter.write(",");
            }
        }

        fileWriter.write("],");
        fileWriter.newLine();
        fileWriter.write("\tactioncolumn : [{");
        fileWriter.newLine();
        fileWriter.write("\t\titemId : 'edit'");
        fileWriter.newLine();
        fileWriter.write("\t}],");
        fileWriter.newLine();
        fileWriter.write("\ttbar : [ {");
        fileWriter.newLine();
        fileWriter.write("\t\titemId : 'add'");
        fileWriter.newLine();
        fileWriter.write("\t},{");
        fileWriter.newLine();
        fileWriter.write("\t\titemId : 'remove'");
        fileWriter.newLine();
        fileWriter.write("\t}]");
        fileWriter.newLine();
        fileWriter.write("});");
        fileWriter.flush();
        fileWriter.close();
        svnAdd(file);
    }

    public void generateJsViewEdit(Table table, String dirPath, String packageName, String msgPackage) throws IOException {
        String modelName = table.getModelName();
        File file = new File(dirPath + "/" + modelName + "Edit.js");
        if (file.exists()) {
            return;
        }

        Collection<Column> columns = table.getColumns().values();
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
        fileWriter.write("Ext.define('" + packageName + modelName + "Edit',{");
        fileWriter.newLine();
        fileWriter.write("\textend: 'Oit.app.view.form.EditForm',");
        fileWriter.newLine();
        fileWriter.write("\talias : 'widget." + StringUtils.uncapitalize(modelName) + "Edit',");
        fileWriter.newLine();
        fileWriter.write("\ttitle: " + msgPackage + "editForm.title,");
        fileWriter.newLine();
        generateFormItems(msgPackage, columns, fileWriter);
        fileWriter.write("});");
        fileWriter.flush();
        fileWriter.close();
        svnAdd(file);
    }

    public void generateJsViewAdd(Table table, String dirPath, String packageName, String msgPackage) throws IOException {
        String modelName = table.getModelName();
        File file = new File(dirPath + "/" + modelName + "Add.js");
        if (file.exists()) {
            return;
        }

        Collection<Column> columns = table.getColumns().values();
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
        fileWriter.write("Ext.define('" + packageName + modelName + "Add',{");
        fileWriter.newLine();
        fileWriter.write("\textend: 'Oit.app.view.form.EditForm',");
        fileWriter.newLine();
        fileWriter.write("\talias : 'widget." + StringUtils.uncapitalize(modelName) + "Add',");
        fileWriter.newLine();
        fileWriter.write("\ttitle: " + msgPackage + "addForm.title,");
        fileWriter.newLine();
        generateFormItems(msgPackage, columns, fileWriter);
        fileWriter.write("});");
        fileWriter.flush();
        fileWriter.close();
        svnAdd(file);
    }

    private void generateFormItems(String msgPackage, Collection<Column> columns, BufferedWriter fileWriter) throws IOException {
        fileWriter.write("\tformItems: [");
        Iterator<Column> it = columns.iterator();
        while (it.hasNext()) {
            Column column = it.next();
            if (isColumnInCommon(column.getModelName())) {
                continue;
            }
            fileWriter.write("{");
            fileWriter.newLine();
            fileWriter.write("\t\tfieldLabel : " + msgPackage + column.getModelName() + ",");
            fileWriter.newLine();
            fileWriter.write("\t\tname : '" + column.getModelName() + "',");
            fileWriter.newLine();
            fileWriter.write("\t\txtype: '" + column.getColumnType().getJsEditor() + "'");
            fileWriter.newLine();
            fileWriter.write("\t} ");
            if (it.hasNext()) {
                fileWriter.write(",");
            }
        }

        fileWriter.write("]");
        fileWriter.newLine();
    }

    public void generateJsController(String rootPath, Table table) throws IOException {
        String modelName = table.getModelName();

        String dirPath = rootPath + "/controller";
        String packageName = "bsmes.controller.";
        ensureDirExists(dirPath);
        File file = new File(dirPath + "/" + modelName + "Controller.js");
        if (file.exists()) {
            return;
        }

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
        fileWriter.write("Ext.define('" + packageName + modelName + "Controller',{");
        fileWriter.newLine();
        fileWriter.write("\textend : 'Oit.app.controller.GridController',");
        fileWriter.newLine();
        fileWriter.write("\tview : '" + StringUtils.uncapitalize(modelName) + "List',");
        fileWriter.newLine();
        fileWriter.write("\teditview : '" + StringUtils.uncapitalize(modelName) + "Edit',");
        fileWriter.newLine();
        fileWriter.write("\taddview : '" + StringUtils.uncapitalize(modelName) + "Add',");
        fileWriter.newLine();
        fileWriter.write("\tviews : ['" + modelName + "List', '" + modelName + "Edit' ,'" + modelName + "Add'],");
        fileWriter.newLine();
        fileWriter.write("\tstores : ['" + modelName + "Store']");
        fileWriter.newLine();
        fileWriter.write("});");
        fileWriter.flush();
        fileWriter.close();
        svnAdd(file);
    }

    private void ensureDirExists(String dirPath) throws IOException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private void svnAdd(File file) throws IOException {
        String command = "svn add --parents " + file.getAbsolutePath();
        logger.debug(command);
        Process process = Runtime.getRuntime().exec(command);
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isColumnInCommon(String modelName) {
        return "orgCode".equals(modelName) || isColumnInBase(modelName);
    }

    private boolean isColumnInBase(String modelName) {
        return isColumnId(modelName) || "createTime".equals(modelName)
                || "modifyTime".equals(modelName) || "createUserCode".equals(modelName)
                || "modifyUserCode".equals(modelName);
    }

    private boolean isColumnId(String modelName) {
        return "id".equals(modelName);
    }

    public static void main(String[] args) throws IOException {
        BeanFilesGenerator generator = new BeanFilesGenerator("fac");
        generator.generate();
    }

}
