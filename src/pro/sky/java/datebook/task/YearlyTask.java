package pro.sky.java.datebook.task;

import java.time.LocalDate;

public class YearlyTask extends Task {

    public YearlyTask(LocalDate date, String type, String title, String description) throws IncorrectArgumentException {
        super(date, type, title, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        return getDate().getMonthValue() == localDate.getMonthValue() &&
                getDate().getDayOfMonth() == localDate.getDayOfMonth();
    }
}
