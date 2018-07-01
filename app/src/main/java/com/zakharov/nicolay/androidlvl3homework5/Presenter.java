package com.zakharov.nicolay.androidlvl3homework5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 1 on 01.07.2018.
 */

public class Presenter {
    MainView mainView;
    List<Model> modelList = new ArrayList<>();
    Endpoints restAPI;
    Realm realm;

    public Presenter(MainView view) {
        this.mainView = view;
    }

    public void load() {
        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/") // - обратить внимание на слэш в базовом адресе
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restAPI = retrofit.create(Endpoints.class);
        } catch (Exception io) {
            io.printStackTrace();
            mainView.setTextIntoTextView(io.getMessage());
            return;
        }
        // подготовили вызов на сервер
        Call<List<Model>> call = restAPI.loadUsers();
        if (mainView.getNetworkInfo()) {
            // запускаем
            try {
                mainView.setVisibilityProgressBar(true);
                downloadOneUrl(call);
            } catch (IOException e) {
                e.printStackTrace();
                mainView.setTextIntoTextView(e.getMessage());
            }
        } else {
            mainView.makeToast("Подключите интернет");
        }
    }


    // ------------------------------------ SQLite

    public void saveAllSQLite() {
        try {
            Date first = new Date();
            for (Model model : modelList) {
                mainView.getSQLiteHelper().saveUserInDateBase(model);
            }
            Date second = new Date();
            setStat(modelList.size(), first, second);
        } catch (Exception e) {
            e.printStackTrace();
            mainView.setTextIntoTextView(e.getMessage());
        }
    }

    public void selectAllSQLite() {
        try {
            Date first = new Date();
            modelList = new ArrayList<>();
            modelList.addAll(mainView.getSQLiteHelper().getAllUsersFromDataBase());
            Date second = new Date();
            setStat(modelList.size(), first, second);

            mainView.appendIntoTextView("\n-----------------");
            for (Model model : modelList) {
                mainView.appendIntoTextView(
                        "\nLogin = " + model.getLogin() +
                                "\nId = " + model.getUserId() +
                                "\nURI = " + model.getAvatar() +
                                "\n-----------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mainView.setTextIntoTextView(e.getMessage());
        }
    }

    public void deleteAllSQLite() {
        try {
            Date first = new Date();
            mainView.getSQLiteHelper().deleteAllUsersFromDataBase();
            selectAllSQLite();
            Date second = new Date();
            setStat(modelList.size(), first, second);
        } catch (Exception e) {
            e.printStackTrace();
            mainView.setTextIntoTextView(e.getMessage());
        }
    }


    // -------------------------------------------------------------- Realm

    public void saveAllRealm() {
        try {
            String curLogin = "";
            String curUserID = "";
            String curAvatarUrl = "";
            realm = Realm.getDefaultInstance();
            Date first = new Date();
            realm.beginTransaction();
            for (Model curItem : modelList) {
                curLogin = curItem.getLogin();
                curUserID = curItem.getUserId();
                curAvatarUrl = curItem.getAvatar();
                try {
                    realm.beginTransaction();
                    RealmModel realmModel = realm.createObject(RealmModel.class);
                    realmModel.setUserID(curUserID);
                    realmModel.setLogin(curLogin);
                    realmModel.setAvatarUrl(curAvatarUrl);
                    realm.commitTransaction();
                } catch (Exception e) {
                    realm.cancelTransaction();
                    e.printStackTrace();
                    mainView.setTextIntoTextView(e.getMessage());
                }
            }
            Date second = new Date();
            long count = realm.where(RealmModel.class).count();
            setStat((int) count, first, second);
            realm.close();
        } catch (Exception e) {
            e.printStackTrace();
            mainView.setTextIntoTextView(e.getMessage());
        }
    }

    public void selectAllRealm() {
        try {
            realm = Realm.getDefaultInstance();
            Date first = new Date();
            RealmResults<RealmModel> tempList = realm.where(RealmModel.class).findAll();
            Date second = new Date();
            setStat(tempList.size(), first, second);
            realm.close();
        } catch (Exception e) {
            e.printStackTrace();
            mainView.setTextIntoTextView(e.getMessage());
        }
    }

    public void deleteAllRealm() {
        try {
            realm = Realm.getDefaultInstance();
            final RealmResults<RealmModel> tempList = realm.where(RealmModel.class).findAll();
            Date first = new Date();
            realm.executeTransaction(realm -> tempList.deleteAllFromRealm());
            Date second = new Date();
            setStat(tempList.size(), first, second);
            realm.close();
        } catch (Exception e) {
            e.printStackTrace();
            mainView.setTextIntoTextView(e.getMessage());
        }
    }


    private void setStat(int count, Date first, Date second) {
        mainView.setTextIntoTextView("количество = " + count + "\n");
        mainView.appendIntoTextView("милисекунд = " + (second.getTime() - first.getTime()));
    }

    private void downloadOneUrl(Call<List<Model>> call) throws IOException {
        call.enqueue(new Callback<List<Model>>() {

            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                if (response.isSuccessful()) {
                    if (response != null) {
                        Model curModel = null;
                        mainView.appendIntoTextView("\n Size = " + response.body().size() +
                                "\n-----------------");
                        for (int i = 0; i < response.body().size(); i++) {
                            curModel = response.body().get(i);
                            modelList.add(curModel);
                            mainView.appendIntoTextView(
                                    "\nLogin = " + curModel.getLogin() +
                                            "\nId = " + curModel.getUserId() +
                                            "\nURI = " + curModel.getAvatar() +
                                            "\n-----------------");
                        }
                    }
                } else {
                    System.out.println("onResponse error: " + response.code());
                    mainView.setTextIntoTextView("onResponse error: " + response.code());
                }
                mainView.setVisibilityProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                System.out.println("onFailure " + t);
                mainView.setTextIntoTextView("onFailure " + t.getMessage());
                mainView.setVisibilityProgressBar(false);
            }
        });
    }
}
