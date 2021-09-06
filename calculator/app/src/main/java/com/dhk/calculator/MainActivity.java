package com.dhk.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;
import java.util.Vector;
/**
 * @Author: Đinh Huy Khánh
 * @created: 4.09.2021 14:30
 **/
public class MainActivity extends AppCompatActivity {

    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8,
            button9, buttonDiv, buttonMul, buttonSub, buttonAdd, buttonEqual, buttonC, buttonDots;
    private EditText editTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = (Button) findViewById(R.id.btn0);
        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        button3 = (Button) findViewById(R.id.btn3);
        button4 = (Button) findViewById(R.id.btn4);
        button5 = (Button) findViewById(R.id.btn5);
        button6 = (Button) findViewById(R.id.btn6);
        button7 = (Button) findViewById(R.id.btn7);
        button8 = (Button) findViewById(R.id.btn8);
        button9 = (Button) findViewById(R.id.btn9);
        buttonAdd = (Button) findViewById(R.id.btnAdd);
        buttonSub = (Button) findViewById(R.id.btnSub);
        buttonMul = (Button) findViewById(R.id.btnMul);
        buttonDiv = (Button) findViewById(R.id.btnDiv);
        buttonC = (Button) findViewById(R.id.btnC);
        buttonDots = (Button) findViewById(R.id.btnDots);
        buttonEqual = (Button) findViewById(R.id.btnEqual);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);


        setOnClickButton(button0, "0");
        setOnClickButton(button1, "1");
        setOnClickButton(button2, "2");
        setOnClickButton(button3, "3");
        setOnClickButton(button4, "4");
        setOnClickButton(button5, "5");
        setOnClickButton(button6, "6");
        setOnClickButton(button7, "7");
        setOnClickButton(button8, "8");
        setOnClickButton(button9, "9");
        setOnClickButton(buttonAdd, "+");
        setOnClickButtonDivAndMul(buttonMul, "*");
        setOnClickButton(buttonSub, "-");
        setOnClickButtonDivAndMul(buttonDiv, "/");
        setOnClickButton(buttonDots, ".");
        setOnClickButtonC();
        setOnClickButtonEqual();

    }

    private void setOnClickButtonC() {
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNumber.setText("");
            }
        });
    }

    private void setOnClickButtonDivAndMul(Button btn, String str) {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNumber.getText().length() != 0) {
                    String toStr = (String) editTextNumber.getText().toString();
                    char chars[] = toStr.toCharArray();
                    if (chars[toStr.length() - 1] - '0' < 0) {
                        editTextNumber.setText(editTextNumber.getText() + "");
                    } else editTextNumber.setText(editTextNumber.getText() + str);
                } else {
                    editTextNumber.setText("");
                }
            }
        });
    }

    private void setOnClickButton(Button btn, String str) {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toStr = (String) editTextNumber.getText().toString();
                char chars[] = toStr.toCharArray();
                if (toStr.length() > 1 && (str.equals("+") || str.equals("-")) && (chars[toStr.length() - 1] - '0' < 0 && chars[toStr.length() - 2] - '0' < 0)) {
                    editTextNumber.setText(editTextNumber.getText() + "");
                } else editTextNumber.setText(editTextNumber.getText() + str);
            }
        });

    }

    private void setOnClickButtonEqual() {
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = (String) editTextNumber.getText().toString();

                if (str.length() == 0) {
                    editTextNumber.setText("0");
                } else {

                    // ép kiểu String về char*;
                    char[] chars = str.toCharArray();

                    // kiem tra 2 chữ số đầu có phải là 2 phép tính hay không
                    // vd: ++, -- ,**,//,*-...
                    if (  (chars[str.length()-1]-'0'<0 && chars[str.length()-1] !='.' ) ) {
                        editTextNumber.setText("ERROR!!!");
                        return;
                    }
                    if(( chars[0] - '0' < 0 && chars[1] - '0' < 0 && chars[2] != '.') ){
                        str = "+0" + str+"+";
                    }else{
                        str = "+"+str+"+";
                    }
                    chars= str.toCharArray();


                    // ans :kết quả của phép toán, fValue là giá trị của số cần tính fValueOne : là giá trị của phép toán nhân hoặc chia
                    float ans = 0, fValue = 0, fValueOne = 0;
                    int idx = 2, idx1 = 1,cur=0; // idx: chỉ mục của vị trí chars, idx1: vị trí của phép tính +1  (+,-,*,/);

                    while (idx < str.length()) {
                        // thuc hiep phep tinh
                        if (chars[idx] - '0' < 0 && chars[idx] != '.') {
                            if (chars[idx - 1] - '0' < 0) {
                                ++idx;
                                continue;
                            } else {
                                String sNumber = str.substring(idx1, idx);
                                fValue = Float.parseFloat(sNumber);
                                // nếu vị trí hiện tại là phép + hoặc phép trừ
                                if (chars[idx] == '+' || chars[idx] == '-') {
                                    // kiểm tra vị trí idx1-1 là phép toán nào ?
                                    if (chars[idx1 - 1] == '+') { // nếu vị trí idx1 -1 là phép + thì  bài toán ans += value +
                                        ans += fValue;
                                    } else if (chars[idx1 - 1] == '-') { // nếu là phép trừ trở thành bài toán ans -= value;
                                        ans -= fValue;
                                    } else if (chars[idx1 - 1] == '*') {
                                        // nếu là phép * bài toán trở thành  ans +=fValueOne * fValue
                                        if(chars[cur] == '+'){
                                            ans += fValueOne * fValue;
                                        } else  {
                                            ans -= fValue*fValueOne;
                                        }
                                    } else { // còn lại là phép chia
                                        if (fValue == 0.0) { // nếu chia cho 0 bài toán vô lý
                                            editTextNumber.setText("ERROR!");
                                            return;
                                        } else {
                                            if(chars[cur]=='+'){
                                                ans += fValueOne / fValue; // tương tự...
                                            }else{
                                                ans -= fValueOne / fValue; // tương tự...
                                            }
                                        }
                                    }
                                } else { // ngược lại nếu idx là phép nhân hoặc phép chia;
                                    // kiểm tra tại idx1 -1 là phép nhân chia hay cộng trừ
                                    if (chars[idx1 - 1] == '+' || chars[idx1 - 1] == '-') {
                                        // nếu là cộng trừ thì fValueOne = fValue vào số bị chia;
                                        fValueOne = fValue;
                                        cur=idx1-1;
                                    } else {// ngược lại nếu idx1-1 là phép nhân hoặc phép chia
                                        if (chars[idx1 - 1] == '*') {
                                            // bài toán a*b*c hoặc a*b/c : ta đã có fValueOne là a : fvalue La b bây giờ ta tính a*b và gán vào fValueOne;
                                            fValueOne *= fValue;
                                        } else {
                                            // bài toán  a/b*c hoặc a/b/c : ta gán fValueOne = fValueOne/fValue ( fValueOne : a , fValue : b)
                                            fValueOne /= fValue;
                                        }
                                    }
                                }
                            }
                            idx1 = idx + 1;
                        }
                        ++idx;
                    }
                    editTextNumber.setText(ans + "");
                }
            }
        });
    }

}
