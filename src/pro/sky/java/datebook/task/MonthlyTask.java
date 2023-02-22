package pro.sky.java.datebook.task;

import java.time.LocalDate;

public class MonthlyTask extends Task {

    public MonthlyTask(LocalDate date, String type, String title, String description) throws IncorrectArgumentException {
        super(date, type, title, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        return getDate().getDayOfMonth() == localDate.getDayOfMonth();
    }
}
