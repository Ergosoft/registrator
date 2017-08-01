package pl.com.ergosoft.register.model;

import lombok.Getter;

@Getter
public class Device {

    private final long id;
    private final String name;

    public Device(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
