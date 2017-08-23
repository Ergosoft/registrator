package pl.com.ergosoft.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.ergosoft.register.model.Device;
import pl.com.ergosoft.register.repository.DeviceRepository;
import pl.com.ergosoft.register.utils.TimeProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class DeviceController {

    private final DeviceRepository deviceRepository;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    public DeviceController(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Device>> getAllDevices(){
        return new ResponseEntity<>((Collection<Device>) deviceRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Device> addDevice(@RequestBody Device input, HttpServletRequest request) {

        Optional.ofNullable(input.getName())
                .ifPresent(name -> Optional.ofNullable(deviceRepository.findByName(name))
                        .ifPresent(device -> input.setId(device.getId())));

        input.setLastUpdate(timeProvider.now());
        input.setIp(request.getRemoteAddr());
        return new ResponseEntity<>(deviceRepository.save(input), HttpStatus.CREATED);
    }
}
