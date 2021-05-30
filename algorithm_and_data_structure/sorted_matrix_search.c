#include <stdio.h>
#include <stdlib.h>

int read_and_solve(int x, int m, int n) {
    int A[n][m], i, j;
    for (i = 0; i < n; i++) {
	for (j = 0; j < m && scanf("%d",&A[i][j]); j++);
    }
    for (i = 0, j = m-1; i < n && j >= 0; A[i][j] < x ? i++ : j--) {
	if (A[i][j] == x) {
	    return 1;
	}
    }
    return 0;
}

int main(int argc, char *argv[]) {
    int x = atoi(argv[argc-1]), m, n;
    if (scanf("%d%d",&n,&m) && read_and_solve(x,m,n)) {
	printf("The value %d is found.",x);
    } else {
	printf("The valud %d cannot be found.",x);
    }
    return 0;
}
