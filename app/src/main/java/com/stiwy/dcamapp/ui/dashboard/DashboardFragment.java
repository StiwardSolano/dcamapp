package com.stiwy.dcamapp.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;
import com.squareup.picasso.Picasso;
import com.stiwy.dcamapp.R;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    Button btnbuscar;
    ImageView imgfonfondo;
    EditText editText;
    private Unsplash unsplash = new Unsplash("vLlT9luLiG3rmBs5tFRiPpYSQz6NzXYbL4_RA0BptlU");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        btnbuscar = root.findViewById(R.id.btnbuscar);
        imgfonfondo = root.findViewById(R.id.imgfonfondo);
        editText= root.findViewById(R.id.editText);

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText != null){
                    Log.d("Texto a buscar:",editText.getText().toString());
                    search(editText.getText().toString());
                }
            }
        });
        return root;
    }

    public void search(String query){
        unsplash.searchPhotos(query, new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos: ", "Total Resultados:" + results.getTotal());
                List<Photo> photos = results.getResults();
                String photoUrl = photos.get(2).getUrls().getRegular();
                Picasso.get().load(photoUrl).into(imgfonfondo);
                photos.clear();
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash: ", error);
            }
        });
    }
}
