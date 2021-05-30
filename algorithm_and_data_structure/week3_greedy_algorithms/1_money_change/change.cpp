#include <iostream>

int get_change(int m) {
  int dp[m+1];
  dp[0] = 0;
  for (int i = 1; i <= m; i++) {
    dp[i] = dp[i-1]+1;
    if (i >= 5) {
        dp[i] = std::min(dp[i],dp[i-5]+1);
    }
    if (i >= 10) {
        dp[i] = std::min(dp[i],dp[i-10]+1);
    }
  }
  return dp[m];
}

int main() {
  int m;
  std::cin >> m;
  std::cout << get_change(m) << '\n';
}
