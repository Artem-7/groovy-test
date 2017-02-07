class Fibonacci{
    static long firstFib = 0;
    static long secondFib = 1;

    static long getN(int level) {
        long fib, fib_prev1, fib_prev2
        if (level <= 1){
            return firstFib
        }
        if (level == 2) {
            return secondFib
        }
        fib_prev2 = firstFib
        fib_prev1 = secondFib
        for (int i = 3; i <= level; i++){
            fib = fib_prev1 + fib_prev2
            fib_prev2 = fib_prev1
            fib_prev1 = fib
        }
        return fib
    }
}

assert Fibonacci.getN(-20) == 0
assert Fibonacci.getN(0) == 0
assert Fibonacci.getN(1) == 0
assert Fibonacci.getN(2) == 1
assert Fibonacci.getN(3) == 1
assert Fibonacci.getN(4) == 2
assert Fibonacci.getN(5) == 3
assert Fibonacci.getN(6) == 5
assert Fibonacci.getN(7) == 8
assert Fibonacci.getN(8) == 13

println Fibonacci.getN(50)