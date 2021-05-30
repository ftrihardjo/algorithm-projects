#include <bits/stdc++.h>

using std::vector;

int get_majority_element(vector<int> &a, int left, int right) {
    std::map<int,int> counter;
    while (left < right) {
        if (++counter[a[left++]] > a.size()*1.0/2) {
            return 1;
        }
    }
    return -1;
}

int main() {
  int n;
  std::cin >> n;
  vector<int> a(n);
  for (size_t i = 0; i < a.size(); ++i) {
    std::cin >> a[i];
  }
  std::cout << (get_majority_element(a, 0, a.size()) != -1) << '\n';
}
