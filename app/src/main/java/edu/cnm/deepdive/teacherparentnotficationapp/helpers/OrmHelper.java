package edu.cnm.deepdive.teacherparentnotficationapp.helpers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.CommonDataKinds.Email;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Absence;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Present;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Student;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Tardy;
import java.util.Calendar;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;
import junit.framework.Assert;


public class OrmHelper extends OrmLiteSqliteOpenHelper {

  private static final String DATABASE_NAME = "student.db";
  private static final int DATABASE_VERSION = 1;

  private Dao <Student, Integer> studentDao = null;
  private Dao <Absence, Integer> absenceDao = null;
  private Dao <Tardy, Integer> tardiesDao = null;
  private Dao <Present, Integer> presentDao = null;
  private Dao <Email, Integer> emailDao = null;


  public OrmHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {
      TableUtils.createTable(connectionSource, Student.class);
      TableUtils.createTable(connectionSource, Absence.class);
      TableUtils.createTable(connectionSource, Tardy.class);
      TableUtils.createTable(connectionSource, Present.class);
     // TableUtils.createTable(connectionSource, Email.class);
      populateDatabase( );
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
      int newVersion) {

  }

  @Override
  public void close() {
    studentDao = null;
    super.close( );
  }

  public synchronized Dao <Student, Integer> getStudentDao() throws SQLException {
    if (studentDao == null) {
      studentDao = getDao(Student.class);
    }
    return studentDao;
  }

  public synchronized Dao <Absence, Integer> getAbsenceDao() throws SQLException {
    if (absenceDao == null) {
      absenceDao = getDao(Absence.class);

    }
    return absenceDao;
  }

  public synchronized Dao <Tardy, Integer> getTardiesDao() throws SQLException {
    if (tardiesDao == null) {
      tardiesDao = getDao(Tardy.class);
    }
    return tardiesDao;
  }

  public synchronized Dao <Present, Integer> getPresentDao() throws SQLException {
    if (presentDao == null) {
      presentDao = getDao(Present.class);
    }
    return presentDao;
  }

//  public synchronized Dao <Email, Integer> getEmailDao() throws SQLException {
//    if (emailDao == null) {
//      emailDao = getDao(Email.class);
//    }
//    return emailDao;
//  }

  private void populateDatabase()throws SQLException {
    Calendar calendar = Calendar.getInstance();
    {
      Student student = new Student();
      student.setName("Mortimer Snerd");
      student.setParent_name("Motley Crue");
      student.setContactInfo("555.555.1111");
      student.setParent_email("santoscharlie9@gmail.com");
      student.setGrade_level(7);
      getStudentDao().create(student);


      Absence absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 10, 31);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);

      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      calendar.set(2017, 10, 30);
      tardy.setDate(calendar.getTime());
      tardy.setExcused(true);
      getTardiesDao().create(tardy);




