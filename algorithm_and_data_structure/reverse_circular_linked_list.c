#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

typedef struct Node {
    struct Node *next;
    int n;
} Node;

int main(int argc, char *argv[]) {
    int i;
    Node *node, *first, *last;
    first = (Node*)malloc(sizeof(Node));
    assert(first);
    last = first;
    last->n = 1;
    last->next = last;
    for (i = 2; i <= atoi(argv[argc-1]); i++) {
	node = (Node*)malloc(sizeof(Node));
	assert(node);
	node->n = i;
	last->next = node;
	last = last->next;
	last->next = first;
    }
    for (i = 0, node = last; i < atoi(argv[argc-1]); i++) {
	printf("%d ",node->n);
	node = node->next;
    }
    for (i = 0, first = last, last = last->next; i < atoi(argv[argc-1]); i++) {
	node = last;
	last = last->next;
	node->next = first;
	first = node;
    }
    for (i = 0, last = first, node = last, putchar('\n'); i < atoi(argv[argc-1]); i++) {
	printf("%d ",node->n);
	node = node->next;
    }
    for (i = 0, node = last; i < atoi(argv[argc-1]); i++) {
	last = last->next;
	free(node);
    }
    return 0;
}
