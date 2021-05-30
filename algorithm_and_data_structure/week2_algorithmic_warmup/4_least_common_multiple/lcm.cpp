#include <bits/stdc++.h>

long long lcm_naive(int a, int b) {

  return 1ll * a * b/std::__gcd(a,b);
}

int main() {
  int a, b;
  std::cin >> a >> b;
  std::cout << lcm_naive(a, b) << std::endl;
  return 0;
}
