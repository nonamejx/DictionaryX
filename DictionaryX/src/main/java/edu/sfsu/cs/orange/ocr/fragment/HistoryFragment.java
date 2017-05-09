package edu.sfsu.cs.orange.ocr.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.sfsu.cs.orange.ocr.R;
import edu.sfsu.cs.orange.ocr.adapter.RecentWordAdapter;
import edu.sfsu.cs.orange.ocr.database.RealmHelper;
import edu.sfsu.cs.orange.ocr.entity.Word;
import edu.sfsu.cs.orange.ocr.util.DividerItemDecoration;

public class HistoryFragment extends Fragment {

    private RecyclerView rvRecentWords;
    private RealmHelper realmHelper;
    private List<Word> recentWords;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realmHelper = new RealmHelper(getContext());
        recentWords = realmHelper.getRecentWords();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        rvRecentWords = (RecyclerView) v.findViewById(R.id.rvRecentWords);
        RecentWordAdapter adapter = new RecentWordAdapter(getContext(), recentWords);
        rvRecentWords.setAdapter(adapter);
        rvRecentWords.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecentWords.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        return v;
    }
}