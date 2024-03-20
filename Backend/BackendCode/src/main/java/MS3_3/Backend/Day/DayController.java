package MS3_3.Backend.Day;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DayController {
    @Autowired
    DayRepository dayRepository;

    @PostMapping("/Day/Create")
    public Day createDay(@RequestBody Day day) {
        dayRepository.save(day);
        return day;
    }

    @DeleteMapping("/Day/Delete/{destination}")
    public String deleteDay(@PathVariable String destination) {
        dayRepository.deleteById(destination);
        return "Day with destination " + destination + " successfully deleted";
    }

    @PutMapping("/Day/Update/{destination}")
    public Day updateDay(@PathVariable String uniqueCode, @RequestBody Day updatedDay) {
        Day existingDay = dayRepository.findById(uniqueCode).orElse(null);
        if (existingDay != null) {
            existingDay.setPlaces(updatedDay.getPlaces());
            dayRepository.save(existingDay);
            return existingDay;
        } else {
            return null;
        }
    }

    @GetMapping("/Day/{destination}")
    public Day getDay(@PathVariable String destination) {
        return dayRepository.findById(destination).orElse(null);
    }

    @GetMapping("/Day/List")
    public List<Day> getAllDays() {
        return dayRepository.findAll();
    }
}

