package com.andre.projjoe.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andre.projjoe.R;

public class Signup extends Activity {
	EditText fn,em,pc,mb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
        Button go = (Button) findViewById(R.id.signup);
        go.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fn = (EditText) findViewById(R.id.fname);
				em = (EditText) findViewById(R.id.email);
				pc = (EditText) findViewById(R.id.pass);
				mb = (EditText) findViewById(R.id.mobileno);
				if(isValidEmail(em.getText().toString().trim()) && isValidPhoneNumber(mb.getText().toString().trim()))
				{
					String[] arr = {fn.getText().toString(),em.getText().toString(),pc.getText().toString(),mb.getText().toString()};

					AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
					// Add the buttons
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int id) {
								   // User clicked OK button

							   }
						   });
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int id) {
								   // User cancelled the dialog
							   }
						   });

					builder.setTitle("Confirm Registration?");
					builder.setItems(arr, new DialogInterface.OnClickListener() {
						   public void onClick(DialogInterface dialog, int which) {
							   // The 'which' argument contains the index position
							   // of the selected item

						   }
					});
					// Set other dialog properties


					// Create the AlertDialog
					AlertDialog dialog = builder.create();
					dialog.show();
				}

				//Intent i = new Intent(Signup.this, MainTabs.class);
				//startActivity(i);
			}
		});
		
	}
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
        return false;
        } else {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    } 

    public static final boolean isValidPhoneNumber(CharSequence target) {
        if (target.length()!=10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
