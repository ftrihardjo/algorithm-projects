# Uses python3

def fibonacci_sum_squares_naive(n):
    a,b=get_fibonacci_huge_naive(n-1,10),get_fibonacci_huge_naive(n,10)

    return b*(a+b)%10

def get_fibonacci_huge_naive(n, m):
    if n <= 1:
        return n

    previous = 0
    current  = 1
    F = [0, 1]

    while True:
        previous, current = current, (previous + current)%m
        F.append(current)
        if len(F)%2 == 0 and F[:len(F)//2] == F[len(F)//2:]:
            previous = 0
            current  = 1
            if n%(len(F)//2) == 0:
                return 0
            if n%(len(F)//2) == 1:
                return 1
            for _ in range(n%(len(F)//2)-1):
                previous, current = current%m, (previous + current)%m
            return current % m

if __name__ == '__main__':
    n = int(input())
    print(fibonacci_sum_squares_naive(n))
