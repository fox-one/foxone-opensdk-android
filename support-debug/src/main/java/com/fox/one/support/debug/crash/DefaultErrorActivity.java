package com.fox.one.support.debug.crash;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.*;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fox.one.support.debug.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.DialogInterface.*;


public final class DefaultErrorActivity extends AppCompatActivity {

    @SuppressLint("PrivateResource")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This is needed to avoid a crash if the developer has not specified
        //an app-level theme that extends Theme.AppCompat
        TypedArray a = obtainStyledAttributes(R.styleable.AppCompatTheme);
        if (!a.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        }
        a.recycle();

        setContentView(R.layout.customactivityoncrash_default_error_activity);

        //Close/restart button logic:
        //If a class if set, use restart.
        //Else, use close and just finish the app.
        //It is recommended that you follow this logic if implementing a custom error activity.
        Button restartButton = (Button) findViewById(R.id.customactivityoncrash_error_activity_restart_button);

        final F1CrashConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());

        if (config.isShowRestartButton() && config.getRestartActivityClass()!=null) {
            restartButton.setText(R.string.customactivityoncrash_error_activity_restart_app);
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.restartApplication(DefaultErrorActivity.this, config);
                }
            });
        } else {
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.closeApplication(DefaultErrorActivity.this, config);
                }
            });
        }

        Button moreInfoButton = (Button) findViewById(R.id.customactivityoncrash_error_activity_more_info_button);

        if (config.isShowErrorDetails()) {
            moreInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //We retrieve all the error data and show it

                    View contentView = LayoutInflater.from(DefaultErrorActivity.this).inflate(R.layout.dialog_error_detail, null);
                    Button copyButton = contentView.findViewById(R.id.btn_copy);
                    Button reportButton = contentView.findViewById(R.id.btn_report);
                    Button closeButton = contentView.findViewById(R.id.btn_close);
                    TextView contentTxt = contentView.findViewById(R.id.txt_content);

                    contentTxt.setText(CustomActivityOnCrash.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent()));

                    final AlertDialog dialog = new AlertDialog.Builder(DefaultErrorActivity.this)
                            .setTitle(R.string.customactivityoncrash_error_activity_error_details_title)
                            .setCustomTitle(contentView)
                            .create();

                    copyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            copyErrorToClipboard();
                            Toast.makeText(DefaultErrorActivity.this, R.string.customactivityoncrash_error_activity_error_details_copied, Toast.LENGTH_SHORT).show();
                        }
                    });

                    reportButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
                                String subject = getPackageName() + "." + pi.versionName + "." + pi.versionCode + System.currentTimeMillis();
                                String[] emails = {"support@fox.zendesk.com"};
                                sendEmail(subject, CustomActivityOnCrash.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent()),
                                        getString(R.string.error_report), emails);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
        } else {
            moreInfoButton.setVisibility(View.GONE);
        }

        Integer defaultErrorActivityDrawableId = config.getErrorDrawable();
        ImageView errorImageView = ((ImageView) findViewById(R.id.customactivityoncrash_error_activity_image));

        if (defaultErrorActivityDrawableId != null) {
            errorImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), defaultErrorActivityDrawableId, getTheme()));
        }
    }

    private void copyErrorToClipboard() {
        String errorInformation = CustomActivityOnCrash.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent());

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(R.string.customactivityoncrash_error_activity_error_details_clipboard_label), errorInformation);
        clipboard.setPrimaryClip(clip);
    }

    private void sendEmail(String subject, String text, String chooserTitle, String[] emails) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);

            startActivity(Intent.createChooser(intent, chooserTitle));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
