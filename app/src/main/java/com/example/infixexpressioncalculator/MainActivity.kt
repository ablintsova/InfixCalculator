package com.example.infixexpressioncalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val digitListener = View.OnClickListener { view ->
            val button = view as Button

            if (tvDisplay.text.isEmpty()) {
                tvDisplay.text = button.text
            } else {
                when (tvDisplay.text.last()) {
                    in '1'..'9','+', '-', '*', '/', '(', '.' -> tvDisplay.append(button.text)
                    // для ситуаций, когда пользователь вводит цифры после 0, не поставив десятичную точку
                    '0' -> {
                        if (tvDisplay.text.length == 1) {
                            tvDisplay.append("." + button.text)
                        } else {
                            val symbolBefore0 = tvDisplay.text.lastIndex - 1
                            when (tvDisplay.text[symbolBefore0]) {
                                '+', '-', '*', '/', '(' -> tvDisplay.append("." + button.text)
                                in '0'..'9', '.' -> tvDisplay.append(button.text)
                            }
                        }
                    }
                }
            }
        }

        btn0.setOnClickListener(digitListener)
        btn1.setOnClickListener(digitListener)
        btn2.setOnClickListener(digitListener)
        btn3.setOnClickListener(digitListener)
        btn4.setOnClickListener(digitListener)
        btn5.setOnClickListener(digitListener)
        btn6.setOnClickListener(digitListener)
        btn7.setOnClickListener(digitListener)
        btn8.setOnClickListener(digitListener)
        btn9.setOnClickListener(digitListener)

        val operationListener = View.OnClickListener { view ->
            val operation = view as Button
            if (tvDisplay.text.isNotEmpty()) {
                when (tvDisplay.text.last()) {
                    in '0'..'9', ')' -> tvDisplay.append(operation.text)
                }
            }
        }

        val minusListener = View.OnClickListener { view ->
            val operation = view as Button
            if (tvDisplay.text.isNotEmpty()) {
                when (tvDisplay.text.last()) {
                    in '0'..'9', '(', ')' -> tvDisplay.append(operation.text)
                }
            } else {
                tvDisplay.append(operation.text)
            }
        }

        btnPlus.setOnClickListener(operationListener)
        btnMinus.setOnClickListener(minusListener)
        btnMulti.setOnClickListener(operationListener)
        btnDivide.setOnClickListener(operationListener)

        val pointListener = View.OnClickListener {
            if (tvDisplay.text.isNotEmpty()) {
                when (tvDisplay.text.last()) {
                    in '0'..'9' -> tvDisplay.append(".")
                    '+', '-', '*', '/', '(' -> tvDisplay.append("0.")
                }
            } else {
                tvDisplay.append("0.")
            }
        }

        btnPoint.setOnClickListener(pointListener)

        val openParListener = View.OnClickListener {
            if (tvDisplay.text.isNotEmpty()) {
                when (tvDisplay.text.last()) {
                    '+', '-', '*', '/', '(' -> tvDisplay.append("(")
                }
            } else {
                tvDisplay.append("(")
            }
        }

        btnOpenPar.setOnClickListener(openParListener)

        val closeParListener = View.OnClickListener {
            if (tvDisplay.text.isNotEmpty()) {
                when (tvDisplay.text.last()) {
                    in '0'..'9', ')' -> tvDisplay.append(")")
                }
            }
        }

        btnClosePar.setOnClickListener(closeParListener)

        val clearDisplayListener = View.OnClickListener {
            tvDisplay.text = ""
        }

        btnClear.setOnClickListener(clearDisplayListener)

        val deleteButtonListener = View.OnClickListener {
            if (tvDisplay.text.isNotEmpty()) {
                val str = tvDisplay.text.dropLast(1)
                tvDisplay.text = str
            }
        }

        btnDelete.setOnClickListener(deleteButtonListener)

        val onResultListener = View.OnClickListener {
            val postfix = helper.infixToPostfix(tvDisplay.text.toString() + ")")
            postfix.push(')')
            postfix.elements.reverse()
            tvDisplay.append(" = " + helper.evaluatePostfixExpression(postfix))
        }

        btnResult.setOnClickListener(onResultListener)
    }
}
