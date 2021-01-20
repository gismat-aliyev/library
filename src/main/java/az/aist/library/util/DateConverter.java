package az.aist.library.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class DateConverter {

    public Date stringToDate(String stringDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
            log.error(""+e);
        }
        return date;
    }

}
