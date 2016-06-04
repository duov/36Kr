package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreendaoClass {

    public static void main(String[] args) {
        //首先要创建一个数据库图表对象

        /**数据库图表(数据库的框架)
         //两个参数,
         //第一个参数是数据库的版本号
         //第二个参数是我自动生成代码的包名**/
        Schema schema = new Schema(1, "com.liangduo.greendao");
        //添加需要创建的实体类
        addNote(schema);
        //自动生成代码
        //两个参数,第一个参数图表对象,第二个参数是生成代码的路径
        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //对框架进行操作

    /**
     * 此方法就是为我数据库里添加我所需要的内容
     * (添加将要创建的实体类的信息,会根据类名生成数据库的表,属性名生成数据库的字段)
     * (如果想建多张表,可以创建多个Entity对象)
     *
     * @param schema
     */
    public static void addNote(Schema schema) {
        //添加表名
        Entity entity = schema.addEntity("Collection");
        //加入id
        //并且id自增
        entity.addIdProperty().autoincrement().primaryKey();
        //添加类属性,根据属性生成相应表中字段
        entity.addStringProperty("imageUrl");
        entity.addStringProperty("title");
        entity.addStringProperty("name");
        entity.addStringProperty("date");
        entity.addStringProperty("tag");
        entity.addStringProperty("feedId");

        Entity entity1 = schema.addEntity("User");
        entity1.addIdProperty().autoincrement().primaryKey();
        entity1.addStringProperty("number");
        entity1.addStringProperty("psw");

    }
}
