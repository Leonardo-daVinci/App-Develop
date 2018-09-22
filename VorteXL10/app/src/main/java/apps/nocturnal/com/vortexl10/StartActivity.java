package apps.nocturnal.com.vortexl10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class StartActivity extends AppCompatActivity {

    private Button mRegisterBtn,mSignInBtn;
    private ProgressBar mProgbar;
    private TextInputLayout mEmail,mPassword;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        mEmail = findViewById(R.id.start_email);
        mPassword = findViewById(R.id.start_password);
        mRegisterBtn = findViewById(R.id.start_registerbtn);
        mSignInBtn = findViewById(R.id.start_signinbtn);
        mProgbar =findViewById(R.id.start_progressBar);
        mProgbar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        mUserdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if( !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    mProgbar.setVisibility(View.VISIBLE);
                    loginUser(email,password,v);
                }
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent (StartActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

    }

    private void loginUser(String email, String password, final View v) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            mProgbar.setVisibility(View.INVISIBLE);

                            final String user_id = mAuth.getCurrentUser().getUid();
                            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                                @Override
                                public void onSuccess(InstanceIdResult instanceIdResult) {
                                    String token = instanceIdResult.getToken();

                                    mUserdatabase.child(user_id).child("token").setValue(token)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Intent mainIntent= new Intent(StartActivity.this,ScrollingActivity.class);
                                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(mainIntent);
                                                    finish();
                                                }
                                            });
                                }
                            });

                        }else{
                            mProgbar.setVisibility(View.INVISIBLE);
                            Snackbar.make(v, "Cannot login you right now, please try again!!", Snackbar.LENGTH_LONG);
                        }
                    }
                });
    }
}
