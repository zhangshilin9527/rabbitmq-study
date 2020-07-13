package com.xiaolinzi.rabbitmq.product.model;

/**
 * @author ：xiaolinzi
 * @date ：2020-07-13 14:46
 * @email: xiaolinzi95_27.com
 */
public class SendInfo {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"age\":\"")
                .append(age).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
