package pl.com.ergosoft.register.repository;


import org.springframework.data.repository.CrudRepository;
import pl.com.ergosoft.register.model.Device;

public interface DeviceRepository extends CrudRepository<Device, Long>{
    Device findByName(String name);
}
