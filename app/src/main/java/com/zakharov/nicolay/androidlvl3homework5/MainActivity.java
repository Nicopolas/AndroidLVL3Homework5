package com.zakharov.nicolay.androidlvl3homework5;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements MainView {

    private TextView mInfoTextView;
    private ProgressBar progressBar;
    Presenter mPresenter;
    Button btnLoad;
    Button btnSaveAllSQLite;
    Button btnSelectAllSQLite;
    Button btnDeleteAllSQLite;
    Button btnSaveAllRealm;
    Button btnSelectAllRealm;
    Button btnDeleteAllRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new Presenter(this);
        mInfoTextView = (TextView) findViewById(R.id.tvLoad);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnSaveAllSQLite = (Button) findViewById(R.id.btnSaveAllSQLite);
        btnSelectAllSQLite = (Button) findViewById(R.id.btnSelectAllSQLite);
        btnDeleteAllSQLite = (Button) findViewById(R.id.btnDeleteAllSQLite);
        btnSaveAllRealm = (Button) findViewById(R.id.btnSaveAllRealm);
        btnSelectAllRealm = (Button) findViewById(R.id.btnSelectAllRealm);
        btnDeleteAllRealm = (Button) findViewById(R.id.btnDeleteAllRealm);
        btnLoad.setOnClickListener(v -> mPresenter.load());
        btnSaveAllSQLite.setOnClickListener(v -> mPresenter.saveAllSQLite());
        btnSelectAllSQLite.setOnClickListener(v -> mPresenter.selectAllSQLite());
        btnDeleteAllSQLite.setOnClickListener(v -> mPresenter.deleteAllSQLite());
        btnSaveAllRealm.setOnClickListener(v -> mPresenter.saveAllRealm());
        btnSelectAllRealm.setOnClickListener(v -> mPresenter.selectAllRealm());
        btnDeleteAllRealm.setOnClickListener(v -> mPresenter.deleteAllRealm());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public SQLiteHelper getSQLiteHelper() {
        return new SQLiteHelper(this);
    }

    @Override
    public void appendIntoTextView(String str) {
        mInfoTextView.append(str);
    }

    @Override
    public void setTextIntoTextView(String str) {
        mInfoTextView.setText("");
        mInfoTextView.setText(str);
    }

    @Override
    public void setVisibilityProgressBar(boolean visibility) {
        if (visibility) {
            progressBar.setVisibility(View.VISIBLE);
            return;
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean getNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public void makeToast(String string) {
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.show();
    }
}
