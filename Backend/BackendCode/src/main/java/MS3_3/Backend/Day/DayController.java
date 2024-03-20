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

    @DeleteMapping("/Day/Delete/{uniqueCode}")
    public String deleteDay(@PathVariable int uniqueCode) {
        dayRepository.deleteByUniqueCode(uniqueCode);
        return "Day with Unique Code " + uniqueCode + " successfully deleted";
    }

    @PutMapping("/Day/Update/{uniqueCode}")
    public Day updateDay(@PathVariable int uniqueCode, @RequestBody Day updatedDay) {
        Day existingDay = dayRepository.findByUniqueCode(uniqueCode);
        if (existingDay != null) {
            existingDay.setPlaces(updatedDay.getPlaces());
            dayRepository.save(existingDay);
            return existingDay;
        } else {
            return null;
        }
    }

    @GetMapping("/Day/{uniqueCode}")
    public Day getDay(@PathVariable int uniqueCode) {
        return dayRepository.findByUniqueCode(uniqueCode);
    }

    @GetMapping("/Day/List")
    public List<Day> getAllDays() {
        return dayRepository.findAll();
    }
}

