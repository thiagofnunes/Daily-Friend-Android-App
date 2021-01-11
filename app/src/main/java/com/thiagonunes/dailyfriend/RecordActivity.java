package com.thiagonunes.dailyfriend;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thiagonunes.dailyfriend.database.RecordRepository;
import com.thiagonunes.dailyfriend.model.MyViewModelFactory;
import com.thiagonunes.dailyfriend.model.Record;
import com.thiagonunes.dailyfriend.model.RecordEditorViewModel;
import com.thiagonunes.dailyfriend.recyclerview.FeelingListAdapter;
import com.thiagonunes.dailyfriend.utils.RecyclerViewInterface;
import com.thiagonunes.dailyfriend.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.StringTokenizer;

import static com.thiagonunes.dailyfriend.utils.Constants.KEY_RECORD_ID;
import static com.thiagonunes.dailyfriend.utils.Constants.RECORD_ALL_FEELINGS;
import static com.thiagonunes.dailyfriend.utils.Constants.RECORD_SELECTED_FEELINGS;
import static com.thiagonunes.dailyfriend.utils.Constants.RECORD_TEXT;
import static com.thiagonunes.dailyfriend.utils.Constants.RECORD_TITLE;

public class RecordActivity extends AppCompatActivity implements RecyclerViewInterface {

