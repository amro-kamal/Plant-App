package org.tensorflow.lite.examples.classification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ResultLoadingFragment extends Fragment {

    public static ResultLoadingFragment newInstance(){
        return new ResultLoadingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
             super.onCreateView(inflater, container, savedInstanceState);
             return  inflater.inflate(R.layout.classification_result_loading_frag , container, false);
    }
}
