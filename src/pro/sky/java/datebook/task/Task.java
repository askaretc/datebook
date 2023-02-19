package pro.sky.java.datebook.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Task {

    private static int idGenerator = 0;
    private final int id;
    private final LocalDateTime dateTimeOfTheTaskCreation;
    private final LocalDate date;
    private final Type type;
    private String title;
    private String description;


    public Task(LocalDate date, String type, String title, String description) throws IncorrectArgumentException {
        this.title = title;
        if (isCorrectType(type)) {
            this.type = Type.valueOf(type);
        } else {
            throw new IncorrectArgumentException(type);
        }
        this.description = description;
        this.date = date;
        dateTimeOfTheTaskCreation = LocalDateTime.now();
        id = idGenerator++;
    }


    public abstract boolean appearsIn(LocalDate localDate);


    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getDateTimeOfTheTaskCreation() {
        return dateTimeOfTheTaskCreation;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "\n" + getType() + " TASK [ID:" + getId() + "] " +
                getDateTimeOfTheTaskCreation().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) +
                "\n" + getTitle().toUpperCase() +
                "\n" + getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    private boolean isCorrectType(String typeOfTheTask) {
        for (Type t : Type.values()) {
            if (typeOfTheTask.equals(t.name())) {
                return true;
            }
        }
        return false;
    }
}
