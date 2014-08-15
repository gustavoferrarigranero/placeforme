package net.placeforme;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class TabOneActivity extends Fragment {


	@Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
		
		
    		View view = inflater.inflate(R.layout.fragment_tab_one, container, false);
    		((TextView) view.findViewById(R.id.section_label_1)).setText("Buscas nesta tela!");
    		((Button) view.findViewById(R.id.button_tela_1)).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(container.getContext(), "hahaha consegui!", Toast.LENGTH_SHORT).show();
				}
			});
    		
    		
    		
    		return view;
    		
    }


}
