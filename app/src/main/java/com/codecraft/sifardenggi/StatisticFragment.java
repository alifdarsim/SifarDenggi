package com.codecraft.sifardenggi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StatisticFragment extends Fragment {

    public String url= "https://idengue.mysa.gov.my/index.php";

    private RecyclerView recyclerView;
    private RecyclerviewItemAdapter recyclerviewItemAdapter;
    private List<Items> itemsList;
    private LinearLayout progressBar;
    private TextView deathtotal;
    private TextView deathfrom;

    private MainActivity mainActivity;

    public StatisticFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        itemsList = new ArrayList<>();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycleView);

        recyclerviewItemAdapter = new RecyclerviewItemAdapter(itemsList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerviewItemAdapter);

        deathtotal = getActivity().findViewById(R.id.death);
        deathfrom = getActivity().findViewById(R.id.deathdaripada);
        progressBar = getActivity().findViewById(R.id.progress_circular);

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("SetTextI18n")
    void parseContent(String webResponce){
//        Log.e("Ini Mula", webResponce);
        webResponce = webResponce.replace("<!--<h4 class=\"copyright\">", "");
        String con = StringUtils.substringBetween(webResponce, "<h4 class=\"copyright\"", "</p>");
        String deathdate = StringUtils.substringBetween(con, "\"maklumat_terkini\">", " </h4>");
        String death = StringUtils.substringBetween(con, "KEMATIAN: ", " Orang");

        deathfrom.setText("Januari sehingga " + deathdate);
        deathtotal.setText(death);

        String content = StringUtils.substringBetween(webResponce, "<table", "/table>");
        Log.e("222", "222");
        content = StringUtils.replace(content, " align=\"center\"", "");
        content = StringUtils.replace(content, " style='color:white;text-align:center'", "");
        content = StringUtils.replace(content, " style='color:white;'", "");
        content = StringUtils.replace(content, "<BR>", "");
        content = StringUtils.replace(content, "<br>", "");
        content = StringUtils.replace(content, "<span id='txt'> ", "");
        content = StringUtils.replace(content, "</span>", "");
        content = content.replace("\n", "").replace("\r", "").replace("\t", "");
        Log.e("555", content);
        content = StringUtils.replace(content, "<th>", "</th><th>");
        content = StringUtils.replace(content, "<tr", "</th>");
        Log.e("333", "333");
//        Log.e("444", content);
        String[] headerContents = StringUtils.substringsBetween(content, "<th>", "</th>");
        Log.e("table1", headerContents[0]);
        Log.e("table2", headerContents[1]);
        headerContents[1] = headerContents[1].replace("KES HARIAN PADA", "Kes harian pada");
        headerContents[2] = headerContents[2].replace("*JUMLAH KES TERKUMPUL DARI", "Kes kumulatif dari").replace("HINGGA", "-");
        Log.e("table3", headerContents[2]);
        String[] rowContents = StringUtils.substringsBetween(content, "<td>", "</td>");
        List<String> stateList = new ArrayList<>();
        List<Integer> dailyList = new ArrayList<>();
        List<Integer> cumulativeList = new ArrayList<>();
        TextView stateHeader = getActivity().findViewById(R.id.state_header);
        TextView dailyHeader = getActivity().findViewById(R.id.daily_header);
        TextView cumulativeHeader = getActivity().findViewById(R.id.cumulative_header);
        TextView statefooter = getActivity().findViewById(R.id.state_footer);
        TextView dailyfooter = getActivity().findViewById(R.id.daily_footer);
        TextView cumulativefooter = getActivity().findViewById(R.id.cumulative_footer);

        stateHeader.setText("Negeri");
        dailyHeader.setText(headerContents[1]);
        cumulativeHeader.setText(headerContents[2]);
        statefooter.setText("Total");

        int j = 0;
        for (int i=0; i<rowContents.length; i++)
        {

            if (0 == i-(3*(j))) {
                mainActivity.homeFragment.stateList.add(rowContents[i]);
            }

            String rowContent = rowContents[i].replace(",", "");
            rowContent = rowContent.replaceAll("\\s+","");
            
            Log.e("rowcontent", rowContent);
            if (0 == i-(3*(j))) {
                stateList.add(rowContent);
            }
            else if (1 == i-(3*(j))) {
                dailyList.add(Integer.parseInt(rowContent.replaceAll("\\s+","")));
            }
            else if (2 == i-(3*(j))) {
                cumulativeList.add(Integer.parseInt(rowContent.replaceAll("\\s+","")));
                j = j + 1;
            }
        }

        dailyfooter.setText(sum(dailyList).toString());
        cumulativefooter.setText(sum(cumulativeList).toString());

        for (int i=0; i<stateList.size(); i++ ){
            Items items = new Items(stateList.get(i), dailyList.get(i).toString(), cumulativeList.get(i).toString());
            itemsList.add(items);
        }

        progressBar.setVisibility(View.GONE);
       recyclerviewItemAdapter.notifyDataSetChanged();

        stateList.add("Malaysia");

        mainActivity.setData(stateList, dailyList, cumulativeList, headerContents[1], headerContents[2]);
    }

    Integer sum(List<Integer> m){
        Integer sum = 0;
        for(int i = 0; i < m.size(); i++)
            sum += m.get(i);
        return sum;
    }

    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Log.e("start22", url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
//                Log.e("start111", myResponse);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("aaaa", "masuk");
                        parseContent(myResponse);
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

}
