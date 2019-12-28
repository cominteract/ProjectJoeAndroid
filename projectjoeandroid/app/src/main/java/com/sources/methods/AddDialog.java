package com.sources.methods;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


public class AddDialog {

	public void DialogShow(Context c, String[] attribs, int layoutint)
	{
		Dialog dialog = new Dialog(c);
		dialog.setContentView(layoutint);
		dialog.setTitle(attribs[0]);
		dialog.show();
	}
	public void DialogBuilder(Context c, String[] attribs, String[]listattribs)
	{
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
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
		
		builder.setTitle(attribs[0]);
		
		builder.setItems(listattribs, new DialogInterface.OnClickListener() {
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
}
