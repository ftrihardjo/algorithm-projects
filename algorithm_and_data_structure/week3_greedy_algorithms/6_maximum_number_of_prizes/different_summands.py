# Uses python3
from math import floor, sqrt

def optimal_summands(n):
    summands = []
    m = floor((-1+sqrt(1+8*n))/2)
    for i in range(m):
        summands.append(i+1)
    summands[m-1] += n-m*(m+1)//2
    return summands

if __name__ == '__main__':
    n = int(input())
    summands = optimal_summands(n)
    print(len(summands))
    for x in summands:
        print(x, end=' ')
