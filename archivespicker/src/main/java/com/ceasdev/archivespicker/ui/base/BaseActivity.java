package com.ceasdev.archivespicker.ui.base;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.material.snackbar.Snackbar;
import android.graphics.Color;

public abstract class BaseActivity<BINDING extends BaseViewBinding, MODEL extends ViewModel> extends AppCompatActivity{

    protected BINDING binding;
    protected MODEL viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = buidViewBinding(LayoutInflater.from(this));
        setContentView(binding.rootView);
        viewModel = buildViewModel(getIntent());
        init(savedInstanceState);
    }

    @NonNull
    protected abstract BINDING buidViewBinding(LayoutInflater layoutInflater);

    @NonNull
    protected abstract MODEL buildViewModel(Intent intent);

    protected abstract void init(Bundle savedInstanceState);


    protected void toast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void toastLong(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }


    protected void snackAction(View view, String title, String button, View.OnClickListener onClickListener){
        Snackbar.make(view, title, Snackbar.LENGTH_INDEFINITE)
            .setActionTextColor(Color.WHITE)
            .setAction(button, onClickListener).show();
    }


    protected void snackLong(View view, String title){
        Snackbar.make(view, title, Snackbar.LENGTH_LONG)
            .setActionTextColor(Color.WHITE)
            .show();
    }
    
    
    protected void snack(View view, String title){
        Snackbar.make(view, title, Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.WHITE)
            .show();
    }
    

}
