package apps.nocturnal.com.chatsite;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mUsersList;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mToolbar = (Toolbar) findViewById(R.id.users_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsersList = (RecyclerView) findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Override
    protected void onStart() {
        super.onStart();

        /*FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                       // .setQuery(query, Users.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Users, UsersViewholder>(options) {
            @Override
            public UsersViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_adapter, parent, false);

                return new UsersViewholder( view);
            }

            @Override
            protected void onBindViewHolder(UsersViewholder holder, int position, Users users) {
                // Bind the Chat object to the ChatHolder
               holder.setName(users.getName());
            }
        };

        mUsersList.setAdapter(adapter);


     /*  FirebaseRecyclerAdapter<Users,UsersViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewholder>() {
           @Override
           protected void onBindViewHolder(@NonNull UsersViewholder holder, int position, @NonNull Users model) {

           }

           @NonNull
           @Override
           public UsersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               return null;
           }
       };*/

    }

    public static class UsersViewholder extends RecyclerView.ViewHolder{

        View mView;

        public UsersViewholder(View itemView) {
            super(itemView);

            mView = itemView;
            }

            public void setName(String name){
                TextView userNameView = mView.findViewById(R.id.users_display_name);
                userNameView.setText(name);
            }
    }
}
