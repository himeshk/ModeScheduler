package com.example.igulhane73.appnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.igulhane73.appnew.dbOps.ConfigDatabaseOperations;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private final int REQUEST_CODE = 20;
    EventViewAdapter eventViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ensuring all database exists or created
        ConfigDatabaseOperations cdo = new ConfigDatabaseOperations(getBaseContext());
        //cdo.dropDB(cdo);
        cdo.createTables(cdo);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.event_list);
        eventViewAdapter= new EventViewAdapter(this,getData());
        recyclerView.setAdapter(eventViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button = (Button) findViewById(R.id.add_event);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // UserEvent event= new UserEvent();
                //event.title="asdad";
                //eventViewAdapter.eventList.add(event);
               //List<Event> list= eventViewAdapter.eventList;
                //eventViewAdapter= new EventViewAdapter(v.getContext(),list);
               // recyclerView.setAdapter(eventViewAdapter);
                //AddDialog addDialog = new AddDialog();
                //addDialog.show(getFragmentManager(),"Add Alert");
                //FragmentManager fragmentManager = getFragmentManager();
                //FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
                //fragmentTransaction.add(R.id.list,addDialog,"my");
                //fragmentTransaction.commit();
                Intent i = new Intent(MainActivity.this, AddDialog.class);
                i.putExtra("mode", 2); // pass arbitrary data to launched activity
                startActivityForResult(i, REQUEST_CODE);

            }
        });
    }


    public static List<UserEvent> getData(){
        List<UserEvent> result= new ArrayList<UserEvent>();
      /*  String[] op={"a","b","c","d","e","f"};
        for (int i = 0; i < op.length; i++) {
            UserEvent event= new UserEvent();
            event.title=op[i];
            result.add(event);
        }*/
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"Selected "+ item.getTitle()+" Option",Toast.LENGTH_SHORT);
            return true;
        }
        if (id == R.id.navigate) {
            startActivity(new Intent(this,SubActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    public void addEvent(UserEvent event) {
     eventViewAdapter.addEvent(event);
    }


    public void deleteEvent() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract Event value from result extras
            UserEvent event= (UserEvent) data.getExtras().getSerializable("event");
            addEvent(event);

        }
    }

}
