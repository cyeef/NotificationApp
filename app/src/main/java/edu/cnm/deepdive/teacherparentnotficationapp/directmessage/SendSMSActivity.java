package edu.cnm.deepdive.teacherparentnotficationapp.directmessage;

import android.Manifest;
import android.Manifest.permission;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.cnm.deepdive.teacherparentnotficationapp.R;

public abstract class SendSMSActivity extends AppCompatActivity {

  private  Object context;
  private Object intent;
  EditText parent_number;
  EditText insert_message;

  int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

  String SENT = "SMS_SENT";
  String DELIVERED = "SMS_DELIVERED";
  PendingIntent sentPI, deliveredPI;
  BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.message_layout );

    parent_number = (EditText) findViewById( R.id.parent_number );
    insert_message = (EditText) findViewById( R.id.insert_message );

    sentPI = PendingIntent.getBroadcast( this, 0, new Intent( SENT ), 0);
    deliveredPI = PendingIntent.getBroadcast( this, 0, new Intent( DELIVERED ),0 );
  }

  @Override
  protected void onResume() {
    super.onResume( );

    smsSentReceiver = new BroadcastReceiver( ) {
      @Override
      public void onReceive(Context context, Intent intent) {

        switch (getResultCode())
        {
          case Activity.RESULT_OK:
            Toast.makeText( MainActivity.this, "SMS sent", Toast.LENGTH_SHORT).show();
            break;

          case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            Toast.makeText( MainActivity.this, "Generic failure!", Toast.LENGTH_SHORT ).show();
            break;

          case SmsManager.RESULT_ERROR_NO_SERVICE:
            Toast.makeText( MaintActivity.this, "No service!", Toast.LENGTH_SHORT ).show();
            break;

          case SmsManager.RESULT_ERROR_NULL_PDU:
            Toast.makeText( MainActivity.this, "Null PDU", Toast.LENGTH_SHORT ).show();
            break;

          case SmsManager.RESULT_ERROR_RADIO_OFF:
            Toast.makeText(MainActivity.this, "Radio Off", Toast.LENGTH_SHORT ).show();
            break;
        }
    };

    smsDeliveredReceiver = new BroadcastReceiver( ) {
      @Override
      public void onReceive(Context context, Intent intent) {

        switch (getResultCode())
        {
          case Activity.RESULT_OK:
            Toast.makeText( MainActivity.this, "SMS delivered", Toast.LENGTH_SHORT ).show();
            break;
          case Activity.RESULT_CANCELED:
            Toast.makeText( MainActivity.this, "SMS not delivered", Toast.LENGTH_SHORT ).show();
            break;
      }
    };

    registerReceiver( smsSentReceiver, new IntentFilter( SENT ) );
    registerReceiver( smsDeliveredReceiver, new IntentFilter( DELIVERED ) );
      }

      @Override
      protected void onPause() {
      SendSMSActivity.super.onPause();

      unregisterReceiver( smsDeliveredReceiver );
      unregisterReceiver( smsSentReceiver );
    }

  public void setButtonSend(View view)

  {

    String number = parent_number.getText( ).toString( );
    String message = insert_message.getText( ).toString( );

    if (ContextCompat.checkSelfPermission( this, permission.SEND_SMS )
        != PackageManager.PERMISSION_GRANTED)
    {
      ActivityCompat.requestPermissions( this, new String[]{permission.SEND_SMS},
          MY_PERMISSIONS_REQUEST_SEND_SMS );

    } else {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage( number, null, message, sentPI, deliveredPI );
    }
  }
}

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






