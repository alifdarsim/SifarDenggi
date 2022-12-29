package com.codecraft.sifardenggi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String desc = "Aplikasi yang dibangunkan untuk tujuan meningkatkan kesedaran masyarakat " +
//                "mengenai penyakit denggi di lokaliti masing-masing dan seluruh negara serta " +
//                "kaedah pencegahannya. Setiap daripada kita boleh membantu mencegah pembiakan " +
//                "nyamuk Aedes serta mengawal wabak denggi.";
//        TextView descTxt = (TextView) getActivity().findViewById(R.id.aboutdesc);
//        descTxt.setText(desc);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

}
