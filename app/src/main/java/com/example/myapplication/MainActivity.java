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

    private TextView balanceTextView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceTextView = findViewById(R.id.balanceTextView);
        button = findViewById(R.id.button);

        updateBalanceText();

        // 아이템 구매 버튼 클릭 이벤트 처리
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurchaseConfirmationDialog();
                // 예를 들어, 아이템 가격을 차감하고 잔액을 업데이트합니다.
                int itemPrice = 100; // 아이템 가격 (예시)
                if (balance >= itemPrice) {
                    balance -= itemPrice;
                    updateBalanceText();//잔액 업데이트
                    // 아이템을 구매하는 추가 로직을 여기에 작성하세요.
                } else {
                    // 잔액이 부족할 경우 처리 (예: 메시지 출력)
                    Toast.makeText(MainActivity.this, "잔액이 부족합니다.", Toast.LENGTH_SHORT).show();

                }
            }

            private void showPurchaseConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("구매 확인");
                builder.setMessage("정말로 이 아이템을 구매하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 여기에서 실제로 아이템을 구매하는 로직을 추가할 수 있습니다.
                        // 구매가 성공하면 이곳에서 처리합니다.
                    }
                });
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
