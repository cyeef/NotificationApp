package edu.cnm.deepdive.teacherparentnotficationapp.directmessage;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.cnm.deepdive.teacherparentnotficationapp.R;

public class SendSMSActivity extends Activity {








    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      Button buttonSend;
      buttonSend = (Button) findViewById(R.id.buttonSend);

      buttonSend.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {

          try {

            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", "default content");
            sendIntent.setType("vnd.android-dir/mms-sms");
            startActivity(sendIntent);

          } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                "SMS faild, please try again later!",
                Toast.LENGTH_LONG).show();
            e.printStackTrace();
          }
        }
      });
    }
  }






