package pl.com.ergosoft.register.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.ergosoft.register.model.Device;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DeviceController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/device")
    public Device greeting(@RequestParam(value="name", defaultValue="Name") String name) {
        return new Device(counter.incrementAndGet(), name);
    }
}
