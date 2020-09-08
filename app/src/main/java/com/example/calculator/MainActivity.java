package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private float num_float;
    private float num_float1;
    private Stack stack = new Stack();
    private ArrayList list = new ArrayList();
    private float result;
    private HashMap<String, Integer> order = new HashMap();

    // sign (현재 부호 체크) -> + : true, - : false
    // check (소수점(.)) 체크 -> 소수점 눌림 : true , 소수점 안 눌림 : false
    // num_check ( 0~9 숫자 버튼 눌렸는지 체크 ) -> 눌림 : true, 안 눌림 : false
    private boolean sign = true, check = false, num_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.result);

        // 연산 우선순위
        order.put("*", 2);
        order.put("/", 2);
        order.put("+", 1);
        order.put("-", 1);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            //0~9버튼 눌렀을 경우
            case R.id.Button0:
                if( num_check || check )
                    text.setText(text.getText().toString() + 0);
                break;
            case R.id.Button1:
                if(num_check || check)
                    text.setText(text.getText().toString() + 1);
                else
                    text.setText("1");
                num_check = true;
                break;
            case R.id.Button2:
                if(num_check || check)
                    text.setText(text.getText().toString() + 2);
                else
                    text.setText("2");
                num_check = true;

                break;
            case R.id.Button3:
                if(num_check || check)
                    text.setText(text.getText().toString() + 3);
                else
                    text.setText("3");
                num_check = true;

                break;
            case R.id.Button4:
                if( num_check || check)
                    text.setText(text.getText().toString() + 4);
                else
                    text.setText("4");
                num_check = true;
                break;
            case R.id.Button5:
                if(num_check || check)
                    text.setText(text.getText().toString() + 5);
                else
                    text.setText("5");
                num_check = true;
                break;
            case R.id.Button6:
                if( num_check || check)
                    text.setText(text.getText().toString() + 6);
                else
                    text.setText("6");
                num_check = true;
                break;
            case R.id.Button7:
                if(num_check || check)
                    text.setText(text.getText().toString() + 7);
                else
                    text.setText("7");
                num_check = true;
                break;
            case R.id.Button8:
                if(num_check || check)
                    text.setText(text.getText().toString() + 8);
                else
                    text.setText("8");
                num_check = true;
                break;
            case R.id.Button9:
                if(num_check || check)
                    text.setText(text.getText().toString() + 9);
                else
                    text.setText("9");
                num_check = true;
                break;
            //.버튼 -> check로 처음 눌렀는지 확인
            case R.id.dotButton:
                if (!check) {
                    check = true;
                    text.setText(text.getText().toString() + ".");
                }
                break;
            // delete버튼 -> 지울 숫자가 있는지 확인 후 제거
            case R.id.delButton:
                if (text.length() > 0 && !text.getText().toString().equals("0")) {
                    if(text.length() - 1 == 0)
                        text.setText("0");
                    else
                        text.setText(text.getText().toString().substring(0, text.length() - 1));
                }
                else
                    text.setText("0");
                break;
            // 각각의 연산 버튼
            // 각 연산의 종류에 따라  location 저장, text에 입력된 숫자는 num_float 변수에 저장
            case R.id.addButton:
                if(num_check) {
                    list.add(Float.valueOf(text.getText().toString().trim()));
                    text.setText("0");
                    if(stack.empty())
                        stack.push("+");
                    else{
                        if( order.get(stack.peek()) >= order.get("+") ){
                            list.add(stack.pop());
                            stack.push("+");
                        }
                        else
                            stack.push("+");
                    }

                    check = false;
                    num_check = false;
                }
                break;
            case R.id.subtrackButton:
                if(num_check) {
                    list.add(Float.valueOf(text.getText().toString().trim()));
                    text.setText("0");
                    if(stack.empty())
                        stack.push("-");
                    else{
                        if( order.get(stack.peek()) >= order.get("-") ){
                            list.add(stack.pop());
                            stack.push("-");
                        }
                        else
                            stack.push("-");
                    }
                    check = false;
                    num_check = false;
                }
                break;
            case R.id.multiplyButton:
                if(num_check) {
                    list.add(Float.valueOf(text.getText().toString().trim()));
                    text.setText("0");
                    if(stack.empty())
                        stack.push("*");
                    else{
                        if( order.get(stack.peek()) >= order.get("*") ){
                            list.add(stack.pop());
                            stack.push("*");
                        }
                        else
                            stack.push("*");
                    }
                    check = false;
                    num_check = false;
                }
                break;
            case R.id.divideButton:
                if(num_check) {
                    list.add(Float.valueOf(text.getText().toString().trim()));
                    text.setText("0");
                    if(stack.empty())
                        stack.push("/");
                    else{
                        if( order.get(stack.peek()) >= order.get("/") ){
                            list.add(stack.pop());
                            stack.push("/");
                        }
                        else
                            stack.push("/");
                    }
                    check = false;
                    num_check = false;
                }
                break;
            case R.id.percentButton:
                num_float = Float.valueOf(text.getText().toString().trim());
                text.setText(String.valueOf(num_float * 0.01));
                check = false;
                break;
            //부호 지정 버튼
            // 텍스트에 입력된 숫자 없거나 +또는 - 일때만 동작
            case R.id.signButton:
                if (sign && text.getText().toString().equals("0")) {
                    text.setText("-0");
                    sign = false;
                } else if (!sign && (text.getText().toString().equals("0") || text.getText().toString().equals("-0"))) {
                    text.setText("0");
                    sign = true;
                }
                break;
            //AC버튼, 초기화 시킴
            case R.id.clearButton:
                num_float = 0;
                num_float1 = 0;
                list.clear();
                stack.clear();
                result = 0;
                check = false;
                num_check = false;
                text.setText("0");
                break;
                // equal버튼,
                // 연산의 결과가 정수일 경우,
                // round() 함수로 실수의 소수점 첫번째 자리에서 반올림하여 정수로 반환
            case R.id.resultButton:
                list.add(Float.valueOf(text.getText().toString().trim()));
                while(!stack.empty()){
                    list.add(stack.pop());
                }
                for(Object c : list){
                    if(!order.containsKey(c)){
                        stack.push((Float) c);
                    }
                    else{
                        if(c.equals("+")) {
                            num_float = (Float) stack.pop();
                            num_float1 = (Float) stack.pop();
                            stack.push(num_float + num_float1);
                        }
                        else if(c.equals("-")) {
                            num_float = (Float) stack.pop();
                            num_float1 = (Float) stack.pop();
                            stack.push(num_float1 - num_float);
                        }
                        else if(c.equals("*")) {
                            num_float = (Float) stack.pop();
                            num_float1 = (Float) stack.pop();
                            stack.push(num_float * num_float1);
                        }
                        else if(c.equals("/")) {
                            num_float = (Float) stack.pop();
                            num_float1 = (Float) stack.pop();
                            stack.push(num_float1 / num_float);
                        }
                    }
                }
                result =(Float) stack.pop();
                if (result % 1 == 0) // 정수일 경우
                     text.setText(String.valueOf(Math.round(result)));
                else
                    text.setText(String.valueOf(result));

        }
    }
}