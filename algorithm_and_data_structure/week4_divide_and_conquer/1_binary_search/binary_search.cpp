#include <iostream>
#include <cassert>
#include <vector>
#include <algorithm>

using std::vector;

int main() {
  int n;
  std::cin >> n;
  vector<int> a(n);
  for (size_t i = 0; i < a.size(); i++) {
    std::cin >> a[i];
  }
  int m;
  std::cin >> m;
  vector<int> b(m);
  for (int i = 0; i < m; ++i) {
    std::cin >> b[i];
  }
  for (int i = 0; i < m; ++i) {
    if (binary_search(a.begin(),a.end(),b[i])) {
        std::cout << lower_bound(a.begin(),a.end(),b[i])-a.begin() << ' ';
    } else {
        std::cout << "-1 ";
    }
  }
}
