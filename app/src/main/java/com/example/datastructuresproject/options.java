package com.example.datastructuresproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SetTextI18n")
public class options extends AppCompatActivity {
    private Button back, inOrder, postOrder, preOrder;
    private TextView result;
    private char negated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        back = findViewById(R.id.btnBack);
        inOrder = findViewById(R.id.btninorder);
        postOrder = findViewById(R.id.btnpostorder);
        preOrder = findViewById(R.id.btnpreorder);

        result = findViewById(R.id.txtResult);
        System.out.println("RESULT IS: " + result);

        Intent i = getIntent();
        String infix = i.getStringExtra("expression");
        result.setText("Original: " + infix);

        back.setOnClickListener(this::onClickBack);
        inOrder.setOnClickListener(this::onClickinOrder);
        postOrder.setOnClickListener(this::onClickpostOrder);
        preOrder.setOnClickListener(this::onClickpreOrder);
        result.setOnClickListener(this::onClickResult);
    }

    //------------------------------ BST LOGIC ------------------------------//

    class Node {
        public char data;
        public Node leftChild;
        public Node rightChild;

        public Node(char x) {
            data = x;
        }
    }

    class Stack1 {
        private Node[] a;
        private int top, m;

        public Stack1(int max)
        {
            m = max;
            a = new Node[m];
            top = -1;
        }

        public void push(Node key) {
            a[++top] = key;
        }

        public Node pop() {
            return (a[top--]);
        }
    }

    class Stack2 {
        private char[] a;
        private int top, m;

        public Stack2(int max) {
            m = max;
            a = new char[m];
            top = -1;
        }

        public void push(char key) {
            a[++top] = key;
        }

        public char pop() {
            return (a[top--]);
        }

        public boolean isEmpty() {
            return (top == -1);
        }
    }

    class Conversion {
        private Stack2 s;
        private String input;
        private String output = "";

        public Conversion(String str) {
            input = str;
            s = new Stack2(str.length());
        }

        public String inToPost() {
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                switch (ch) {
                    case '+':
                    case '-':
                        gotOperator(ch, 1);
                        break;
                    case '*':
                    case '/':
                    case '%':
                        gotOperator(ch, 2);
                        break;
                    case '^':
                        gotOperator(ch,3);
                        break;
                    case '(':
                        s.push(ch);
                        break;
                    case ')':
                        gotParenthesis();
                        break;
                    default:
                        output = output + ch;
                }
            }
            while (!s.isEmpty())
                output = output + s.pop();
            return output;
        }

        private void gotOperator(char opThis, int priority1) {
            while (!s.isEmpty())
            {
                char top = s.pop();
                if (top == '(') {
                    s.push(top);
                    break;
                }

                else {
                    int priority2;
                    if (top == '+' || top == '-')
                        priority2 = 1;
                    else if(top == '*' || top == '/' || top =='%')
                        priority2 = 2;
                    else
                        priority2 = 3;

                    if (priority2 < priority1) {
                        s.push(top);
                        break;
                    }
                    else
                        output += top;
                }
            }
            s.push(opThis);
        }

        private void gotParenthesis() {
            while (!s.isEmpty()) {
                char ch = s.pop();
                if (ch == '(')
                    break;
                else
                    output += ch;
            }
        }
    }

    class Tree {
        private Node root;


        public Tree() {
            root = null;
        }

        public void insert(String s) {
            Conversion c = new Conversion(s);
            s = c.inToPost();
            Stack1 stk = new Stack1(s.length());
            s = s + "?";
            int i = 0;

            char symbol = s.charAt(i);
            Node nodeNew;
            while (symbol != '?') {
                if (symbol >= '0' && symbol <= '9' || symbol >= 'A' && symbol <= 'Z' ) {
                    nodeNew = new Node(symbol);
                    stk.push(nodeNew);
                }

                else if (symbol == '+' || symbol == '-' || symbol == '/' || symbol == '*' || symbol == '^' || symbol == '%') {
                    Node ptr1 = stk.pop();
                    Node ptr2 = stk.pop();
                    nodeNew = new Node(symbol);
                    nodeNew.leftChild = ptr2;
                    nodeNew.rightChild = ptr1;
                    stk.push(nodeNew);
                }
                if (symbol == '~') {
                    negated = s.charAt(i+1);

                }
                symbol = s.charAt(++i);
            }
            root = stk.pop();
        }
    }

    //------------------------------ POST ORDER ------------------------------//
    String postAnswer;
    private String postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            postAnswer += localRoot.data;
        }
        return postAnswer;
    }

    public void onClickpostOrder (View v) {
        Intent i = getIntent();
        String infix = i.getStringExtra("expression");
        Tree tree = new Tree();
        tree.insert(infix);
        String postFix = postOrder(tree.root);
        postFix = postFix.replaceAll(String.valueOf(negated), "~" + negated);
        String text = "";
        text = "Original: " + infix + "\n" + "PostOrder: " + postFix;
        text = text.replaceAll("null","");
        result.setText(text);
        postAnswer = "";
        postOrder.setClickable(false);
        preOrder.setClickable(true);
        inOrder.setClickable(true);
    }


    //------------------------------ IN ORDER ------------------------------//
    String inAnswer;
    private String inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            inAnswer+=localRoot.data;
            inOrder(localRoot.rightChild);
        }
        return inAnswer;
    }

    public void onClickinOrder (View v) {
        Intent i = getIntent();
        String infix = i.getStringExtra("expression");
        Tree tree = new Tree();
        tree.insert(infix);
        String infixUpdated = inOrder(tree.root);
        infixUpdated = infixUpdated.replaceAll(String.valueOf(negated), "~" + negated);
        String text = "Original: " + infix + "\n" + "InOrder: " + infixUpdated;
        text = text.replaceAll("null","");
        result.setText(text);
        inAnswer = "";
        postOrder.setClickable(true);
        preOrder.setClickable(true);
        inOrder.setClickable(false);
    }

    //------------------------------ PRE ORDER ------------------------------//
    String preAnswer;
    private String preOrder(Node localRoot) {
        if (localRoot != null) {
            preAnswer+= localRoot.data;
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
        return preAnswer;
    }

    public void onClickpreOrder (View v) {
        Intent i = getIntent();
        String infix = i.getStringExtra("expression");
        Tree tree = new Tree();
        tree.insert(infix);
        String preFix = preOrder(tree.root);
        preFix = preFix.replaceAll(String.valueOf(negated), "~" + negated);
        String text = "Original: " + infix + "\n" + "PreOrder: " + preFix;
        text = text.replaceAll("null","");
        result.setText(text);
        preAnswer = "";
        postOrder.setClickable(true);
        preOrder.setClickable(false);
        inOrder.setClickable(true);
    }


    //------------------------------ COPY TO CLIPBOARD ------------------------------//
    public void onClickResult (View v) {
        String copy = "", message = result.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(copy, message);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Copied to Clipboard.", Toast.LENGTH_SHORT).show();
    }


    //------------------------------ BACK BUTTON ------------------------------//
    public void onClickBack (View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

}