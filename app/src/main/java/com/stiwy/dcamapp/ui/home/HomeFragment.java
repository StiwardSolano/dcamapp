package com.stiwy.dcamapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.RequiresApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;
import android.widget.Toast;
import android.os.Environment;
import android.os.StrictMode;
import java.io.File;
import com.stiwy.dcamapp.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageView imageView;
    Button button;
    VideoView videoView;
    MediaController mc;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private Uri fileUri;
    int bandera = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        button = (Button) root.findViewById(R.id.button);
        imageView = root.findViewById(R.id.imageView);
        videoView = (VideoView) root.findViewById(R.id.videoView);
        mc = new MediaController(getActivity());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        videoView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                snapVideo();

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snapPicture();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snapPicture();

            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void snapVideo(){
        File mediaFile = new
                File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/myvideo.mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = Uri.fromFile(mediaFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        videoView.setForeground(null);
        startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);

    }
    private void snapPicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            bandera = 1;
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_IMAGE_CAPTURE && bandera == 1) {
                Bundle extras = data.getExtras();
                assert extras != null;
                if (extras != null){
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                } else {
                    Toast.makeText(getActivity(), "Vídeo :).", Toast.LENGTH_LONG).show();
                    Uri videoUri = data.getData();
                    videoView.setVideoURI(videoUri);
                    videoView.start();
                }
            } else if (requestCode == REQUEST_VIDEO_CAPTURE) {
                if (resultCode == Activity.RESULT_OK) {
                    Uri videoUri = data.getData();
                    videoView.setVideoURI(videoUri);
                    videoView.start();
                }
            }
        }
    }
}
