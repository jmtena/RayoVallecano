package es.upm.miw.rayovallecano;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import es.upm.miw.rayovallecano.models.Futbolista;

/**
 * Created by bm0806 on 30/10/2015.
 */
public class FutbolistaAdapter extends ArrayAdapter<Futbolista> {
    Context _contexto;
    private ArrayList<Futbolista> _futbolistas;

    public FutbolistaAdapter(Context context, ArrayList<Futbolista> futbolistas) {
        super(context, R.layout.layout_listado_futbolista,futbolistas);
        this._futbolistas = futbolistas;
        this._contexto = context;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater =
                    (LayoutInflater) _contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_listado_futbolista, null);
        }

        Futbolista futbolista = this._futbolistas.get(position);
        if (futbolista != null){
            TextView tvId = (TextView) convertView.findViewById(R.id.tvFutbolistaId);
            tvId.setText(futbolista.get_id() + "");

            TextView tvNombre = (TextView) convertView.findViewById(R.id.tvFutbolistaNombre);
            tvId.setText(futbolista.get_nombre());

            TextView tvEquipo = (TextView) convertView.findViewById(R.id.tvFutbolistaEquipo);
            tvId.setText(futbolista.get_equipo());
        }

        return  convertView;
    }
}
