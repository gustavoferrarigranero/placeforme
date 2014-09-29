package net.placeforme;

import net.placeforme.model.Usuario;
import net.placeforme.model.UsuarioDao;
import net.placeforme.util.Utils;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends Activity {

	public static RegisterActivity registerActivity;

	public String PREF_NAME = "PlaceforMePreferences";

	public static SharedPreferences settings;

	private Usuario usuario;
	private UsuarioDao usuarioDao;

	private View mProgressView;
	private View mLoginFormView;

	static final int REQUEST_IMAGE_CAPTURE = 1;

	private UserRegisterTask mRegisterTask = null;

	private EditText nomeText;
	private ImageView fotoImageView;
	private EditText emailText;
	private EditText senhaText;
	private Button cadastrar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// Set up the action bar.
		final ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		this.usuarioDao = new UsuarioDao(this);

		registerActivity = this;

		settings = getSharedPreferences(PREF_NAME, 0);

		mLoginFormView = findViewById(R.id.register_form);
		mProgressView = findViewById(R.id.register_progress);

		((ImageView) findViewById(R.id.foto))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dispatchTakePictureIntent();
					}
				});

		nomeText = (EditText) findViewById(R.id.nome);
		fotoImageView = (ImageView) findViewById(R.id.foto);
		fotoImageView.setImageBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.no_image));
		emailText = (EditText) findViewById(R.id.email);
		senhaText = (EditText) findViewById(R.id.password);
		cadastrar = (Button) findViewById(R.id.cadastrar);

		cadastrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cadastra();
			}
		});

	}

	private void cadastra() {
		if (mRegisterTask != null) {
			return;
		}
		usuario = new Usuario();
		usuario.setNome(nomeText.getText().toString());
		usuario.setFoto(((BitmapDrawable) fotoImageView.getDrawable())
				.getBitmap());
		usuario.setEmail(emailText.getText().toString());
		usuario.setSenha(senhaText.getText().toString());
		usuario.setStatus(1);
		usuario.setTipo(1);

		showProgress(true);
		mRegisterTask = new UserRegisterTask();
		mRegisterTask.execute((Void) null);
	}

	// take photo
	private void dispatchTakePictureIntent() {

		// para tirar foto
		// Intent takePictureIntent = new
		// Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		// if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
		// startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		// }

		// para pegar da galeria
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, REQUEST_IMAGE_CAPTURE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// para tirar foto
		/*
		 * if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
		 * { Bundle extras = data.getExtras(); Bitmap imageBitmap = (Bitmap)
		 * extras.get("data"); fotoImageView.setImageBitmap(imageBitmap); }
		 */

		// para galeria
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImageUri = data.getData();

			// OI FILE Manager
			String filemanagerstring = selectedImageUri.getPath();

			// MEDIA GALLERY
			String selectedImagePath = Utils.getPathImage(selectedImageUri);

			Bitmap image = null;

			if (selectedImagePath != null) {
				image = Utils.ShrinkBitmap(selectedImagePath, 300, 300);
			} else {
				image = Utils.ShrinkBitmap(filemanagerstring, 300, 300);
			}

			image = Utils.squareImage(image);

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
			int shortAnimTime = getResources().getInteger(
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
	 * Represents an asynchronous registration task used to authenticate the
	 * user.
	 */
	public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

		private final Usuario user;

		UserRegisterTask() {
			this.user = usuario;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			long usuario_id = usuarioDao.add(user);

			usuario = usuarioDao.get(usuario_id);

			if (null != usuario) {
				MainActivity.usuarioLogado = usuario;
				// Account exists, return true if the password matches.
				return true;
			}

			// TODO: register the new account here.
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mRegisterTask = null;
			showProgress(false);

			if (success) {

				SharedPreferences.Editor editor = settings.edit();

				editor.putString("Email", usuario.getEmail());
				editor.putString("Senha", usuario.getSenha());

				editor.commit();

				Intent main = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(main);
			} else {
				// mensagens de erro
			}
		}

		@Override
		protected void onCancelled() {
			mRegisterTask = null;
			showProgress(false);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
