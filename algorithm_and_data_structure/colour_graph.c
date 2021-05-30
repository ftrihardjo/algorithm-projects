#include <stdio.h>

int wrong_colour(int *neighbours, int *colours, int colour, int n) {
    int i;
    for (i = 0; i < n; i++) {
	if (neighbours[i] && colours[i] == colour) {
	    return 1;
	}
    }
    return 0;
}

void colour_graph(int n, int d) {
    int G[n][n], i, j, a, b, colour[n];
    for (i = 0 ; i < n; i++) {
	for (j = 0; j < n; j++) {
	    G[i][j] = 0;
	}
    }
    for (i = 0, scanf("%d",&j); i < j && scanf("%d%d",&a,&b); i++) {
	G[a][b] = 1;
	G[b][a] = 1;
    }
    for (i = 0; i < n; i++) {
	colour[i] = -1;
    }
    for (i = 0; i < n; i++) {
	for (j = 0; j < d && wrong_colour(G[i],colour,j,n); j++);
	colour[i] = j;
    }
    for (i = 0; i < n; i++) {
	printf("%d %d\n",i,colour[i]);
    }
}

int main() {
    int d, n;
    scanf("%d%d",&n,&d);
    colour_graph(n,d);
    return 0;
}