      List<Student> testList = getStudentDao().queryForAll();
      Assert.assertEquals(testList.size(), 1);
      Assert.assertEquals(testList.get(0).getAbsences().size(), 1);

    }
    {
      Student student = new Student();
      student.setName("Charlie McCarthy");
      student.setParent_name("Stanley Willow");
      student.setContactInfo("555.555.2222");
      student.setGrade_level(8);
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 5, 31);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);
      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      tardy.setDate(new Date());
      getTardiesDao().create(tardy);

      tardy = new Tardy();
      tardy.setStudent(student);
      calendar.set(2017, 10, 31);
      tardy.setDate(calendar.getTime());
      tardy.setExcused(true);
      getTardiesDao().create(tardy);

    }
    {
      Student student = new Student();
      student.setName("Jenny Sneed");
      student.setParent_name("Chris Tucker");
      student.setContactInfo("555.555.3333");
      student.setGrade_level(7);
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 1, 31);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);

      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      tardy.setDate(new Date());
      getTardiesDao().create(tardy);

      tardy = new Tardy();
      tardy.setStudent(student);
      calendar.set(2017, 4, 12);
      tardy.setDate(calendar.getTime());
      tardy.setExcused(true);
      getTardiesDao().create(tardy);

    }
    {
      Student student = new Student();
      student.setName("Johnny Tink");
      student.setParent_name("Led Zep");
      student.setContactInfo("555.666.5555");
      student.setGrade_level(8);
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 4, 14);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);

      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      tardy.setDate(new Date());
      getTardiesDao().create(tardy);

      tardy = new Tardy();
      tardy.setStudent(student);
      calendar.set(2017, 7, 15);
      tardy.setDate(calendar.getTime());
      tardy.setExcused(true);
      getTardiesDao().create(tardy);

    }
    {
      Student student = new Student();
      student.setName("Ferro McKnignt");
      student.setParent_name("Johnny Rotten");
      student.setContactInfo("555.222.1111");
      student.setGrade_level(8);
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 10, 12);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);

      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      tardy.setDate(new Date());
      getTardiesDao().create(tardy);

      tardy = new Tardy();
      tardy.setStudent(student);
      calendar.set(2017, 05, 31);
      tardy.setDate(calendar.getTime());
      tardy.setExcused(true);
      getTardiesDao().create(tardy);

    }
    {
      Student student = new Student();
      student.setName("Wibbo Needles");
      student.setParent_name("Ferrah Faucet");
      student.setContactInfo("555.333.2222");
      student.setGrade_level(7);
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 04, 21);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);

      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      tardy.setDate(new Date());
      getTardiesDao().create(tardy);

      tardy = new Tardy();
      tardy.setStudent(student);
      calendar.set(2017, 10, 31);
      tardy.setDate(calendar.getTime());
      tardy.setExcused(true);
      getTardiesDao().create(tardy);

    }
    {
      Student student = new Student();
      student.setName("Jacob Dimmler");
      student.setParent_name("Miley Cyrus");
      student.setContactInfo("555.444.3333");
      student.setGrade_level(7);
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 10, 03);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);

      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      tardy.setDate(new Date());
      getTardiesDao().create(tardy);

      tardy = new Tardy();
      tardy.setStudent(student);
      calendar.set(2017, 7, 02);
      tardy.setDate(calendar.getTime());
      tardy.setExcused(true);
      getTardiesDao().create(tardy);

    }
    {
      Student student = new Student();
      student.setName("Nancy Trenton");
      student.setParent_name("Rebecca Lou");
      student.setContactInfo("555.123.1111");
      student.setGrade_level(7);
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 11, 01);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);

      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      tardy.setDate(new Date());
      getTardiesDao().create(tardy);

      tardy = new Tardy();
      tardy.setStudent(student);
      calendar.set(2017, 7, 30);
      tardy.setDate(calendar.getTime());
      tardy.setExcused(true);
      getTardiesDao().create(tardy);

    }
    {
      Student student = new Student();
      student.setName("Nicole Friedmann");
      student.setParent_name("Margret Thacther");
      student.setContactInfo("555.999.2222");
      student.setGrade_level(7);
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 10, 02);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);

      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      tardy.setDate(new Date());
      getTardiesDao().create(tardy);

      tardy = new Tardy();
      tardy.setStudent(student);
      calendar.set(2017, 9, 01);
      tardy.setDate(calendar.getTime());
      tardy.setExcused(true);
      getTardiesDao().create(tardy);

    }
    {
      Student student = new Student();
      student.setName("Evan Aberdeen");
      student.setParent_name("Jesse Ventura");
      student.setContactInfo("555.666.6969");
      student.setGrade_level(7);
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 02, 27);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);

      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      tardy.setDate(new Date());
      getTardiesDao().create(tardy);

      tardy = new Tardy();
      tardy.setStudent(student);
      calendar.set(2017, 10, 12);
      tardy.setDate(calendar.getTime());
      tardy.setExcused(true);
      getTardiesDao().create(tardy);


      List<Student> testList = getStudentDao().queryForAll();
      Assert.assertEquals(testList.size(), 10);

    }
  }

  public interface OrmInteraction {

    OrmHelper getHelper();

  }

}