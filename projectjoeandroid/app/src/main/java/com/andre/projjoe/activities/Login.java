package com.andre.projjoe.activities;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andre.projjoe.R;


public class Login extends Activity {

    String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		



		Button login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Login.this, MainNavigation.class);
				startActivity(i);
				//Intent i = new Intent(Login.this, MainTabs.class);
				//startActivity(i);
				
				/*if(facebook.isSessionValid())
				{
					
					//Log.d("output",facebook.getAccessToken());
					Log.d("mpref",mPrefs.getString("access_token", null));
					//getProfileInformation();
					//getFriends();
					Log.d("session",String.valueOf(facebook.isSessionValid()));
					
					friendPick();
				}
				else
				{
					loginToFacebook();
					Log.d("session",String.valueOf(facebook.isSessionValid()));
				}*/
				
			}
		});
		
		Button sign = (Button) findViewById(R.id.create);
		sign.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Login.this, Signup.class);
				startActivity(i);
				/*if(!facebook.isSessionValid())
				logoutFromFacebook();
				else
				Toast.makeText(Login.this, "Already logged out", Toast.LENGTH_LONG).show();*/	
				///friendPick();
			}
		});
		
	}
	
	public void friendPick() {
		
//
//
//
//
//		mAsyncRunner.request("/me/friends", new RequestListener() {
//	        @Override
//	        public void onComplete(String response, Object state) {
//	            Log.d("dddd", response);
//	            jsonx = response;
//
//
//
//	                runOnUiThread(new Runnable() {
//
//	                    @Override
//	                    public void run() {
//	                        Toast.makeText(getApplicationContext(), jsonx, Toast.LENGTH_LONG).show();
//	                    }
//
//	                });
//
//
//	        }
//
//			@Override
//			public void onIOException(IOException e, Object state) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onFileNotFoundException(FileNotFoundException e,
//					Object state) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onMalformedURLException(MalformedURLException e,
//					Object state) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onFacebookError(FacebookError e, Object state) {
//				// TODO Auto-generated method stub
//
//			}
//		});
		//Intent i = new Intent(Login.this, FriendPickerSampleActivity.class);
		//startActivity(i);
	}

	public void postToWall() {
	    // post on user's wall.
//	    facebook.dialog(this, "feed", new DialogListener() {
//
//	        @Override
//	        public void onFacebookError(FacebookError e) {
//	        }
//
//	        @Override
//	        public void onError(DialogError e) {
//	        }
//
//	        @Override
//	        public void onComplete(Bundle values) {
//	        }
//
//	        @Override
//	        public void onCancel() {
//	        }
//	    });
	 
	}
	
	public void logoutFromFacebook() {
//	    mAsyncRunner.logout(Login.this, new RequestListener() {
//	        @Override
//	        public void onComplete(String response, Object state) {
//	            Log.d("Logout from Facebook", response);
//	            if (Boolean.parseBoolean(response) == true) {
//	                // User successfully Logged out
//	            }
//	        }
//
//	        @Override
//	        public void onIOException(IOException e, Object state) {
//	        }
//
//	        @Override
//	        public void onFileNotFoundException(FileNotFoundException e,
//	                Object state) {
//	        }
//
//	        @Override
//	        public void onMalformedURLException(MalformedURLException e,
//	                Object state) {
//	        }
//
//	        @Override
//	        public void onFacebookError(FacebookError e, Object state) {
//	        }
//	    });
	}
	
	public void loginToFacebook() {
//	    mPrefs = getPreferences(MODE_PRIVATE);
//	    String access_token = mPrefs.getString("access_token", null);
//	    long expires = mPrefs.getLong("access_expires", 0);
//
//	    if (access_token != null) {
//	        facebook.setAccessToken(access_token);
//	    }
//
//	    if (expires != 0) {
//	        facebook.setAccessExpires(expires);
//	    }
//
//	    if (!facebook.isSessionValid()) {
//	        facebook.authorize(this,
//	                new String[] { "email", "publish_stream", "user_friends" },
//	                new DialogListener() {
//
//	                    @Override
//	                    public void onCancel() {
//	                        // Function to handle cancel event
//	                    }
//
//	                    @Override
//	                    public void onComplete(Bundle values) {
//	                        // Function to handle complete event
//	                        // Edit Preferences and update facebook acess_token
//	                        SharedPreferences.Editor editor = mPrefs.edit();
//	                        editor.putString("access_token",
//	                                facebook.getAccessToken());
//	                        editor.putLong("access_expires",
//	                                facebook.getAccessExpires());
//	                        editor.commit();
//	                    }
//
//
//
//	                    @Override
//	                    public void onFacebookError(FacebookError fberror) {
//	                        // Function to handle Facebook errors
//
//	                    }
//
//
//						@Override
//						public void onError(DialogError e) {
//							// TODO Auto-generated method stub
//
//						}
//
//	                });
//	    }
	}
	String jsonx;
	public void getFriends() {
	
//		 mAsyncRunner.request("me/friends_about_me", new RequestListener() {
//		        @Override
//		        public void onComplete(String response, Object state) {
//		            Log.d("Profile", response);
//		            jsonx = response;
//
//
//
//		                runOnUiThread(new Runnable() {
//
//		                    @Override
//		                    public void run() {
//		                        Toast.makeText(getApplicationContext(), jsonx, Toast.LENGTH_LONG).show();
//		                    }
//
//		                });
//
//
//		        }
//
//		        @Override
//		        public void onIOException(IOException e, Object state) {
//		        }
//
//		        @Override
//		        public void onFileNotFoundException(FileNotFoundException e,
//		                Object state) {
//		        }
//
//		        @Override
//		        public void onMalformedURLException(MalformedURLException e,
//		                Object state) {
//		        }
//
//		        @Override
//		        public void onFacebookError(FacebookError e, Object state) {
//		        }
//
//		    });
	}

	
	String name,email,friends;
	public void getProfileInformation() {
//	    mAsyncRunner.request("me", new RequestListener() {
//	        @Override
//	        public void onComplete(String response, Object state) {
//	            Log.d("Profile", response);
//	            String json = response;
//	            try {
//	                JSONObject profile = new JSONObject(json);
//
//	                // getting namen of the user
//	                name = profile.getString("name");
//	                // getting email of the user
//	                email = profile.getString("email");
//	                Log.d("name",name);
//
//
//	                runOnUiThread(new Runnable() {
//
//	                    @Override
//	                    public void run() {
//	                        Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
//	                    }
//
//	                });
//
//	            } catch (JSONException e) {
//	                e.printStackTrace();
//	            }
//	        }
//
//	        @Override
//	        public void onIOException(IOException e, Object state) {
//	        }
//
//	        @Override
//	        public void onFileNotFoundException(FileNotFoundException e,
//	                Object state) {
//	        }
//
//	        @Override
//	        public void onMalformedURLException(MalformedURLException e,
//	                Object state) {
//	        }
//
//	        @Override
//	        public void onFacebookError(FacebookError e, Object state) {
//	        }
//
//	    });
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
