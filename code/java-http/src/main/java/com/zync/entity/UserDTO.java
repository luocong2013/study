package com.zync.entity;

/**
 * 用户传输对象
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/30 16:34
 */
public class UserDTO {

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名称
     */
    private String name;

    public UserDTO() {
    }

    public UserDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
