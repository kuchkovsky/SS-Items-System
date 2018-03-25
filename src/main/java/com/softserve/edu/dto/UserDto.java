package com.softserve.edu.dto;

import com.softserve.edu.entity.UserEntity;

public class UserDto {

    private String login;
    private String password;
    private String firstName;
    private String lastName;

    public UserDto(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto(UserEntity userEntity) {
        login = userEntity.getLogin();
        password = userEntity.getPassword();
        firstName = userEntity.getFirstName();
        lastName = userEntity.getLastName();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullUserName() {
        return firstName + " " + lastName;
    }

}
