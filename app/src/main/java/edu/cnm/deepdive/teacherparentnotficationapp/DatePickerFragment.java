package edu.cnm.deepdive.teacherparentnotficationapp;


import static edu.cnm.deepdive.teacherparentnotficationapp.R.id.edit_attendance;
import static edu.cnm.deepdive.teacherparentnotficationapp.R.id.mdtp_day_picker_selected_date_layout;
import static edu.cnm.deepdive.teacherparentnotficationapp.R.id.new_absence_date;
import static edu.cnm.deepdive.teacherparentnotficationapp.StudentDetailFragment.DATE_FIELD_KEY;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import edu.cnm.deepdive.teacherparentnotficationapp.directmessage.SendSMSActivity;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Absence;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Student;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Tardy;
import edu.cnm.deepdive.teacherparentnotficationapp.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {


  private int fieldId;
  private EditText newAbsenceDate;
  private EditText newTardyDate;
  private Student student;
  private EditText date;


  public Dialog onCreateDialog(Bundle savedInstanceState) {
    //use the current date as the default date
    final Calendar c = Calendar.getInstance( );
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    fieldId = getArguments( ).getInt(DATE_FIELD_KEY);

    //create a new instance of DatePickerDialog and return it
    return new DatePickerDialog(getActivity( ), this, year, month, day);
  }


  @Override
  public void onStart() {
    super.onStart( );

  }


  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


    Calendar calendar = Calendar.getInstance( );
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month );
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

    ((EditText) getActivity( ).findViewById(fieldId)).setText(sdf.format(calendar.getTime( )));
   // Toast.makeText(getActivity( ), "Absence Created", Toast.LENGTH_LONG).show( );


  }



 /* public void onClick(View v) {

    int onDateSet;
    if (v.getId( ) == onDateSet(getActivity(), new Absence())){
      Absence absence = new Absence( );
      absence.setStudent(student);
      absence.setExcused(onDateSet(new DatePicker(EditText)).findViewById(R.id.new_absence_date));
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
      try {
        absence.setDate(sdf.parse(
            ((EditText) getActivity( ).findViewById(new_absence_date)).getText( )
                .toString( )));
      } catch (ParseException e) {
        e.printStackTrace( );
        absence.setDate(new Date( ));
      }
      try {
        ((OrmInteraction) getActivity( )).getHelper( ).getAbsenceDao( ).create(absence);
        Toast.makeText(getActivity( ), "Absence Created", Toast.LENGTH_LONG).show( );
      } catch (SQLException e) {
        e.printStackTrace( );
      }
    } else if (v.getId( ) == R.id.new_tardy_button) {
      Tardy tardy = new Tardy( );
      tardy.setStudent(student);
      tardy
          .setExcused(((Button) getActivity( ).findViewById(R.id.new_tardy_button)).isClickable( ));
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
      try {
        tardy.setDate(sdf.parse(
            ((EditText) getActivity( ).findViewById(R.id.new_tardy_date)).getText( ).toString( )));
      } catch (ParseException e) {
        e.printStackTrace( );
        tardy.setDate(new Date( ));
      }
      try {
        ((OrmInteraction) getActivity( )).getHelper( ).getTardiesDao( ).create(tardy);
        Toast.makeText(getActivity( ), "Tardy Created", Toast.LENGTH_LONG).show( );
      } catch (SQLException e) {
        e.printStackTrace( );
      }


    } else if ((v.getId( ) == R.id.notify_parent_button)) {
      Intent intent = new Intent(getActivity( ), SendSMSActivity.class);
      getActivity( ).startActivity(intent);

    } else {
      Bundle bundle = new Bundle( );
      bundle.putInt(DATE_FIELD_KEY, v.getId( ));
      DialogFragment newFragment = new DatePickerFragment( );
      Toast.makeText(getActivity( ), "Absence Created", Toast.LENGTH_LONG).show( );
      Toast.makeText(getActivity( ), "Tardy Created", Toast.LENGTH_LONG).show( );
      newFragment.setArguments(bundle);
      newFragment.show(getFragmentManager( ), "DatePicker");

    }

  }

  private int onDateSet(FragmentActivity activity,
      Absence absence) {
    return 0;
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
*///}
}

