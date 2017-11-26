package edu.cnm.deepdive.teacherparentnotficationapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


@DatabaseTable(tableName = "STUDENT")
public class Student {

  @DatabaseField(columnName = "STUDENT_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
      format = "yyyy-MM-dd HH:mm:ss", canBeNull = false, readOnly = true)
  private Timestamp created;


  @DatabaseField(columnName = "NAME", canBeNull = false)
  private String name;

  @ForeignCollectionField
  public ForeignCollection<Absence> absences;

  @ForeignCollectionField
  public ForeignCollection<Tardy> tardies;


  @DatabaseField(columnName = "PARENT_NAME", canBeNull = false)

  private String parent_name;

  @DatabaseField(columnName = "PARENT_NUMBER", canBeNull = false)
  private String parent_number;

  @DatabaseField(columnName = "PARENT_EMAIL", canBeNull = true)
  private String parent_email;

  @DatabaseField(columnName = "ALTERNATIVE_NUMBER", canBeNull = true)
  private String alternative_number;

  @DatabaseField(columnName = "GRADE_LEVEL", canBeNull = false)
  private int grade_level;


  public int getId() {
    return id;
  }

  public Timestamp getCreated() {
    return created;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ForeignCollection<Absence> getAbsences() {
    return absences;
  }
  public void setAbsences(
      ForeignCollection<Absence> absences) {
    this.absences = absences;
  }

  public ForeignCollection<Tardy> getTardies() {
    return tardies;
  }

  public void setTardies(
      ForeignCollection<Tardy> tardies) {
    this.tardies = tardies;
  }

  public String getParent_name() {
    return parent_name;
  }

  public void setParent_name(String parent_name) {
    this.parent_name = parent_name;
  }

  public String getParent_number() {
    return parent_number;
  }

  public void setContactInfo(String parent_number) {
    this.parent_number = parent_number;
  }

  public String getParent_email() {
    return parent_email;
  }

  public void setParent_email(String parent_email) {
    this.parent_email = parent_email;
  }

  public String getAlternative_number() {
    return alternative_number;
  }

  public void setAlternative_number(String alternative_number) {
    this.alternative_number = alternative_number;
  }

  public int getGrade_level() {
    return grade_level;
  }

  public void setGrade_level(int grade_level) {
    this.grade_level = grade_level;
  }

  @Override
  public String toString() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("name", name);
    map.put("parentname", parent_name);
    map.put("parentnumber", parent_number);
    map.put("parentemail", parent_email);
    map.put("alternativenumber", alternative_number);
    map.put("gradelevel", grade_level);
    map.put("created", created);
    return map.toString();
  }

}
