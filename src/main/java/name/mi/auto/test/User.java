package name.mi.auto.test;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class User {

    private int age = 29;
    private int test = 0;
    private String name = "mkyong";
    private List<String> messages = new ArrayList<String>() {
        {
            add("msg 1");
            add("msg 2");
            add("msg 3");
        }
    };

    @JsonProperty("WhatAge")
    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    //getter and setter methods

    @Override
    public String toString() {
        return "User [age=" + age + ",test=" + test + " name=" + name + ", " +
                "messages=" + messages + "]";
    }
}