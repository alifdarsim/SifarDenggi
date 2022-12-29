package com.codecraft.sifardenggi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class PosterFragment extends Fragment {

    public PosterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.poster1);
        Glide.with(getActivity()).load("https://pbs.twimg.com/media/DuSNqBZVAAAKdMw.jpg").into(imageView);

        ImageView imageView2 = (ImageView) getActivity().findViewById(R.id.poster2);
        Glide.with(getActivity()).load("https://www.infosihat.gov.my/images/media_sihat/infografik/jpeg/Denggi%20-%20Tanda-tanda%20Penyakit%20Denggi.jpg").into(imageView2);

        ImageView imageView3 = (ImageView) getActivity().findViewById(R.id.poster3);
        Glide.with(getActivity()).load("https://scontent.fszb2-1.fna.fbcdn.net/v/t1.6435-0/p526x296/50182952_10155809631916237_3585879010158051328_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=730e14&_nc_ohc=kxbjzMic660AX-e9vX4&_nc_ht=scontent.fszb2-1.fna&tp=6&oh=38d894021ae058b655e8a99b7b3e2c5b&oe=60E65D1E").into(imageView3);

        ImageView imageView4 = (ImageView) getActivity().findViewById(R.id.poster4);
        Glide.with(getActivity()).load("https://www.mmgazette.com/wp-content/uploads/2014/01/1556352_346260288847326_423160872_o.jpg").into(imageView4);

        ImageView imageView5 = (ImageView) getActivity().findViewById(R.id.poster5);
        Glide.with(getActivity()).load("https://media.siraplimau.com/wp-content/uploads/2019/06/FB_IMG_1560697025652.jpg").into(imageView5);

        ImageView imageView6 = (ImageView) getActivity().findViewById(R.id.poster6);
        Glide.with(getActivity()).load("https://prebiu.com/wp-content/uploads/2020/07/Denggi-Simptom.jpg").into(imageView6);

        YouTubePlayerView youTubePlayerView = getActivity().findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        YouTubePlayerView youTubePlayerView2 = getActivity().findViewById(R.id.youtube_player_view2);
        getLifecycle().addObserver(youTubePlayerView2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poster, container, false);
    }

}
