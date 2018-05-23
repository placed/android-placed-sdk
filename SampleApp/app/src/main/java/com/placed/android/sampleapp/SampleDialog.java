package com.placed.android.sampleapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

enum SampleDialogAction {
    TERMS, PRIVACY, ACCEPT, CANCEL
}

public class SampleDialog extends Dialog {
    public interface SampleDialogListener {
        void onActionReceived(SampleDialogAction action);
    }

    public SampleDialogListener onActionListener;

    SampleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sample_dialog);

        setupButtons();
    }

    private void setupButtons() {
        findViewById(R.id.btn_terms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionListener.onActionReceived(SampleDialogAction.TERMS);
            }
        });

        findViewById(R.id.btn_privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionListener.onActionReceived(SampleDialogAction.PRIVACY);
            }
        });

        findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionListener.onActionReceived(SampleDialogAction.ACCEPT);
                dismiss();
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionListener.onActionReceived(SampleDialogAction.CANCEL);
                dismiss();
            }
        });
    }
}
