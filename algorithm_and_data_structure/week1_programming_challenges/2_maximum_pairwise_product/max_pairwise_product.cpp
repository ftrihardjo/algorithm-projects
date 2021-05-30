#include <bits/stdc++.h>
using namespace std;
long long MaxPairwiseProduct(int *numbers, int n) {
    sort(numbers,numbers+n,greater<int>());

    return 1ll*numbers[0]*numbers[1];
}

int main() {
    int n;
    cin >> n;
    int numbers[n];
    for (int i = 0; i < n; ++i) {
        cin >> numbers[i];
    }

    cout << MaxPairwiseProduct(numbers,n) << "\n";
    return 0;
}
