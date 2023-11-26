package com.jnu.student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.student.view.GameView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private GameView gameView;
    public GameViewFragment() {
        // Required empty public constructor
    }


    public static GameViewFragment newInstance(String param1, String param2) {
        GameViewFragment fragment = new GameViewFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_view, container, false);

    }
}