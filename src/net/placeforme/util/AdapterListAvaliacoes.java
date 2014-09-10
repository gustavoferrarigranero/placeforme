package net.placeforme.util;

import java.util.ArrayList;
import java.util.List;

import net.placeforme.R;
import net.placeforme.model.AtributoDao;
import net.placeforme.model.Avaliacao;
import net.placeforme.model.AvaliacaoDao;
import net.placeforme.model.EventoAtributo;
import net.placeforme.model.UsuarioDao;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterListAvaliacoes extends BaseAdapter {

    private Context context;
    private View view;
    private LayoutInflater inflater;
    
    private ArrayList<Avaliacao> avaliacoes;
    private Avaliacao avaliacao;
    
    private UsuarioDao usuarioDao;
    
    private ImageView campoNota ;
    private TextView campoTexto;
    private TextView campoNome;
    

    public AdapterListAvaliacoes(Context context, ArrayList<Avaliacao> avaliacoes){ 
        this.context = context;
        this.avaliacoes = avaliacoes;
    }

    @Override

    public int getCount() {
        return avaliacoes.size();
    }


    @Override

    public Object getItem(int position) {
        return avaliacoes.get(position);
    }

    @Override

    public long getItemId(int position) {
    	return position;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.list_avaliacoes, null);
        
        usuarioDao = new UsuarioDao(context);

        avaliacao = avaliacoes.get(position);
        
        campoNome = (TextView) view.findViewById(R.id.evento_avaliacao_nome);
        campoTexto = (TextView) view.findViewById(R.id.evento_avaliacao_texto);
        campoNota = (ImageView) view.findViewById(R.id.evento_avaliacao_nota);
        
        campoTexto.setText(avaliacao.getTexto());
        
        switch (avaliacao.getNota()) {
        case 0: 
			campoNota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avaliacao_nota_0));
			break;
		case 1: 
			campoNota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avaliacao_nota_1));
			break;
		case 2: 
			campoNota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avaliacao_nota_2));
			break;
		case 3: 
			campoNota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avaliacao_nota_3));
			break;
		case 4: 
			campoNota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avaliacao_nota_4));
			break;
		case 5: 
			campoNota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avaliacao_nota_5));
			break;
		}
        
        campoNome.setText(usuarioDao.get(avaliacao.getUsuarioId()).getNome());
        
        return view;
        
    }

	
}