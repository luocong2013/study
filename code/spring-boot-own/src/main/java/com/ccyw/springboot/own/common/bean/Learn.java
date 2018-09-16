package com.ccyw.springboot.own.common.bean;

import javax.persistence.*;

public class Learn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cupsize;

    private Integer age;

    private Double money;

    public Learn() {
    }

    public Learn(Integer id, String cupsize, Integer age, Double money) {
        this.id = id;
        this.cupsize = cupsize;
        this.age = age;
        this.money = money;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return cupsize
     */
    public String getCupsize() {
        return cupsize;
    }

    /**
     * @param cupsize
     */
    public void setCupsize(String cupsize) {
        this.cupsize = cupsize == null ? null : cupsize.trim();
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return money
     */
    public Double getMoney() {
        return money;
    }

    /**
     * @param money
     */
    public void setMoney(Double money) {
        this.money = money;
    }
}