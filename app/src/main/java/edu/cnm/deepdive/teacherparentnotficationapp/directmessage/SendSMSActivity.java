package edu.cnm.deepdive.teacherparentnotficationapp.directmessage;

import android.Manifest;
import android.Manifest.permission;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageManager;
import android.net.Uri;
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

public class SendSMSActivity extends AppCompatActivity implements OnClickListener {

  private Object context;
  private Object intent;
  EditText parent_number;
  EditText insert_message;
  EditText parent_email;
 // EditText email_message;

  int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;


  String SENT = "SMS_SENT";
  String DELIVERED = "SMS_DELIVERED";
  String SENT_EMAIL = "EMAIL_SENT";
  PendingIntent sentPI;
  PendingIntent deliveredPI;
  BroadcastReceiver smsSentReceiver;
  BroadcastReceiver smsDeliveredReceiver;
  private Button send_button;
  private Button email_send;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.message_layout);


    parent_number = (EditText) findViewById(R.id.parent_number);
    parent_number.setText(getIntent().getStringExtra("parent_number"));
    insert_message = (EditText) findViewById(R.id.insert_message);
    send_button = (Button) findViewById(R.id.buttonSend);
    parent_email = (EditText) findViewById(R.id.parent_email);
    parent_email.setText(getIntent().getStringExtra("parent_email"));
   // email_message = (EditText) findViewById(R.id.email_text);
    email_send = (Button) findViewById(R.id.emailSend);


    send_button.setOnClickListener(this);
    sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
    deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
    email_send.setOnClickListener(this);

  }

  @Override
  protected void onResume() {
    super.onResume( );

    smsSentReceiver = new BroadcastReceiver( ) {
      @Override
      public void onReceive(Context context, Intent intent) {

        switch (getResultCode( )) {
          case Activity.RESULT_OK:
            Toast.makeText(SendSMSActivity.this, "SMS sent", Toast.LENGTH_SHORT).show( );
            break;

          case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            Toast.makeText(SendSMSActivity.this, "Generic failure!", Toast.LENGTH_SHORT).show( );
            break;

          case SmsManager.RESULT_ERROR_NO_SERVICE:
            Toast.makeText(SendSMSActivity.this, "No service!", Toast.LENGTH_SHORT).show( );
            break;

          case SmsManager.RESULT_ERROR_NULL_PDU:
            Toast.makeText(SendSMSActivity.this, "Null PDU", Toast.LENGTH_SHORT).show( );
            break;

          case SmsManager.RESULT_ERROR_RADIO_OFF:
            Toast.makeText(SendSMSActivity.this, "Radio Off", Toast.LENGTH_SHORT).show( );
            break;
        }
      }
    };

    smsDeliveredReceiver = new BroadcastReceiver( ) {
      @Override
      public void onReceive(Context context, Intent intent) {

        switch (getResultCode( )) {
          case Activity.RESULT_OK:
            Toast.makeText(SendSMSActivity.this, "SMS delivered", Toast.LENGTH_SHORT).show( );
            break;
          case Activity.RESULT_CANCELED:
            Toast.makeText(SendSMSActivity.this, "SMS not delivered", Toast.LENGTH_SHORT).show( );
            break;
        }
        finish();
      }
    };

    registerReceiver(smsSentReceiver, new IntentFilter(SENT));
    registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
  }

  @Override
  protected void onPause() {
    SendSMSActivity.super.onPause( );

    unregisterReceiver(smsDeliveredReceiver);
    unregisterReceiver(smsSentReceiver);
  }

  public void setButtonSend()

  {

    String number = parent_number.getText( ).toString( );
    String message = insert_message.getText( ).toString( );

    if (ContextCompat.checkSelfPermission(this, permission.SEND_SMS)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{permission.SEND_SMS},
          MY_PERMISSIONS_REQUEST_SEND_SMS);

    } else {
      SmsManager sms = SmsManager.getDefault( );
      sms.sendTextMessage(number, null, message, sentPI, deliveredPI);
    }
  }

  @Override
  public void onClick(View v) {
    if (v.getId()== send_button.getId()) {
      setButtonSend();
    }else{
      Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
      emailIntent.setData(Uri.parse("mailto:" + parent_email.getText()));
       //   + "&body=" + Uri.encode(email_message.getText().toString())));
      parent_email.setText("");
      startActivity(emailIntent);
    }

  }

}







