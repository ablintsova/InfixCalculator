package com.example.infixexpressioncalculator

class Helper {

    // Проверка приоритета операции
    private fun precedenceOf(operation: Any?): Int {
        when (operation) {
            '*', '/' -> return 2
            '+', '-' -> return 1
            else -> return 0
        }
    }

    // Выполнение указанной операции над двумя операндами
    private fun performOperation(op1: String, op2: String, operation: String): Any? {
        val A = op2.toDouble()
        val B = op1.toDouble()
        var result: Any? = null
        when (operation) {
            "+" -> result = A + B
            "-" -> result = A - B
            "*" -> result = A * B
            "/" -> result = A / B
        }

        return result
    }

    // Перевод выражения пользователя в постфиксную нотацию
    fun infixToPostfix(infix: String): Stack {
        var operand = ""
        var isReadingOperand = false
        val stack = Stack()
        val postfix = Stack()
        stack.push('(')

        // Закончить считывать число в строке и занести его в постфиксный стек
        fun pushOperandToStack() {
            if (isReadingOperand) {
                isReadingOperand = false
                postfix.push(operand.toDouble())
                operand = ""
            }
        }

        // Добавить в постфиксный стек операции, у которых приоритет больше либо равен приоритету текущей операции
        fun pushOperationToStack(operation: Char) {
            while (precedenceOf(stack.peek()) >= precedenceOf(operation)) {
                postfix.push(stack.pop()!!)
            }
            stack.push(operation)
        }

        for ((index, char) in infix.withIndex()) {
            when (char) {
                '(' -> stack.push(char)

                in '0'..'9', '.' -> {
                    isReadingOperand = true
                    operand += char
                }

                '+', '*', '/' -> {
                    pushOperandToStack()
                    pushOperationToStack(char)
                }

                '-' -> {
                    // Проверка на унарный минус
                    if (index == 0 || infix.elementAt(index-1) == '(') {
                        isReadingOperand = true
                        operand += char
                    } else {
                        pushOperandToStack()
                        pushOperationToStack(char)
                    }
                }

                ')' -> {
                    pushOperandToStack()
                    while (stack.peek()!! != '(') {
                        postfix.push(stack.pop()!!)
                    }
                    stack.pop()
                }
            }
        }
        return postfix
    }

    // Вычислить постфиксное выражение
    fun evaluatePostfixExpression(postfix: Stack): Any? {
        val stack = Stack()
        var result: Any? = ""
        while (postfix.peek() != ')') {

            when (postfix.peek()) {
                '+', '-', '*', '/' -> {
                    val A = stack.pop().toString()
                    val B = stack.pop().toString()

                    result = performOperation(A, B, postfix.pop().toString())
                    if (result != null) {
                        stack.push(result)
                    }
                }
                else -> stack.push(postfix.pop()!!)
            }
        }
        return result
    }
}