#include <bits/stdc++.h>

int gcd_naive(int a, int b) {
  return std::__gcd(a,b);
}

int main() {
  int a, b;
  std::cin >> a >> b;
  std::cout << gcd_naive(a, b) << std::endl;
  return 0;
}
