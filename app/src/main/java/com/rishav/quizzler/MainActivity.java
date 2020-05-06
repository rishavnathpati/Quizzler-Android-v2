package com.rishav.quizzler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static com.rishav.quizzler.R.color.colorAccent;
import static com.rishav.quizzler.R.color.colorPrimary;
import static com.rishav.quizzler.R.color.colorPrimaryDark;


public class MainActivity extends Activity
{
    // TODO: Declare member variables here:
    Button mTrueButton, mFalseButton, mChangeBackground,mInfoButton;
    TextView mQuestionTextView;
    int mIndex, mQuestion, mScore, mCount;
    ProgressBar mProgressBar;
    TextView mScoreTextView;
    RelativeLayout mRelativeLayout;

    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13, true)
    };

    // TODO: Declare constants here
    final int PROGRESS_BAR_INCREMENT = (int) (Math.ceil(100.0 / mQuestionBank.length));

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null)
        {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        } else
        {
            mScore = 0;
            mIndex = 0;
        }

        //Linking all elements using findViewByID
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mScoreTextView = findViewById(R.id.score);
        mProgressBar = findViewById(R.id.progress_bar);
        mChangeBackground = findViewById(R.id.background_change_button);
        mRelativeLayout = findViewById(R.id.backgroundRelativeLayout);
        mInfoButton  = findViewById(R.id.info_button);


        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);

        mInfoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder devInfo = new AlertDialog.Builder(MainActivity.this);
                devInfo.setTitle("Dev Information");
                devInfo.setMessage("Developer: Rishav Nath Pati" +
                        "\nClick the links below to visit profiles");
                devInfo.setCancelable(true);
                devInfo.setNeutralButton("Facebook", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        goToUrl("https://www.facebook.com/rishav.pati.1");
                    }
                });
                devInfo.setPositiveButton("Github", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        goToUrl("https://github.com/rishavnathpati");
                    }
                });
                devInfo.setNegativeButton("LinkedIn", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        goToUrl("https://www.linkedin.com/in/rishav-nath-p-67223bb9/");
                    }
                });
                devInfo.show();
            }

            private void goToUrl(String url)
            {
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        mChangeBackground.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("Background", "Background to be changed");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    updateBackgroundColour(mCount);
                }
                mCount = (mCount + 1) % 3;
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    checkAnswer(true);
                }
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    checkAnswer(false);
                }
                updateQuestion();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateBackgroundColour(int count)
    {
        Window window = getWindow();

        if (count == 0)
        {
            mRelativeLayout.setBackgroundColor(getResources().getColor(colorPrimary));
            mChangeBackground.setBackgroundColor(getResources().getColor(colorPrimary));
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                window.setStatusBarColor(getResources().getColor(colorPrimary));
            }
        } else if (count == 1)
        {
            mRelativeLayout.setBackgroundColor(getResources().getColor(colorAccent));
            mChangeBackground.setBackgroundColor(getResources().getColor(colorAccent));
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                window.setStatusBarColor(getResources().getColor(colorAccent));
            }
        } else if (count == 2)
        {
            mRelativeLayout.setBackgroundColor(getResources().getColor(colorPrimaryDark));
            mChangeBackground.setBackgroundColor(getResources().getColor(colorPrimaryDark));
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                window.setStatusBarColor(getResources().getColor(colorPrimaryDark));
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateQuestion()
    {
        mIndex = (mIndex + 1) % mQuestionBank.length;
        if (mIndex == 0)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + " score");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    finish();
                }
            });
            alert.show();
        }
        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(boolean userSelection)
    {
        Window window = getWindow();
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();
        if (userSelection == correctAnswer)
        {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore++;
            mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.colorGreenLight));
            mChangeBackground.setBackgroundColor(getResources().getColor(R.color.colorGreenLight));
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                window.setStatusBarColor(getResources().getColor(R.color.colorGreenLight));
            }
        } else
        {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.colorRedLight));
            mChangeBackground.setBackgroundColor(getResources().getColor(R.color.colorRedLight));
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                window.setStatusBarColor(getResources().getColor(R.color.colorRedLight));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
    }
}
