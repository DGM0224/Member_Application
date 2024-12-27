package siestageek.memberapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import siestageek.memberapplication.helper.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    // 변수 선언
    private EditText editTextUserid, editTextPassswd;
    private Button buttonLogin;
    private DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 변수 초기화
        editTextUserid = findViewById(R.id.editTextUserid);
        editTextPassswd = findViewById(R.id.editTextPasswd);
        buttonLogin = findViewById(R.id.buttonLogin);

        // databaseHelper 초기화
        databaseHelper = new DatabaseHelper(this);

        // sharePreferences 초기화
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);


        // 로그인 이벤트 처리
        buttonLogin.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 입력한 id/pass를 가져옴
                    String userid = editTextUserid.getText().toString().trim();
                    String passwd = editTextPassswd.getText().toString().trim();

                    // 로그인 성공 여부에 따른 처리
                    if(databaseHelper.loginUser(userid, passwd)) {
                        // 로그인 성공시 sharePreferences에 세션 데이터 저장
                        // sharedPreferences 수정을 위한 초기화
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply(); // 추가한 내용 저장

                        Intent intent = new Intent(LoginActivity.this, UserlistActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀렸습니다!", Toast.LENGTH_SHORT).show();
                    };




                }
            }
        );

        

    }
    
    

}