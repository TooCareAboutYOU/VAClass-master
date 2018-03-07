package com.dts.vaclass.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by zs on 2018/2/2.
 */

@Entity(
//        schema = "mychema",   //告知greenDao当前实体类属于哪个schema
//        active = true,         //标记一个实体类处于活动状态，活动实体有更新、删除、刷新方法
//        nameInDb = "AWESOME_USER", //在数据中使用的别名，默认使用的是实体的类名
//        indexes = {
//                @Index(value = "name DESC",unique = true)
//        },
//        createInDb = false,
//        generateConstructors = true,
//        generateGettersSetters = true
)
public class UserDataBean {
    @Id   //  @Id(autoincrement = true) 主键Long型，设置自增长
    private Long id;
    //@Property(nameInDb = "UserName")  //数据库别名
    private String name;
//    @NotNull  //设置数据库当前列不能为空
    private int age;
//    @Transient  //数据库不会生成此列
//    private String mark;



    @Generated(hash = 422955855)
    public UserDataBean(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 1684300321)
    public UserDataBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":").append(id).append('\"');
        sb.append(",\"name\":\"").append(name).append('\"');
        sb.append(",\"age\":").append(age).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
