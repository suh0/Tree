package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int balance = 2000; // 초기 잔액

    private TextView balanceTextView; //현재 잔액을 나타냄
    private Button button1; //나무1 구매 버튼
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceTextView = findViewById(R.id.balanceTextView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        //리소스 아이디를 통해서 레이아웃에 있는 뷰 객체들 중 일치하는 뷰를 가져오는 메소드

        updateBalanceText();//잔액을 화면에 표시하는 데 사용되는 함수



        // 아이템 구매 버튼 클릭 시 실행되는 동작을 정의하는 메소드
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurchaseConfirmationDialog();
                // 사용자에게 아이템 구매 확인을 요청하는 메소드
            }
            //아이템 구매 확인을 요청하기 위해 표시되는 대화상자(Dialog)를 표시하는 메서드
            private void showPurchaseConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //대화상자(Dialog)를 생성하고 구성하는 데 사용되는 빌더(Builder) 클래스
                builder.setTitle("구매 확인"); // 대화상자(Dialog)의 제목(Title)을 설정하는 코드
                builder.setMessage("정말로 이 아이템을 구매하시겠습니까?");// 대화상자(Dialog)의 메시지(Message)를 설정하는 코드
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int itemPrice1 =100;
                        balance -= itemPrice1;
                        updateBalanceText();
                        // 여기에서 실제로 아이템을 구매하는 로직을 추가할 수 있습니다.
                        // 구매가 성공하면 이곳에서 처리합니다.
                    }
                });
               // 대화상자(Dialog)에 "아니요" 또는 부정적인 응답을 나타내는 버튼을 추가하는 코드
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "구매 실패", Toast.LENGTH_SHORT).show();
                        // 구매를 취소하거나 다른 작업을 수행할 수 있습니다.
                    }
                });
                builder.show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurchaseConfirmationDialog();
            }
            private void showPurchaseConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //대화상자(Dialog)를 생성하고 구성하는 데 사용되는 빌더(Builder) 클래스
                builder.setTitle("구매 확인");
                builder.setMessage("정말로 이 아이템을 구매하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int itemPrice2 =200;
                        balance -= itemPrice2;
                        updateBalanceText();
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurchaseConfirmationDialog();
            }
            private void showPurchaseConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("구매 확인");
                builder.setMessage("정말로 이 아이템을 구매하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int itemPrice3 =300;
                        balance -= itemPrice3;
                        updateBalanceText();
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

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurchaseConfirmationDialog();
            }
            private void showPurchaseConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("구매 확인");
                builder.setMessage("정말로 이 아이템을 구매하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int itemPrice4 =400;
                        balance -= itemPrice4;
                        updateBalanceText();
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

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurchaseConfirmationDialog();
            }
            private void showPurchaseConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("구매 확인");
                builder.setMessage("정말로 이 아이템을 구매하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int itemPrice5 =500;
                        balance -= itemPrice5;
                        updateBalanceText();
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

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurchaseConfirmationDialog();
            }
            private void showPurchaseConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("구매 확인");
                builder.setMessage("정말로 이 아이템을 구매하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int itemPrice6 = 600;
                        balance -= itemPrice6;
                        updateBalanceText();
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
