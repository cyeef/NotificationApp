package edu.cnm.deepdive.teacherparentnotficationapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import edu.cnm.deepdive.teacherparentnotficationapp.DatePickerFragment;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@DatabaseTable(tableName = "ABSENCE")
public class Absence {

  @DatabaseField(columnName = "ABSENCE_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
      format = "yyyy-MM-dd HH:mm:ss", canBeNull = false, readOnly = true)
  private Timestamp created;

  @DatabaseField(columnName = "STUDENT_ID", canBeNull = false,
      foreign = true, foreignAutoRefresh = true)
  private Student student;

  @DatabaseField(columnName = "DATE", format = "yyyy-MM-dd", canBeNull = false)
  private Date date;

  @DatabaseField(columnName = "EXCUSED", canBeNull = false)
  private boolean excused;

  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;

  @DatabaseField(columnName = "UNEXCUSED_ABSENCES", canBeNull = true)
  private String unexcused_absences;

public int getId() {
  return id;

}
public Timestamp getCreated() {
  return created;
}
public Student getStudent() {
  return student;
}
public void setStudent(Student student) {
  this.student = student;
}
public Date dgetDate() {
  return date;
}
public void setDate(Date date) {
  this.date = date;
}
public boolean isExcused() {
  return excused;
}
public void setExcused(boolean excused) {
  this.excused = excused;
}
public String getNotes() {
  return notes;
}
  public void setNotes(String notes) {
  this.notes = notes;
  }
  @Override
  public String toString() {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return String.format("%s (%s)", format.format(date),
        excused ? "excused" : "not excused");
  }
}
