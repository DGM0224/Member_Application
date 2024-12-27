package siestageek.memberapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import siestageek.memberapplication.helper.DatabaseHelper;
import siestageek.memberapplication.helper.MariaDBHelper;

public class MainActivity extends AppCompatActivity {

    // 변수선언
    private EditText editTextUserid, editTextPassswd, editTextName, editTextEmail;
    private Button buttonJoin, buttonUserlist;
//    private DatabaseHelper databaseHelper;
    private MariaDBHelper MariaDBHelper;

    // 경량 데이터를 저장하기 위한 내부 객체
    // 데이터는 보통 Key-Value 형태로 앱의 내부 저장소에 저장
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 뷰 초기화
        editTextUserid = findViewById(R.id.editTextUserid);
        editTextPassswd = findViewById(R.id.editTextPasswd);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonJoin = findViewById(R.id.buttonJoin);
        buttonUserlist = findViewById(R.id.buttonUserlist);

        // 데이터베이스 헬퍼 초기화
    //    databaseHelper = new DatabaseHelper(this);
        MariaDBHelper = new MariaDBHelper();

        // sharePreferences 초기화
        // MODE_PRIVATE " 특정 앱만 접근 가능
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // 회원가입 이벤트 처리
        buttonJoin.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerUser();
                }
            }
        );

        // 회원조회 이벤트 처리
        // 로그인 되어있다면 - UserListActivity로 이동
        // 로그인 되어있지 않다면 - LoginActivity로 이동
        buttonUserlist.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 로그인 관련 변수 가져오기
                    // getBoolean(Key, defaultValue)
                    boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                    if(isLoggedIn) { // 로그인 했다면
                        // UserListActivity를 View에 표시
                        Intent intent = new Intent(MainActivity.this, UserlistActivity.class);
                        startActivity(intent);
                        //Toast.makeText(MainActivity.this, "✨UserListActivity!✨", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        //Toast.makeText(MainActivity.this, "✨LoginActivity!✨", Toast.LENGTH_SHORT).show();
                    }
                }
            }
      );
        
        
        
    }

    private void registerUser() {
        // 변수 초기화
        String userid = editTextUserid.getText().toString().trim();
        String passwd = editTextPassswd.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();

        // 입력값 검증
        if (userid.isEmpty() || passwd.isEmpty()
                    || name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력하세요!", Toast.LENGTH_SHORT).show();
            return;  // 여기서 중지
        }

        // 중복 아이디 체크
        //if (databaseHelper.useridCheck(userid)) {
        if (MariaDBHelper.useridCheck(userid)) {
            Toast.makeText(this, "이미 사용중인 아이디입니다!", Toast.LENGTH_SHORT).show();
            return;  // 여기서 중지
        }

        // 회원 저장
        boolean success =
            //databaseHelper.insertMember(userid, passwd, name, email);
                MariaDBHelper.insertMember(userid, passwd, name, email);
        if (success) {
            Toast.makeText(this, "✨회원 가입 성공!✨", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "😱회원 가입 실패!!😱 다시 시도하세요!!", Toast.LENGTH_SHORT).show();
        }
    }
}








