package com.codecraft.sifardenggi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    public String url= "https://idengue.mysa.gov.my/kluster.php";

    private RecyclerView recyclerView;
    private RecyclerviewItem2Adapter recyclerviewItemAdapter;
    private List<Items2> itemsList;
    private LinearLayout progressBar;

    public HomeFragment(String stateChoose) {
        Log.e("Negeriii", stateChoose + "");
        this.stateChoose = stateChoose;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    String stateChoose;
    TextView stateDaily;
    TextView stateCumulative;
    TextView sumDaily;
    TextView sumCumulative;
    TextView casedate1;
    TextView casedate2;
    TextView casecumdate1;
    TextView casecumdate2;
    TextView statename1;
    TextView statename2;
    public ArrayList<String> stateList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemsList = new ArrayList<>();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycleView2);

        recyclerviewItemAdapter = new RecyclerviewItem2Adapter(itemsList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerviewItemAdapter);
        progressBar = getActivity().findViewById(R.id.progress_circular2);

        stateDaily = getActivity().findViewById(R.id.state_daily);
        stateCumulative = getActivity().findViewById(R.id.state_cumulative);
        sumDaily = getActivity().findViewById(R.id.sum_daily);
        sumCumulative = getActivity().findViewById(R.id.sum_cumulative);
        casedate1 = getActivity().findViewById(R.id.case_date1);
        casedate2 = getActivity().findViewById(R.id.case_date2);
        casecumdate1 = getActivity().findViewById(R.id.case_cumdate1);
        casecumdate2 = getActivity().findViewById(R.id.case_cumdate2);
        statename1 = getActivity().findViewById(R.id.statename1);
        statename2 = getActivity().findViewById(R.id.statename2);

        LinearLayout changeState = getActivity().findViewById(R.id.changestate);
        changeState.setOnClickListener(v -> {

            String[] stateArr = new String[stateList.size()];
            stateArr = stateList.toArray(stateArr);
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Paparan tetap negeri");
            String[] finalStateArr = stateArr;
            builder.setItems(stateArr, (dialog, which) -> {
                String name = finalStateArr[which];

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("statename", name);
                editor.apply();

                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);

            });
            AlertDialog dialog = builder.create();
            dialog.show();

        });

        CardView alter = getActivity().findViewById(R.id.alternative);
        alter.setOnClickListener(v -> {
            Intent myIntent = new Intent(getActivity(), HalauActivity.class);
            getActivity().startActivity(myIntent);
        });

        CardView cegah = getActivity().findViewById(R.id.cegah);
        cegah.setOnClickListener(v -> {
            Intent myIntent = new Intent(getActivity(), PencegahanActivity.class);
            getActivity().startActivity(myIntent);
        });

        CardView gigitan = getActivity().findViewById(R.id.gigitan);
        gigitan.setOnClickListener(v -> {
            Intent myIntent = new Intent(getActivity(), GigitanActivity.class);
            getActivity().startActivity(myIntent);
        });

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        statename1.setText(stateChoose);
        statename2.setText("Kawasan Aktif Wabak Denggi (" + stateChoose + ")");

    }

    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("negeri", stateChoose)
                .addFormDataPart("preview_button", "Papar")
                .build();

        Log.e("url", url);
//        Log.e("requestBody", requestBody.);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseContent(myResponse);
                    }
                });
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void parseContent(String webResponce){
        Log.e("content", webResponce);
        String[] contents = StringUtils.substringsBetween(webResponce, "<tr valign='top' align='center' class='tbldata' >", "</tr>");


        ArrayList<String> negeriList = new ArrayList<>();
        ArrayList<String> daerahList = new ArrayList<>();
        ArrayList<String> lokalitiList = new ArrayList<>();

        if (contents != null){
            for (int i=0; i<contents.length;i++){

                String[] string = StringUtils.substringsBetween(contents[i], "<td class='tbldata' >", "</td>");
                String negeri = string[0];
//                Log.e("asdasdsad", negeri);
                String daerah = string[1];
                String lokaliti = string[2];

                negeriList.add(negeri);
                daerahList.add(daerah);
                lokalitiList.add(lokaliti);

//            Log.e();
            }
        }
        else {
            negeriList.add("");
            daerahList.add("");
            lokalitiList.add("Tiada kawasan aktif di negeri ini");
        }





        for (int i=0; i<negeriList.size(); i++ ){
            Items2 items = new Items2(negeriList.get(i), daerahList.get(i).toString(), lokalitiList.get(i).toString());
            itemsList.add(items);
        }

        progressBar.setVisibility(View.GONE);
        recyclerviewItemAdapter.notifyDataSetChanged();
    }

    public void setText(String stateDaily, String stateCumulative, String sumDaily, String sumCumulative, String yesterdayDate, String cumDate){
        this.stateDaily.setText(stateDaily);
        this.stateCumulative.setText(stateCumulative);
        this.sumDaily.setText(sumDaily);
        this.sumCumulative.setText(sumCumulative);

        this.casedate1.setText(yesterdayDate);
        this.casedate2.setText(yesterdayDate);
        this.casecumdate1.setText(cumDate);
        this.casecumdate2.setText(cumDate);
    }

}
