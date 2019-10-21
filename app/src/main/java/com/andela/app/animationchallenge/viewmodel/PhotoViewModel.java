package com.andela.app.animationchallenge.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andela.app.animationchallenge.model.Photo;
import com.andela.app.animationchallenge.util.LoadingStatus;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class PhotoViewModel extends ViewModel {
    private final MutableLiveData<List<Photo>> photo = new MutableLiveData<>();
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();
    private final MutableLiveData<LoadingStatus> loadingState = new MutableLiveData<>();

    public PhotoViewModel() {
        init();
    }

    private void init() {
        // Init loading
        loadingState.setValue(LoadingStatus.RUNNING);

        Query collectionReference = database
                .collection("photos")
                .orderBy("date", Query.Direction.DESCENDING);

        collectionReference.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null || queryDocumentSnapshots == null) {
                loadingState.setValue(LoadingStatus.FAILED);
                return;
            }

            loadingState.setValue(LoadingStatus.SUCCESS);
            photo.setValue(queryDocumentSnapshots.toObjects(Photo.class));
        });
    }

    public LiveData<LoadingStatus> getLoadingStatus() { return loadingState; }
    public LiveData<List<Photo>> getPhoto() { return photo; }
}
