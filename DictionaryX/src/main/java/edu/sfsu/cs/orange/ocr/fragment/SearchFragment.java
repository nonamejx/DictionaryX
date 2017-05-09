package edu.sfsu.cs.orange.ocr.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import edu.sfsu.cs.orange.ocr.R;
import edu.sfsu.cs.orange.ocr.database.RealmHelper;
import edu.sfsu.cs.orange.ocr.entity.Word;

public class SearchFragment extends Fragment {
    private HtmlTextView htmlTVDefinition;
    private EditText edtSearch;
    private OnSearchListener searchListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchListener = (OnSearchListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        htmlTVDefinition = (HtmlTextView) view.findViewById(R.id.htmlTVDefinition);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    new SearchAsyncTask().execute(edtSearch.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT] != null && event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // clear text
                        edtSearch.setText("");
                        // invisible cross button
                        edtSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        // set focus and show keyboard
                        edtSearch.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
                        return true;
                    }
                }
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_black, 0);
                } else {
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public interface OnSearchListener {
        void onSearchCompleted();

        void onClearKeyword();
    }

    private class SearchAsyncTask extends AsyncTask<String, Word, Word> {

        @Override
        protected Word doInBackground(String... params) {
            Word word = new RealmHelper(getActivity()).getWord(params[0]);
            if (word == null) return null;
            return word.clone();
        }

        @Override
        protected void onPostExecute(Word word) {
            // Search Completed
            searchListener.onSearchCompleted();

            if (word == null) {
                htmlTVDefinition.setHtml("No words found !");
            } else {
                htmlTVDefinition.setHtml(word.getMean());
            }
        }
    }
}