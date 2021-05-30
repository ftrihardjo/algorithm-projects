#include <bits/stdc++.h>

using std::vector;
using std::swap;

void randomized_quick_sort(vector<int> &a, int l, int r) {
  sort(a.begin(),a.end());
}

int main() {
  int n;
  std::cin >> n;
  vector<int> a(n);
  for (size_t i = 0; i < a.size(); ++i) {
    std::cin >> a[i];
  }
  randomized_quick_sort(a, 0, a.size() - 1);
  for (size_t i = 0; i < a.size(); ++i) {
    std::cout << a[i] << ' ';
  }
}
