package edu.cnm.deepdive.teacherparentnotficationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;


import com.j256.ormlite.android.apptools.OpenHelperManager;

import com.j256.ormlite.table.TableUtils;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Present;
import edu.cnm.deepdive.teacherparentnotficationapp.entities.Student;
import edu.cnm.deepdive.teacherparentnotficationapp.helpers.OrmHelper;
import edu.cnm.deepdive.teacherparentnotficationapp.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * An activity representing a list of Students. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link StudentDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two vertical panes.
 */
public class StudentListActivity
    extends AppCompatActivity
    implements OrmInteraction, OnCheckedChangeListener {
  private static final String LOG_TAG = "STUDENT_LIST_ACTIVITY";

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
   */
  private boolean mTwoPane;
  private OrmHelper helper = null;
  private RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getHelper().getWritableDatabase();
    setContentView( R.layout.activity_student_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

  //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //fab.setOnClickListener(new View.OnClickListener() {
   //   @Override
   //   public void onClick(View view) {
    //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
     //       .setAction("Action", null).show();
   //   }
  //  });

     recyclerView = (RecyclerView) findViewById(R.id.student_list);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView);

    if (findViewById(R.id.student_detail_container) != null) {
      // The detail container view will be present only in the
      // large-screen layouts (res/values-w900dp).
      // If this view is present, then the
      // activity should be in two-pane mode.
      mTwoPane = true;
    }
  }

  protected void onStart() {
    super.onStart();
    getHelper();
  }

  protected void onStop() {
    super.onStop();
    releaseHelper();
  }

  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
    try {
      recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter
          (getHelper().getStudentDao().queryForAll()));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public OrmHelper getHelper() {
    if (helper == null) {
      helper = OpenHelperManager.getHelper(this, OrmHelper.class);

    }
    return helper;
  }

  public synchronized void releaseHelper() {
    if (helper != null) {
      OpenHelperManager.releaseHelper();
      helper = null;
    }

  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


  }


  public class SimpleItemRecyclerViewAdapter
      extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final List<Student> mValues;


    public SimpleItemRecyclerViewAdapter(List<Student> items) {
      mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.student_list_content, parent, false);
  //    ((CheckBox)view.findViewById(R.id.present_checkbox)).setOnCheckedChangeListener(StudentListActivity.this);

      return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      holder.mItem = mValues.get(position);
      holder.mStudentView.setText(mValues.get(position).getName());
    //  holder.mCreatedView.setText(mValues.get(position).getCreated().toString());
      final CheckBox present_checkbox = holder.mView.findViewById(R.id.present_checkbox);
      present_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener( ) {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          Present present = new Present();
          present.setStudent(mValues.get(position));
          present.setDate(new Date( ));

          try {
            getHelper().getPresentDao().create(present);
          } catch (SQLException e) {
            e.printStackTrace( );
          }
        }
      });
      holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(StudentDetailFragment.STUDENT_ID, holder.mItem.getId());
            StudentDetailFragment fragment = new StudentDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.student_detail_container, fragment)
                .commit();
          } else {
            Context context = v.getContext();
            Intent intent = new Intent(context, StudentDetailActivity.class);
            intent.putExtra(StudentDetailFragment.STUDENT_ID, holder.mItem.getId());
            context.startActivity(intent);
          }
        }
      });
      holder.mRecordAbsences.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Context context = v.getContext();
            Intent intent = new Intent(context, StudentDetailActivity.class);
            intent.putExtra(StudentDetailFragment.STUDENT_ID, holder.mItem.getId());
            context.startActivity(intent);
          }

      });
    }

    @Override
    public int getItemCount() {
      return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      public final View mView;
      public final TextView mStudentView;
      // public final TextView mCreatedView;
      public final TextView mParentName;
      public final TextView mParentNumber;
      public final TextView mParentEmail;
      public final TextView mAlternativeNumber;
      public final TextView mGradeLevel;
      public final TextView mmAbsences;
      public final TextView mUnexcusedAbsences;
     // public final TextView mTardies;
      public Student mItem;
      public final Button mRecordAbsences;






      public ViewHolder(View view) {
        super(view);
        mView = view;
        mStudentView = (TextView) view.findViewById(R.id.student_name);
      //  mCreatedView = (TextView) view.findViewById(R.id.student_created);
        mParentName = (TextView) view.findViewById(R.id.parent_name);
        mParentNumber = (TextView) view.findViewById(R.id.parent_number);
        mParentEmail = (TextView) view.findViewById(R.id.parent_email);
        mAlternativeNumber = (TextView) view.findViewById(R.id.alternative_number);
        mGradeLevel = (TextView) view.findViewById(R.id.grade_level);
        mmAbsences = (TextView) view.findViewById(R.id.absences);
        mUnexcusedAbsences =  view.findViewById(R.id.unexcused_absences);
        mRecordAbsences = (Button)view.findViewById(R.id.edit_attendance);
        //mTardies = (TextView) view.findViewById(R.id.tardies);

      }

      @Override
      public String toString() {

        return super.toString() + " '" + mStudentView.getText() + "'";
      }
    }
  }
}
