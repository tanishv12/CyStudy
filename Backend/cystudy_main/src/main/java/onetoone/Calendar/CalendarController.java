package onetoone.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {

    @Autowired
    CalendarRepository calendarRepository;

//    @GetMapping(path="/calender/{userName}")
}
