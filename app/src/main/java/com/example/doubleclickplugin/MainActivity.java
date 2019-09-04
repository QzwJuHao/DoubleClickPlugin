package com.example.doubleclickplugin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.jump_button).setOnClickListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        BlankFragment blankFragment = new BlankFragment();
        ft.add(R.id.fragment_framelayout,blankFragment,null);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.jump_button) {
            startActivity(new Intent(this, SecondActivity.class));
        }
    }
}
