# Uses python3
def calc_fib(n):
    if n == 0:
        return 0
    elif n == 1:
        return 1
    a, b = 0, 1
    for _ in range(n-1):
        a, b = b, a+b
    return b

n = int(input())
print(calc_fib(n))