    private int id;
    private RecordEditorViewModel mRecordEditorViewModel;
    private EditText mTitleEt, mTextEt;
    private ArrayList<String> mAllFeelings;
    private ArrayList<String> mSelectedFeelings;
    private FeelingListAdapter mAllFeelingsAdapter;
    private FeelingListAdapter mSelectedFeelingsAdapter;
    private HashMap<String, Integer> mPreviousPositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(KEY_RECORD_ID, -1);
        }

        mTitleEt = findViewById(R.id.title_editText);
        mTextEt = findViewById(R.id.messageTextInputEditText);

        mAllFeelings = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.feelings_array)));
        mSelectedFeelings = new ArrayList<>();

        if (savedInstanceState != null) {
            id = savedInstanceState.getInt(KEY_RECORD_ID, -1);
            mTitleEt.setText(savedInstanceState.getString(RECORD_TITLE, ""));
            mTextEt.setText(savedInstanceState.getString(RECORD_TEXT, ""));
            mAllFeelings = savedInstanceState.getStringArrayList(RECORD_ALL_FEELINGS);
            mSelectedFeelings = savedInstanceState.getStringArrayList(RECORD_SELECTED_FEELINGS);
        }

        if (id != -1) {

            mRecordEditorViewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getApplication(), id)).get(RecordEditorViewModel.class);

            mRecordEditorViewModel.getRecord().observe(this, record -> {
                if (record != null) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(record.date);
                    mTitleEt.setText(record.title);
                    mTextEt.setText(record.text);
                    updateLists(record.feelings);


                    if (mAllFeelingsAdapter != null && mSelectedFeelingsAdapter != null) {
                        mAllFeelingsAdapter.notifyDataSetChanged();
                        mSelectedFeelingsAdapter.notifyDataSetChanged();
                    }
                }


            });
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(String.format("New (%s)", Utils.getDateStr()));
        }


        mPreviousPositions = new HashMap<>();

        generateMainRecyclerView();
        generateSelectedFeelingsRecyclerView();

        ImageView add_imageview = findViewById(R.id.add_imageview);
        add_imageview.setOnClickListener(v -> {
            addEmotion();
        });


    }

    private void updateLists(String feelings) {

        if (feelings.isEmpty() || feelings.equals("")) {
            return;
        }

        StringTokenizer stringTokenizer = new StringTokenizer(feelings, ",");
        while (stringTokenizer.hasMoreElements()) {
            String value = stringTokenizer.nextToken();
            mSelectedFeelings.add(value);
            mAllFeelings.remove(value);
        }
    }

    private void generateMainRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_feelings);
        mAllFeelingsAdapter = new FeelingListAdapter(this);
        recyclerView.setAdapter(mAllFeelingsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        mAllFeelingsAdapter.setFeelingsList(mAllFeelings);
        mAllFeelingsAdapter.notifyDataSetChanged();
        mAllFeelingsAdapter.setInterface(this);
        mAllFeelingsAdapter.setType(0);
    }

    private void generateSelectedFeelingsRecyclerView() {
        RecyclerView recyclerView2 = findViewById(R.id.recyclerview_selected_feelings);
        mSelectedFeelingsAdapter = new FeelingListAdapter(this);
        recyclerView2.setAdapter(mSelectedFeelingsAdapter);
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        mSelectedFeelingsAdapter.setFeelingsList(mSelectedFeelings);
        mSelectedFeelingsAdapter.notifyDataSetChanged();
        mSelectedFeelingsAdapter.setInterface(this);
        mSelectedFeelingsAdapter.setType(1);
    }

    private void addEmotion() {

        LayoutInflater layoutInflater = getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.alert_dialog_add_feeling, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.add_feeling));

        EditText feeling = (EditText) view.findViewById(R.id.input);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.ok), (dialog, which) -> {
            String text = feeling.getText().toString();
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.addAll(mAllFeelings);
            arrayList.addAll(mSelectedFeelings);
            boolean exists = false;
            for (String feelingS : arrayList) {
                if (feelingS.equalsIgnoreCase(text)) {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                addCustomFeelingToSelectedFeelings(text);
                dialog.dismiss();
            } else {
                feeling.setError(getString(R.string.feeling_already_exists));
            }

        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (id == -1) {
            menu.findItem(R.id.action_delete).setEnabled(false);
            menu.findItem(R.id.action_delete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int optionId = item.getItemId();

        if (optionId == R.id.action_delete) {
            if (id != -1) {
                mRecordEditorViewModel.deleteRecord(mRecordEditorViewModel.getRecord().getValue());
                getViewModelStore().clear();
                finish();
            }
            return true;
        } else if (optionId == R.id.action_save) {

            String title = mTitleEt.getText().toString();
            String text = mTextEt.getText().toString();

            if (title == null || title.isEmpty()) {
                title = getString(R.string.no_title);
            }

            if (text == null || text.isEmpty() || text.equals("")) {
                mTextEt.setError(getString(R.string.set_message));
                return true;
            }

            if (id == -1) {
                Record record = new Record();
                record.title = title;
                record.text = text;
                record.date = Utils.getDateStr();
                record.hour = Utils.getHourStr();
                record.feelings = setFeelingsFromChosenFeelings();
                RecordRepository mRepository = new RecordRepository(getApplication());
                mRepository.insert(record);

            } else {
                Record record = mRecordEditorViewModel.getRecord().getValue();
                record.title = title;
                record.text = text;
                record.feelings = setFeelingsFromChosenFeelings();
                mRecordEditorViewModel.insert(record);
            }

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String setFeelingsFromChosenFeelings() {
        if (mSelectedFeelings.isEmpty()) {
            return "";
        } else {
            String aux = "";
            for (String feeling : mSelectedFeelings) {
                aux += feeling + ",";
            }
            return aux;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(KEY_RECORD_ID, id);
        savedInstanceState.putString(RECORD_TITLE, mTitleEt.getText().toString());
        savedInstanceState.putString(RECORD_TEXT, mTextEt.getText().toString());
        savedInstanceState.putStringArrayList(RECORD_ALL_FEELINGS, mAllFeelings);
        savedInstanceState.putStringArrayList(RECORD_SELECTED_FEELINGS, mSelectedFeelings);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModelStore().clear();

    }

    @Override
    public void addItemToSelectedFeelings(String item, int position) {

        mSelectedFeelings.add(item);
        mSelectedFeelingsAdapter.notifyDataSetChanged();
        mAllFeelings.remove(position);
        mAllFeelingsAdapter.notifyDataSetChanged();
        mPreviousPositions.put(item, position);

    }

    @Override
    public void removeItemFromSelectedFeelings(String item) {

        mSelectedFeelings.remove(item);
        mSelectedFeelingsAdapter.notifyDataSetChanged();
        if (mPreviousPositions.containsKey(item)) {
            mAllFeelings.add(mPreviousPositions.get(item), item);
        } else {
            mAllFeelings.add(item);
        }
        mAllFeelingsAdapter.notifyDataSetChanged();

    }

    private void addCustomFeelingToSelectedFeelings(String feeling) {
        mSelectedFeelings.add(feeling);
        mSelectedFeelingsAdapter.notifyDataSetChanged();
    }
}