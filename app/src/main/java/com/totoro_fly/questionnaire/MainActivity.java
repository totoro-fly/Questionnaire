package com.totoro_fly.questionnaire;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.yes_radio_button)
    RadioButton yesRadioButton;
    @Bind(R.id.no_radio_button)
    RadioButton noRadioButton;
    @Bind(R.id.right_one_check_box)
    CheckBox rightOneCheckBox;
    @Bind(R.id.right_two_check_box)
    CheckBox rightTwoCheckBox;
    @Bind(R.id.answer_one_edit_view)
    EditText answerOneEditView;
    @Bind(R.id.answer_two_edit_view)
    EditText answerTwoEditView;
    @Bind(R.id.submit_button)
    Button submitButton;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.name_edit_view)
    EditText nameEditView;
    int score = 0;//计分
    int emailScorse = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toast.makeText(this, R.string.activity_main_explain, LENGTH_LONG).show();
    }

    /*取消焦点，关闭软键盘*/
    @OnTouch(R.id.activity_main)
    public boolean OnTouch() {
        answerOneEditView.clearFocus();
        answerTwoEditView.clearFocus();
        nameEditView.clearFocus();
        submitButton.setFocusable(true);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activityMain.getWindowToken(), 0);
        return true;
    }

    /*验证答案*/
    private void verifyAnswer() {
        if (yesRadioButton.isChecked())
            score++;
        if (rightOneCheckBox.isChecked() && rightTwoCheckBox.isChecked())
            score++;
        if (answerOneEditView.getText().toString().equals("2"))
            score++;
        if (answerTwoEditView.getText().toString().equals("2"))
            score++;
    }

    @OnClick({R.id.submit_button, R.id.email_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_button:
                verifyAnswer();
                OnTouch();
                displayAnswer();
                emailScorse = score;
                score = 0;
                break;
            case R.id.email_button:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"liujian180389@163.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Questionnaire APP");
                intent.putExtra(Intent.EXTRA_TEXT, nameEditView.getText().toString() + " " + emailScorse + getString(R.string.activity_main_point));
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);
                break;
        }
    }

    private void displayAnswer() {
        Toast.makeText(this, score + getString(R.string.activity_main_point), Toast.LENGTH_SHORT).show();
    }
}
