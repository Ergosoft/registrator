package pl.com.ergosoft.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.com.ergosoft.register.model.Device;
import pl.com.ergosoft.register.repository.DeviceRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class DeviceController {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceController(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Device>> getAllDevices(){
        return new ResponseEntity<>((Collection<Device>) deviceRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Device> addDevice(@RequestBody Device input, HttpServletRequest request) {

        Optional.ofNullable(input.getName())
                .ifPresent(name -> Optional.ofNullable(deviceRepository.findByName(name))
                        .ifPresent(device -> input.setId(device.getId())));

        input.setLastUpdate(LocalDateTime.now());
        input.setIp(request.getRemoteAddr());
        return new ResponseEntity<>(deviceRepository.save(input), HttpStatus.CREATED);
    }
}
