package um5.ssi.traininggps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditTitle extends AppCompatActivity {

    TextView tv_title_to_edit;
    EditText pt_edited_text;
    Button btn_ok, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_title);

        int id;
        String title;
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        title = intent.getStringExtra("title");

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_ok = findViewById(R.id.btn_ok);
        tv_title_to_edit = findViewById(R.id.tv_title_to_edit);
        pt_edited_text = findViewById(R.id.pt_edited_text);

        tv_title_to_edit.setText(title);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(EditTitle.this);
                boolean success = feedReaderDbHelper.editOne(pt_edited_text.getText().toString(),id);
                if(success) {
                    Toast.makeText(EditTitle.this, " Edited!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(EditTitle.this, " Edit Error", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(EditTitle.this,ShowSavedLocationsList.class);
                startActivity(i);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditTitle.this,ShowSavedLocationsList.class);
                startActivity(i);
            }
        });
    }
}