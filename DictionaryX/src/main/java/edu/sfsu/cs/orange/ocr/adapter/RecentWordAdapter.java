package edu.sfsu.cs.orange.ocr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.List;
import edu.sfsu.cs.orange.ocr.R;
import edu.sfsu.cs.orange.ocr.entity.Word;

public class RecentWordAdapter extends RecyclerView.Adapter<RecentWordAdapter.ViewHolder> {

    private List<Word> mRecentWords;
    private Context mContext;

    public RecentWordAdapter(Context context, List<Word> words) {
        this.mContext = context;
        this.mRecentWords = words;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recent_word, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Word word = mRecentWords.get(mRecentWords.size() - 1 - position);
        holder.tvNewWord.setText(word.getNew_word());
        holder.tvMeaning.setText(getMeaning(word.getMean()));
    }

    private String getMeaning(String text) {
        Document doc = Jsoup.parse(text);
        Elements fonts = doc.getElementsByTag("font");
        if (fonts != null && fonts.size() > 0) {
            return fonts.get(0).text();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mRecentWords.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNewWord;
        TextView tvMeaning;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNewWord = (TextView) itemView.findViewById(R.id.tvNewWord);
            tvMeaning = (TextView) itemView.findViewById(R.id.tvMeaning);
        }
    }
}