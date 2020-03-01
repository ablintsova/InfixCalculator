package com.example.infixexpressioncalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val helper = Helper()

    private val digitListener = View.OnClickListener { view ->
        val button = view as Button

        if (tvDisplay.text.isEmpty()) {
            tvDisplay.text = button.text
        } else {
            when (tvDisplay.text.last()) {
                in '1'..'9', '+', '-', '*', '/', '(', '.' -> tvDisplay.append(button.text)
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

    private val operationListener = View.OnClickListener { view ->
        val operation = view as Button
        if (tvDisplay.text.isNotEmpty()) {
            when (tvDisplay.text.last()) {
                in '0'..'9', ')' -> tvDisplay.append(operation.text)
            }
        }
    }

    private val minusListener = View.OnClickListener { view ->
        val operation = view as Button
        if (tvDisplay.text.isNotEmpty()) {
            when (tvDisplay.text.last()) {
                in '0'..'9', '(', ')' -> tvDisplay.append(operation.text)
            }
        } else {
            tvDisplay.append(operation.text)
        }
    }

    private val pointListener = View.OnClickListener {
        if (tvDisplay.text.isNotEmpty()) {
            when (tvDisplay.text.last()) {
                in '0'..'9' -> tvDisplay.append(".")
                '+', '-', '*', '/', '(' -> tvDisplay.append("0.")
            }
        } else {
            tvDisplay.append("0.")
        }
    }

    private val closeParListener = View.OnClickListener {
        if (tvDisplay.text.isNotEmpty()) {
            when (tvDisplay.text.last()) {
                in '0'..'9', ')' -> tvDisplay.append(")")
            }
        }
    }

    private val openParListener = View.OnClickListener {
        if (tvDisplay.text.isNotEmpty()) {
            when (tvDisplay.text.last()) {
                '+', '-', '*', '/', '(' -> tvDisplay.append("(")
            }
        } else {
            tvDisplay.append("(")
        }
    }

    private val clearDisplayListener = View.OnClickListener {
        tvDisplay.text = ""
    }

    private val deleteButtonListener = View.OnClickListener {
        if (tvDisplay.text.isNotEmpty()) {
            val str = tvDisplay.text.dropLast(1)
            tvDisplay.text = str
        }
    }

    private val onResultListener = View.OnClickListener {
        try {
            val postfix = helper.infixToPostfix(tvDisplay.text.toString() + ")")
            postfix.push(')')
            postfix.elements.reverse()
            tvDisplay.append(" = " + helper.evaluatePostfixExpression(postfix))
        } catch (e: Exception) {
            Toast.makeText(this, "Возникла ошибка, проверьте ваше выражение", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        btnPlus.setOnClickListener(operationListener)
        btnMinus.setOnClickListener(minusListener)
        btnMulti.setOnClickListener(operationListener)
        btnDivide.setOnClickListener(operationListener)

        btnPoint.setOnClickListener(pointListener)

        btnOpenPar.setOnClickListener(openParListener)

        btnClosePar.setOnClickListener(closeParListener)

        btnClear.setOnClickListener(clearDisplayListener)

        btnDelete.setOnClickListener(deleteButtonListener)

        btnResult.setOnClickListener(onResultListener)
    }
}
