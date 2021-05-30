#include <bits/stdc++.h>

using std::cin;
using std::cout;
using std::vector;
using std::max;

int compute_min_refills(int dist, int tank, vector<int> & stops) {
    int answer = 0;
    for (int i = 0, d = tank; d < dist; answer++, d += tank) {
        bool stay = true;
        for (int D = d; i < stops.size() && D >= stops[i]; d = stops[i++], stay = false);
        if (stay) {
            return -1;
        }
    }
    return answer;
}


int main() {
    int d = 0;
    cin >> d;
    int m = 0;
    cin >> m;
    int n = 0;
    cin >> n;

    vector<int> stops(n);
    for (size_t i = 0; i < n; ++i)
        cin >> stops.at(i);

    cout << compute_min_refills(d, m, stops) << "\n";

    return 0;
}
