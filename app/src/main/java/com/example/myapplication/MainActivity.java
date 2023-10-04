package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int balance = 220; // 초기 잔액

    private TextView balanceTextView; //현재 잔액을 나타냄
    private Button button; //나무1 구매 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceTextView = findViewById(R.id.balanceTextView);
        button = findViewById(R.id.button);
        //리소스 아이디를 통해서 레이아웃에 있는 뷰 객체들 중 일치하는 뷰를 가져오는 메소드

        updateBalanceText();//잔액을 화면에 표시하는 데 사용되는 함수

        // 아이템 구매 버튼 클릭 시 실행되는 동작을 정의하는 메소드
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurchaseConfirmationDialog();
                // 사용자에게 아이템 구매 확인을 요청하는 메소드
                int itemPrice = 100; // 아이템 가격(나무1)
                if (balance >= itemPrice) {
                    balance -= itemPrice;
                    updateBalanceText();//잔액 업데이트
                    // 아이템을 구매하는 추가 로직을 여기에 작성하세요.
                } else {
                    // 잔액이 부족할 경우 메세지 처리
                    Toast.makeText(MainActivity.this, "잔액이 부족합니다.", Toast.LENGTH_SHORT).show();

                }
            }
            //아이템 구매 확인을 요청하기 위해 표시되는 대화상자(Dialog)를 표시하는 메서드
            private void showPurchaseConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //대화상자(Dialog)를 생성하고 구성하는 데 사용되는 빌더(Builder) 클래스
                builder.setTitle("구매 확인"); // 대화상자(Dialog)의 제목(Title)을 설정하는 코드
                builder.setMessage("정말로 이 아이템을 구매하시겠습니까?");// 대화상자(Dialog)의 메시지(Message)를 설정하는 코드
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 여기에서 실제로 아이템을 구매하는 로직을 추가할 수 있습니다.
                        // 구매가 성공하면 이곳에서 처리합니다.
                    }
                });
               // 대화상자(Dialog)에 "아니요" 또는 부정적인 응답을 나타내는 버튼을 추가하는 코드
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 구매를 취소하거나 다른 작업을 수행할 수 있습니다.
                    }
                });
                builder.show();
            }
        });
    }

    private void updateBalanceText() {
        balanceTextView.setText("현재 잔액: $" + balance);
    }
}
