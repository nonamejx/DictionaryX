package edu.sfsu.cs.orange.ocr.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import edu.sfsu.cs.orange.ocr.R;
import edu.sfsu.cs.orange.ocr.database.DatabaseHelper;
import edu.sfsu.cs.orange.ocr.entity.Word;

public class SearchFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    private HtmlTextView htmlTVDefinition;
    private EditText edtSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        htmlTVDefinition = (HtmlTextView) view.findViewById(R.id.htmlTVDefinition);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    new SearchAsyncTask().execute(edtSearch.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    private class SearchAsyncTask extends AsyncTask<String, Word, Word> {

        @Override
        protected Word doInBackground(String... params) {
            Word word = databaseHelper.search(params[0]);
            return word;
        }

        @Override
        protected void onPostExecute(Word word) {
            if (word == null) {
                htmlTVDefinition.setHtml("No words found !");
            } else {
                htmlTVDefinition.setHtml(word.getMean());
            }
        }
    }
}