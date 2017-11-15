package edu.cnm.deepdive.teacherparentnotficationapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Absence;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Student;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Tardy;
import edu.cnm.deepdive.teacherparentnotficationapp.helpers.OrmHelper;
import edu.cnm.deepdive.teacherparentnotficationapp.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A fragment representing a single Student detail screen. This fragment is either contained in a
 * {@link StudentListActivity} in two-pane mode (on tablets) or a {@link StudentDetailActivity} on
 * handsets.
 */
public class StudentDetailFragment extends Fragment implements OnClickListener {

  /**
   * The fragment argument representing the item ID that this fragment represents.
   */
  public static final String STUDENT_ID = "student_id";
  public static final String DATE_FIELD_KEY = "date_field";

  /**
   * The dummy content this fragment is presenting.
   */
  private int studentId;
  private Student student;
  private OrmHelper helper;
  private View rootView;
  private ListView absenceList;
  private ArrayAdapter<Absence> absenceAdapter;
  private Student mItem;
  private View imageAbsenseButton;
  private View imageTardyButton;
  private View newAbsenseDate;
  private View newTardyDate;
  private View newAbsenseButton;
  private View newTardyButton;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
   * screen orientation changes).
   */
  // public StudentDetailFragment() {
  //}

  // Load the dummy content specified by the fragment} // arguments. In a real-world scenario, use a Loader
  // to load content from a content provider.
  //   try {
  //   mItem = ((StudentDetailFragmentDaoInteraction) getContext())
  // .getDao().queryForId(getArguments().getInt(ARG_ITEM_ID));
  // } catch (SQLException e) {
  //   throw new RuntimeException(e);
  //   }

  //  Activity activity = this.getActivity();
  // CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity
  //     .findViewById(R.id.toolbar_layout);
  //  if (appBarLayout != null) {
  //   appBarLayout.setTitle(mItem.getName());
  //  }
  //  }
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    studentId = getArguments( ).getInt( STUDENT_ID, 0 );
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate( R.layout.fragment_student_detail, container, false );
    absenceList = rootView.findViewById( R.id.absence_list );
    absenceAdapter = new ArrayAdapter<>( getContext( ), R.layout.absence_list_item );
    absenceList.setAdapter( absenceAdapter );
    imageAbsenseButton = rootView.findViewById( R.id.input_absence );
    imageTardyButton = rootView.findViewById( R.id.input_tardies );
    imageAbsenseButton.setOnClickListener( this );
    imageTardyButton.setOnClickListener( this );
    newAbsenseDate = rootView.findViewById( R.id.new_absence_date );
    newTardyDate = rootView.findViewById( R.id.new_tardy_date );
    newAbsenseDate.setOnClickListener( this );
    newTardyDate.setOnClickListener( this );
    newAbsenseButton = rootView.findViewById( R.id.new_absence_button );
    newTardyButton = rootView.findViewById( R.id.new_tardy_button );
    newAbsenseButton.setOnClickListener( this );
    newTardyButton.setOnClickListener( this );

    helper = ((OrmHelper.OrmInteraction) getActivity( )).getHelper( );
    if (studentId > 0) {
      try {
        Dao<Student, Integer> studentDao = helper.getStudentDao( );
        Dao<Absence, Integer> absenceDao = helper.getAbsenceDao( );
        Dao<Tardy, Integer> tardiesDao = helper.getTardiesDao( );
        student = studentDao.queryForId( studentId );
        ((TextView) rootView.findViewById( R.id.student_id ))
            .setText( Integer.toString( student.getId( ) ) );
        ((EditText) rootView.findViewById( R.id.student_name )).setText( student.getName( ) );
        ((TextView) rootView.findViewById( R.id.student_created ))
            .setText( student.getCreated( ).toString( ) );
        // ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle(student.getName());
        QueryBuilder<Absence, Integer> builder = absenceDao.queryBuilder( );
        builder.where( ).eq( "STUDENT_ID", student.getId( ) );
        builder.orderBy( "DATE", true );
        ArrayAdapter<Absence> adapter
            = new ArrayAdapter<>( getContext( ), R.layout.absence_list_item,
            absenceDao.query( builder.prepare( ) ) );
        ((ListView) rootView.findViewById( R.id.absence_list )).setAdapter( adapter );
        ((ListView) rootView.findViewById( R.id.absence_list )).invalidateViews( );
        QueryBuilder<Tardy, Integer> tardiesBuilder = tardiesDao.queryBuilder( );
        tardiesBuilder.where( ).eq( "STUDENT_ID", student.getId( ) );
        tardiesBuilder.orderBy( "DATE", true );
        ArrayAdapter<Tardy> tardiesAdapter
            = new ArrayAdapter<>( getContext( ), R.layout.absence_list_item,
            tardiesDao.query( tardiesBuilder.prepare( ) ) );
        ((ListView) rootView.findViewById( R.id.tardies_list )).setAdapter( tardiesAdapter );
        ((ListView) rootView.findViewById( R.id.tardies_list )).invalidateViews( );

      } catch (SQLException e) {
        throw new RuntimeException( e );
      }
    } else {
      student = null;
    }

    return rootView;

  }

  // Show the dummy content as text in a TextView.
  // if (mItem != null) {
  //   ((TextView) rootView.findViewById(R.id.student_detail)).setText(mItem.toString());
  // }
  //     return rootView;

  @Override
  public void onStart() {
    super.onStart( );

  }

  @Override
  public void onClick(View v) {

    if (v.getId( ) == R.id.new_absence_button) {
     Absence absence = new Absence();
     absence.setStudent(student);
     absence.setExcused(((CheckBox)getActivity().findViewById(R.id.unexcused_checkbox_absence)).isChecked());
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
      try {
        absence.setDate(sdf.parse(((EditText)getActivity().findViewById(R.id.new_absence_date)).getText().toString()));
      } catch (ParseException e) {
        e.printStackTrace( );
      }
      try {
        ((OrmInteraction)getActivity()).getHelper().getAbsenceDao().create(absence);
      } catch (SQLException e) {
        e.printStackTrace( );
      }
    } else if(v.getId() == R.id.new_tardy_button) {
      Tardy tardy = new Tardy();
      tardy.setStudent(student);
      tardy.setExcused(((CheckBox)getActivity().findViewById(R.id.tardies_checkbox)).isChecked());
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
      try {
        tardy.setDate(sdf.parse(((EditText)getActivity().findViewById(R.id.new_tardy_date)).getText().toString()));
      } catch (ParseException e) {
        e.printStackTrace( );
      }
      try {
        ((OrmInteraction)getActivity()).getHelper().getTardiesDao().create(tardy);
      } catch (SQLException e) {
        e.printStackTrace( );
      }

    }else {
      Bundle bundle = new Bundle( );
      bundle.putInt( DATE_FIELD_KEY, v.getId( ) );
      DialogFragment newFragment = new DatePickerFragment( );
      newFragment.setArguments( bundle );
      newFragment.show( getFragmentManager( ), "DatePicker" );

    }


  }
}




