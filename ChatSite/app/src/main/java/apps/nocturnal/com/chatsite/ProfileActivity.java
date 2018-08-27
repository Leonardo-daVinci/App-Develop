package apps.nocturnal.com.chatsite;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView mProfileImage;
    private TextView mProfilename, mProfileStatus, mProfileFriends;
    private Button mProfileSendReq;

    private FirebaseUser mCurrentUser;

    private DatabaseReference mUsersdatabase,mFriendReqdatabase;
    private ProgressDialog mProg;

    private String mCurrent_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final String user_id = getIntent().getStringExtra("user_id");

        mProfileImage = findViewById(R.id.profile_image);
        mProfilename = findViewById(R.id.profile_displayName);
        mProfileFriends = findViewById(R.id.profile_totalFriends);
        mProfileStatus = findViewById(R.id.profile_status);
        mProfileSendReq = findViewById(R.id.profile_send_req_btn);

        mUsersdatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mFriendReqdatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mCurrent_state = "not_friends";

        mProg = new ProgressDialog(this);
        mProg.setTitle("Loading User data");
        mProg.setMessage("Please wait while we load the data required..");
        mProg.setCanceledOnTouchOutside(false);
        mProg.show();

        mUsersdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String display_name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                mProfilename.setText(display_name);
                mProfileStatus.setText(status);

                Picasso.get().load(image).placeholder(R.drawable.ic_profile).into(mProfileImage);

                mProg.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mProfileSendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrent_state.equals("not_friends")){

                    mFriendReqdatabase.child(mCurrentUser.getUid()).child(user_id).child("request_type")
                    .setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mFriendReqdatabase.child(user_id).child(mCurrentUser.getUid()).child("request_type")
                                        .setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ProfileActivity.this, "Request Sent! :) ",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else{
                                Toast.makeText(ProfileActivity.this, "Cannot send friend request. Try again later! ",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
