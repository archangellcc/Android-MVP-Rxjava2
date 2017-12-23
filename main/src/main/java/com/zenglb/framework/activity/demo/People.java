package com.zenglb.framework.activity.demo;

import javax.inject.Inject;

/**
 * Created by zlb on 2017/12/14.
 */
public class People {
    String name;

    @Inject
    public People(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
