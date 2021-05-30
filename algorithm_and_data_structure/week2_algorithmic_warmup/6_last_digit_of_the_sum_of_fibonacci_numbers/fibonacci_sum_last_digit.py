# Uses python3

def fibonacci_sum_naive(n):
    if n <= 1:
        return n

    previous = 0
    current  = 1
    F      = 1
    if n%60 == 0:
        return 0
    if n%60 == 1:
        return 1

    for _ in range(n%60-1):
        previous, current = current%10, (previous + current)%10
        F += current%10

    return F % 10

if __name__ == '__main__':
    n = int(input())
    print(fibonacci_sum_naive(n))
