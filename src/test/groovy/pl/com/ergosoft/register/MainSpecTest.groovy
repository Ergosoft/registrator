package pl.com.ergosoft.register

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.com.ergosoft.register.model.Device
import spock.lang.Specification

@SpringBootTest(classes = RegisterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeviceControllerTest extends Specification {

    @Autowired
    private ApplicationContext ctx

    protected final static String ENDPOINT_URL = "/"

    protected ResponseEntity<String> response

    @Autowired
    protected TestRestTemplate restTemplate

    def "main url responses with 200 OK"() {
        given:
        when:
            response = restTemplate.getForEntity(ENDPOINT_URL, String.class)
        then:
            response.getStatusCode() == HttpStatus.OK
    }
    
    def "register new device responses with 201 OK"() {
        given:
            Device device = new Device()
            device.setName("test")
        when:
            ResponseEntity<Device> response = restTemplate.postForEntity(ENDPOINT_URL, device , Device.class)
        then:
            response.getStatusCode() == HttpStatus.CREATED
            Device newDevice = (Device)response.getBody()
            newDevice.getId() == 1
            newDevice.getName() == "test"
    }
}
