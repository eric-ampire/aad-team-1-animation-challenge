package com.andela.app.animationchallenge.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andela.app.animationchallenge.model.Photo;
import com.andela.app.animationchallenge.util.LoadingState;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class PhotoViewModel extends ViewModel {
    private final MutableLiveData<List<Photo>> photo = new MutableLiveData<>();
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();
    private final MutableLiveData<LoadingState> loadingState = new MutableLiveData<>();


    private void init() {
        // Init loading
        loadingState.setValue(LoadingState.LOADING);

        Query collectionReference = database
                .collection("photos")
                .orderBy("date", Query.Direction.DESCENDING);

        collectionReference.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null || queryDocumentSnapshots == null) {
                loadingState.setValue(LoadingState.setError(e.getMessage()));
                return;
            }

            loadingState.setValue(LoadingState.LOADED);
            photo.setValue(queryDocumentSnapshots.toObjects(Photo.class));
        });
    }

    public LiveData<LoadingState> getLoadingState() { return loadingState; }
    public LiveData<List<Photo>> getPhoto() { return photo; }
}
