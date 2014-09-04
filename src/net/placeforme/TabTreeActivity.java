package net.placeforme;

import java.util.ArrayList;
import java.util.List;

import net.placeforme.model.Usuario;
import net.placeforme.model.UsuarioDao;
import net.placeforme.util.Utils;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.GetChars;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class TabTreeActivity extends Fragment {

	
public String PREF_NAME = "PlaceforMePreferences";

	public static SharedPreferences settings;
	public static TabTreeActivity tabTreeActivity;
	
	public static Activity activityStatic;
	
    private Usuario usuario;
    private UsuarioDao usuarioDao;
    
	private View mProgressView;
	private View mLoginFormView;
	
	static final int REQUEST_IMAGE_CAPTURE = 1;

	private UserUpdateTask mUpdateTask = null;
	
    private EditText nomeText;
    private static ImageView fotoImageView;
    private EditText emailText;
    private EditText senhaText;
    private Button cadastrar;
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		
    	ctx = MainActivity.mainActivity;
    	
    	activityStatic = MainActivity.mainActivity;
		
		View view = inflater.inflate(R.layout.fragment_tab_tree, container, false);
    		
		this.usuarioDao = new UsuarioDao(ctx);
		
		
		settings = MainActivity.mainActivity.getSharedPreferences(PREF_NAME, 0);
		
		mLoginFormView = view.findViewById(R.id.register_form);
		mProgressView = view.findViewById(R.id.register_progress);
		
		((ImageView) view.findViewById(R.id.foto)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dispatchTakePictureIntent();
			}
		});
		
		nomeText = (EditText) view.findViewById(R.id.nome);
		nomeText.setText(MainActivity.usuarioLogado.getNome());
	    fotoImageView = (ImageView) view.findViewById(R.id.foto);
	    fotoImageView.setImageBitmap(MainActivity.usuarioLogado.getFoto());
	    emailText = (EditText) view.findViewById(R.id.email);
	    emailText.setText(MainActivity.usuarioLogado.getEmail());
	    senhaText = (EditText) view.findViewById(R.id.password);
	    senhaText.setText(MainActivity.usuarioLogado.getSenha());
	    cadastrar = (Button) view.findViewById(R.id.cadastrar);
		
	    cadastrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cadastra();
			}
		});
	    
		return view;
		
    }
	
	private void cadastra(){
		if (mUpdateTask != null) {
			return;
		}
		usuario = new Usuario();
		usuario.setUsuarioId(MainActivity.usuarioLogado.getUsuarioId());
		usuario.setNome(nomeText.getText().toString());
		usuario.setFoto(((BitmapDrawable)fotoImageView.getDrawable()).getBitmap());
		usuario.setEmail(emailText.getText().toString());
		usuario.setSenha(senhaText.getText().toString());
		usuario.setStatus(1);
		usuario.setTipo(1); 
		
		showProgress(true);
		mUpdateTask = new UserUpdateTask();
		mUpdateTask.execute((Void) null);
	}
	
	//take photo
	private void dispatchTakePictureIntent() {
		
		//para tirar foto
	    //Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		//if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    //}
		
		//para pegar da galeria
		Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		MainActivity.mainActivity.startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
	    
	}
	
	/**
     * helper to retrieve the path of an image URI
     */
    public static String getPath(Uri uri) {
            // just some safety built in 
            if( uri == null ) {
                // TODO perform some logging or show user feedback
                return null;
            }
            // try to retrieve the image from the media store first
            // this will only work for images selected from gallery
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = managedQuery(uri, projection, null, null, null);
            if( cursor != null ){
                int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
            // this is our fallback here
            return uri.getPath();
    }
	

	private static Cursor managedQuery(Uri uri, String[] projection,
			Object object, Object object2, Object object3) {
		// TODO Auto-generated method stub
		return activityStatic.managedQuery(uri, projection, null, null, null);
	}

	public static void result(int requestCode, int resultCode, Intent data) {
		
		//para tirar foto
		/*if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        fotoImageView.setImageBitmap(imageBitmap);
	    }*/
		
		//para galeria
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == android.app.Activity.RESULT_OK && null != data) {
			Uri selectedImageUri = data.getData();

            //OI FILE Manager
            String filemanagerstring = selectedImageUri.getPath();

            //MEDIA GALLERY
            String selectedImagePath = getPath(selectedImageUri);

            Bitmap image = null;
            
            if(selectedImagePath!=null){
            	image = Utils.ShrinkBitmap(selectedImagePath,300,300);
            }else{
            	image = Utils.ShrinkBitmap(filemanagerstring,300,300);
            }
            
            if(image.getWidth() != image.getHeight()){
            	if(image.getWidth()>image.getHeight()){
            		image = Bitmap.createBitmap(
						image, 
						image.getHeight()/2 ,
						0,
						image.getHeight(), 
						image.getHeight()
					);

            	}else{
            		image = Bitmap.createBitmap(
    						image, 
    						0 ,
    						image.getWidth()/2,
    						image.getWidth(), 
    						image.getWidth()
    					);
            	}
            }
                        
            fotoImageView.setImageBitmap(image);
        }
		
	}	
	
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = MainActivity.mainActivity.getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	


	
	/**
	 * Represents an asynchronous registration task used to authenticate
	 * the user.
	 */
	@SuppressLint("ShowToast") public class UserUpdateTask extends AsyncTask<Void, Void, Boolean> {

		private final Usuario user;

		UserUpdateTask() {
			this.user = usuario;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return false;
			}
			
			
			
			long usuario_id = usuarioDao.update(this.user);
			
			usuario = usuarioDao.get(usuario_id);

			if (null!=usuario) {
				MainActivity.usuarioLogado = usuario;
				return true;
			}

			// TODO: register the new account here.
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mUpdateTask = null;
			showProgress(false);

			if (success) {
				
				SharedPreferences.Editor editor = settings.edit();
			      
			    editor.putString("Email", usuario.getEmail());
			    editor.putString("Senha", usuario.getSenha());

			    editor.commit();
			    MainActivity.mainActivity.actionBarStatic.selectTab(MainActivity.mainActivity.actionBarStatic.getTabAt(1));
			    Log.d("salvou", "terminou");
			    MainActivity.mainActivity.recreate();
				
			} else {
				//mensagens de erro
			}
		}

		@Override
		protected void onCancelled() {
			mUpdateTask = null;
			showProgress(false);
		}
	}


}
