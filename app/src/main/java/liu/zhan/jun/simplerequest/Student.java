package liu.zhan.jun.simplerequest;

import liu.zhan.jun.simplerequest.comerequest.ComeRequestIn;

/**
 * Created by 刘展俊 on 2017/4/29.
 */

public class Student implements ComeRequestIn.RequestModel<Student> {
    private Integer ID;
    private String name;
    private Integer age;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
