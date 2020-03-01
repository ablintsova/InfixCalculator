package com.example.infixexpressioncalculator

class Helper {
    private fun precedenceOf(symbol: Char): Int {
        when (symbol) {
            '*', '/' -> return 2
            '+', '-' -> return 1
            else -> return 0
        }
    }

    private fun precedenceOf(symbol: Any?): Int {
        when (symbol) {
            '*', '/' -> return 2
            '+', '-' -> return 1
            else -> return 0
        }
    }

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

    fun infixToPostfix(infix: String): Stack {
        var operand = ""
        var operandFlag = false
        val stack = Stack()
        val postfix = Stack()
        stack.push('(')
        for (char in infix) {
            when (char) {
                '(' -> stack.push(char)

                in '0'..'9' -> {
                    operandFlag = true
                    operand += char
                }

                '.' -> {
                    operand += char
                }

                '+', '-', '*', '/' -> {
                    if (operandFlag) {
                        operandFlag = false
                        postfix.push(operand.toDouble())
                        operand = ""
                    }
                    while (precedenceOf(stack.peek()) >= precedenceOf(char)) {
                        postfix.push(stack.pop()!!)
                    }
                    stack.push(char)
                }

                ')' -> {
                    if (operandFlag) {
                        operandFlag = false
                        postfix.push(operand.toDouble())
                        operand = ""
                    }
                    while (stack.peek()!! != '(') {
                        postfix.push(stack.pop()!!)
                    }
                    stack.pop()
                }
            }
        }
        return postfix
    }

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