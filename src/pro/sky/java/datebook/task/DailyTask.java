package pro.sky.java.datebook.task;

import java.time.LocalDate;

public class DailyTask extends Task {

    public DailyTask(LocalDate date, String type, String title, String description) throws IncorrectArgumentException {
        super(date, type, title, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        return true;
    }
}
