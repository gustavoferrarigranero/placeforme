package net.placeforme;

import java.text.ParseException;
import java.util.ArrayList;

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
import net.placeforme.util.ExpandableHeightGridView;
import net.placeforme.util.ExpandableHeightListView;
import net.placeforme.util.AdapterGridViewFotos;
import net.placeforme.util.Utils;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ShowEventoActivity extends Activity {

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
	private ExpandableHeightListView eventoAtributosList;

	private ArrayList<EventoAtributo> eventoAtributos;
	
	private ArrayList<Foto> fotos;
	
	private int REQUEST_IMAGE_CAPTURE = 1 ;

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

		usuarioText = (TextView) findViewById(R.id.evento_usuario);
		dataText = (TextView) findViewById(R.id.evento_data);
		horarioText = (TextView) findViewById(R.id.evento_horario);
		eventoAtributosList = (ExpandableHeightListView) findViewById(R.id.evento_atributos);

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

			eventoAtributos = eventoAtributoDao.getAllByEvento(evento);

			populaListAtributos();

			populaGridViewFotos();

		}

	}

	private void populaListAtributos() {
		AdapterListAtributos adapter = new AdapterListAtributos(this,
				eventoAtributos);
		eventoAtributosList.setAdapter(adapter);
		eventoAtributosList.setExpanded(true);
	}
	
	private void populaGridViewFotos(){
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
				if(position == fotos.size()-1){
					dispatchTakePictureIntent();
				}else{

					Dialog dialog = new Dialog(showEventoActivity);
					dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(getLayoutInflater().inflate(R.layout.dialog_evento_foto, null));
					
					ImageView imageView = (ImageView) dialog.findViewById(R.id.fotoEvento);
					imageView.setImageBitmap(fotos.get(position).getFoto());
					
					dialog.show();
					
				}
			}
		});

		gridview.setExpanded(true);
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
		
		//para tirar foto
		/*if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        fotoImageView.setImageBitmap(imageBitmap);
	    }*/
		
		//para galeria
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && null != data) {
			Uri selectedImageUri = data.getData();

            //OI FILE Manager
            String filemanagerstring = selectedImageUri.getPath();

            //MEDIA GALLERY
            String selectedImagePath = Utils.getPathImage(selectedImageUri);

            Bitmap image = null;
            
            if(selectedImagePath!=null){
            	image = Utils.ShrinkBitmap(selectedImagePath,300,300);
            }else{
            	image = Utils.ShrinkBitmap(filemanagerstring,300,300);
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
	
	//take photo
	private void dispatchTakePictureIntent() {
		
		//para tirar foto
	    //Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		//if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    //}
		
		//para pegar da galeria
		Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
	    
	}
		
}
