package net.placeforme;

import java.text.ParseException;
import java.util.ArrayList;

import br.com.condesales.EasyFoursquareAsync;
import br.com.condesales.criterias.CheckInCriteria;
import br.com.condesales.criterias.TipsCriteria;
import br.com.condesales.criterias.VenuesCriteria;
import br.com.condesales.listeners.AccessTokenRequestListener;
import br.com.condesales.listeners.CheckInListener;
import br.com.condesales.listeners.FoursquareVenuesRequestListener;
import br.com.condesales.listeners.ImageRequestListener;
import br.com.condesales.listeners.TipsRequestListener;
import br.com.condesales.listeners.UserInfoRequestListener;
import br.com.condesales.models.Checkin;
import br.com.condesales.models.Tip;
import br.com.condesales.models.User;
import br.com.condesales.models.Venue;
import br.com.condesales.tasks.users.UserImageRequest;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import net.placeforme.model.Avaliacao;
import net.placeforme.model.AvaliacaoDao;
import net.placeforme.model.Evento;
import net.placeforme.model.EventoAtributo;
import net.placeforme.model.EventoAtributoDao;
import net.placeforme.model.EventoDao;
import net.placeforme.model.Foto;
import net.placeforme.model.FotoDao;
import net.placeforme.model.UsuarioDao;
import net.placeforme.model.AtributoDao;
import net.placeforme.util.AdapterListAtributos;
import net.placeforme.util.AdapterListAvaliacoes;
import net.placeforme.util.ExpandableHeightGridView;
import net.placeforme.util.ExpandableHeightListView;
import net.placeforme.util.AdapterGridViewFotos;
import net.placeforme.util.Utils;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ShowEventoActivity extends Activity implements AccessTokenRequestListener {

	public static ShowEventoActivity showEventoActivity;

	private Evento evento;

	private int evento_id;

	private EventoDao eventoDao;
	private UsuarioDao usuarioDao;
	private FotoDao fotoDao;
	private AvaliacaoDao avaliacaoDao;
	private EventoAtributoDao eventoAtributoDao;
	private AtributoDao atributoDao;

	private TextView usuarioText;
	private TextView dataText;
	private TextView horarioText;

	private Button buttonAddAvaliacao;
	private Button buttonDialogAddAvaliacao;

	private SeekBar avaliacaoNota;
	private TextView notaText;
	private EditText avaliacaoTexto;

	private ExpandableHeightListView eventoAtributosList;
	private ArrayList<EventoAtributo> eventoAtributos;

	private ExpandableHeightListView eventoAvaliacoesList;
	private ArrayList<Avaliacao> eventoAvaliacoes;

	private ArrayList<Foto> fotos;

	private int REQUEST_IMAGE_CAPTURE = 1;

	private Intent shareIntent;
	
	private Dialog shareDialog;
	
	private UiLifecycleHelper uiHelper;
	
	private EasyFoursquareAsync async;
	
	private Location userLocation;
	
	private LocationListener locationListener;
	
	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_evento);

		// Set up the action bar.
		final ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		showEventoActivity = this;

		Bundle bundle = getIntent().getExtras();

		evento_id = bundle.getInt("evento_id");

		eventoDao = new EventoDao(this);
		usuarioDao = new UsuarioDao(this);
		fotoDao = new FotoDao(this);
		avaliacaoDao = new AvaliacaoDao(this);
		eventoAtributoDao = new EventoAtributoDao(this);
		atributoDao = new AtributoDao(this);
		
		uiHelper = new UiLifecycleHelper(this, null);
	    uiHelper.onCreate(savedInstanceState);
	    
	    locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				userLocation = new Location(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};
		
		locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		
	     
		usuarioText = (TextView) findViewById(R.id.evento_usuario);
		dataText = (TextView) findViewById(R.id.evento_data);
		horarioText = (TextView) findViewById(R.id.evento_horario);
		eventoAtributosList = (ExpandableHeightListView) findViewById(R.id.evento_atributos);
		eventoAvaliacoesList = (ExpandableHeightListView) findViewById(R.id.list_evento_avaliacoes);

		try {
			evento = eventoDao.get(evento_id);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (null != evento) {
			actionBar.setTitle(evento.getTitulo());
			usuarioText.setText("Evento criado por: "
					+ usuarioDao.get(evento.getUsuarioId()).getNome());
			dataText.setText("Marcado para o dia: "
					+ Utils.sqlDateToString(evento.getDataInicio()));
			horarioText.setText("Ás: "
					+ Utils.sqlTimeToString(evento.getHorario()) + " hs");

			populaListAtributos();

			populaGridViewFotos();

			populaListAvaliacoes();

		}

		buttonAddAvaliacao = (Button) findViewById(R.id.button_add_avaliacao);
		buttonAddAvaliacao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(showEventoActivity);
				dialog.setContentView(R.layout.dialog_evento_add_avaliacao);
				dialog.setTitle("Avaliar");

				notaText = (TextView) dialog.findViewById(R.id.nota_text);

				avaliacaoNota = (SeekBar) dialog
						.findViewById(R.id.avaliacao_nota);
				avaliacaoNota
						.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

							@Override
							public void onStopTrackingTouch(SeekBar seekBar) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onStartTrackingTouch(SeekBar seekBar) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onProgressChanged(SeekBar seekBar,
									int progress, boolean fromUser) {
								// TODO Auto-generated method stub
								notaText.setText("Nota: "
										+ seekBar.getProgress());

							}
						});

				avaliacaoTexto = (EditText) dialog
						.findViewById(R.id.evento_avaliacao_texto);

				buttonDialogAddAvaliacao = (Button) dialog
						.findViewById(R.id.button_dialog_add_avaliacao);

				buttonDialogAddAvaliacao
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Avaliacao avaliacao = new Avaliacao();
								avaliacao.setEventoId(evento_id);
								avaliacao.setNota(avaliacaoNota.getProgress());
								avaliacao.setTexto(avaliacaoTexto.getText()
										.toString());
								avaliacao.setStatus(1);
								avaliacao
										.setUsuarioId(MainActivity.usuarioLogado
												.getUsuarioId());

								avaliacaoDao.add(avaliacao);
								populaListAvaliacoes();
								eventoAvaliacoesList.requestFocus();
								dialog.dismiss();
							}
						});

				dialog.show();

			}
		});
		
		shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");

	}

	private void populaListAtributos() {

		eventoAtributos = new ArrayList<EventoAtributo>();

		eventoAtributos = eventoAtributoDao.getAllByEvento(evento);

		AdapterListAtributos adapter = new AdapterListAtributos(this,
				eventoAtributos);
		eventoAtributosList.setAdapter(adapter);
		eventoAtributosList.setExpanded(true);
	}

	private void populaGridViewFotos() {

		fotos = new ArrayList<Foto>();

		fotos = fotoDao.getAllByEvento(evento.getEventoId());

		Foto foto = new Foto();
		foto.setFotoId(99999999);
		foto.setEventoId(1);
		foto.setFoto(BitmapFactory.decodeResource(getResources(),
				R.drawable.add_foto_icon, null));
		foto.setStatus(1);

		fotos.add(foto);

		ExpandableHeightGridView gridview = (ExpandableHeightGridView) findViewById(R.id.gridview_fotos);
		gridview.setAdapter(new AdapterGridViewFotos(this, fotos));

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == fotos.size() - 1) {
					dispatchTakePictureIntent();
				} else {

					Dialog dialog = new Dialog(showEventoActivity);
					dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(getLayoutInflater().inflate(
							R.layout.dialog_evento_foto, null));

					ImageView imageView = (ImageView) dialog
							.findViewById(R.id.fotoEvento);
					imageView.setImageBitmap(fotos.get(position).getFoto());

					dialog.show();

				}
			}
		});

		gridview.setExpanded(true);
	}

	private void populaListAvaliacoes() {

		eventoAvaliacoes = new ArrayList<Avaliacao>();

		eventoAvaliacoes = avaliacaoDao.getAllByEvento(evento);
		AdapterListAvaliacoes adapter = new AdapterListAvaliacoes(this,
				eventoAvaliacoes);

		eventoAvaliacoesList.setAdapter(adapter);
		eventoAvaliacoesList.setExpanded(true);

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            shareDialog.dismiss();
	        }
	    });

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

			Foto newFoto = new Foto();

			newFoto.setEventoId(evento_id);
			newFoto.setFoto(image);
			newFoto.setStatus(1);

			fotoDao.add(newFoto);

			populaGridViewFotos();

		}

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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu resource file.
		getMenuInflater().inflate(R.menu.show_evento, menu);

		MenuItem item = menu.findItem(R.id.menu_item_share);
		
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				///shareIntent.setPackage("com.facebook.katana");
				
				shareDialog = new Dialog(showEventoActivity);
				shareDialog.setContentView(R.layout.dialog_evento_share);
				shareDialog.setTitle("Compartilhar no");
				

				shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send. "+evento.getTitulo());
				
				LinearLayout s_facebook = (LinearLayout)shareDialog.findViewById(R.id.share_facebook);
				LinearLayout s_whatsapp = (LinearLayout)shareDialog.findViewById(R.id.share_whatsapp);
				LinearLayout s_swarm = (LinearLayout)shareDialog.findViewById(R.id.share_swarm);
				LinearLayout s_gmail = (LinearLayout)shareDialog.findViewById(R.id.share_gmail);
				LinearLayout s_twitter = (LinearLayout)shareDialog.findViewById(R.id.share_twitter);
				
				s_facebook.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(showEventoActivity)
				        .setLink("https://developers.facebook.com/android")
				        .build();
				uiHelper.trackPendingDialogCall(shareDialog.present());
					}
				});
				
				s_whatsapp.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						shareIntent.setPackage("com.whatsapp");
						startActivity(shareIntent);
					}
				});
				
				s_swarm.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						async = new EasyFoursquareAsync(showEventoActivity);
					    async.requestAccess(showEventoActivity);
					}

				});
				
				s_gmail.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						shareIntent.setPackage("com.google.android.gm");
						startActivity(shareIntent);
					}
				});
				
				s_twitter.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						shareIntent.setPackage("com.twitter.android");
						startActivity(shareIntent);
					}
				});
				
				shareDialog.show();
				
				return false;
			}
		});

		// Return true to display menu
		return true;
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	
	@Override
    public void onError(String errorMsg) {
        // Do something with the error message
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAccessGrant(String accessToken) {
        requestTipsNearby();
    }

    private void checkin(Venue venue) {
    	    	
    	CheckInCriteria criteria = new CheckInCriteria();

        criteria.setVenueId(venue.getId());
        
        criteria.setBroadcast(CheckInCriteria.BroadCastType.PUBLIC);

        async.checkIn(new CheckInListener() {
            @Override
            public void onCheckInDone(Checkin checkin) {
                shareDialog.dismiss();
                Toast.makeText(showEventoActivity, "Check-in feito!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(showEventoActivity, "error", Toast.LENGTH_LONG).show();
            }
        }, criteria);
    	
        
    }
    
    private void requestTipsNearby() {
    	
    	Location loc = new Location("");
        loc.setLatitude(userLocation.getLatitude());
    	loc.setLongitude(userLocation.getLongitude());

        VenuesCriteria criteria = new VenuesCriteria();
        criteria.setLocation(loc);

        async.getVenuesNearby(new FoursquareVenuesRequestListener() {
			
			@Override
			public void onError(String errorMsg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onVenuesFetched(ArrayList<Venue> venues) {
				// TODO Auto-generated method stub
				checkin(venues.get(0));
				
			}
		},criteria);
        
    }

}
