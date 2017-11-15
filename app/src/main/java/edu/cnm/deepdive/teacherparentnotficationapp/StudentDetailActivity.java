package edu.cnm.deepdive.teacherparentnotficationapp;

import static android.content.DialogInterface.*;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import edu.cnm.deepdive.teacherparentnotficationapp.helpers.OrmHelper;
import edu.cnm.deepdive.teacherparentnotficationapp.helpers.OrmHelper.OrmInteraction;


/**
 * An activity representing a single Student detail screen. This activity is only used narrow width
 * devices. On tablet-size devices, item details are presented side-by-side with a list of items in
 * a {@link StudentListActivity}.
 */

//public class StudentDetailActivity extends AppCompatActivity
//  implements StudentDetailFragment.StudentDetailFragmentDaoInteraction {
//
//    private OrmHelper helper = null;

public class StudentDetailActivity
    extends AppCompatActivity
    implements OrmInteraction {

  private static final String LOG_TAG = "STUDENT_DETAIL_ACTIVITY";
  private static final int DATE_DIALOG_ID = 1;
  private OrmHelper helper = null;

  private TextView tvDisplayDate;
  private DatePicker dpResult;
  private int year;
  private int month;
  private int day;
  private Button btnChangeDate;
  private Context activity;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_student_detail );
    Toolbar toolbar = (Toolbar) findViewById( R.id.detail_toolbar );
    setSupportActionBar( toolbar );

    //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//  //  fab.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show();
//      }
//    });

    // Show the Up button in the action bar.
    ActionBar actionBar = getSupportActionBar( );
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled( true );
    }

    // savedInstanceState is non-null when there is fragment state
    // saved from previous configurations of this activity
    // (e.g. when rotating the screen from portrait to landscape).
    // In this case, the fragment will automatically be re-added
    // to its container so we don't need to manually add it.
    // For more information, see the Fragments API guide at:
    //
    // http://developer.android.com/guide/components/fragments.html
    //
    if (savedInstanceState == null) {
      // Create the detail fragment and add it to the activity
      // using a fragment transaction.
      Bundle arguments = new Bundle( );
      arguments.putInt( StudentDetailFragment.STUDENT_ID,
          getIntent( ).getIntExtra( StudentDetailFragment.STUDENT_ID, 0 ) );
      StudentDetailFragment fragment = new StudentDetailFragment( );
      fragment.setArguments( arguments );
      getSupportFragmentManager( ).beginTransaction( )
          .add( R.id.student_detail_container, fragment )
          .commit( );
    }
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId( );
    if (id == android.R.id.home) {
      onBackPressed( );
      return true;
      // This ID represents the Home or Up button. In the case of this
      // activity, the Up button is shown. For
      // more details, see the Navigation pattern on Android Design:
      //
      // http://developer.android.com/design/patterns/navigation.html#up-vs-back
      //

    }
    return super.onOptionsItemSelected( item );
  }

  @Override
  protected void onStart() {
    super.onStart( );
    getHelper( );
  }

  @Override
  protected void onStop() {
    releaseHelper( );
    super.onStop( );
  }

  public synchronized OrmHelper getHelper() {
    if (helper == null) {
      helper = OpenHelperManager.getHelper( this, OrmHelper.class );

    }
    return helper;
  }

  public synchronized void releaseHelper() {
    if (helper != null) {
      OpenHelperManager.releaseHelper( );
      helper = null;
    }


  }
  public AlertDialog populateAbsence(int yy, int mm, int dd) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage(R.string.absense_alert)
        .setPositiveButton(R.string.yes, new OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {

          }
        })
        .setNegativeButton(R.string.no, new OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder( getActivity( ) );

// 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage( R.string.yes )
                .setTitle( R.string.absense_alert );

// 3. Get the AlertDialog from create()


          }
        });

    return builder.create();
  }
  public AlertDialog populateTardies(int yy, int mm, int dd) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage(R.string.tardy_alert)
        .setPositiveButton(R.string.yes, new OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {

          }
        })
        .setNegativeButton(R.string.no, new OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {

          }
        });
    return builder.create();

  }


  public Context getActivity() {
    return activity;
  }
}
