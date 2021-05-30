#include <iostream>
#include <vector>

using std::vector;

int optimal_weight(int W, const vector<int> &w) {
  vector<vector<int>> dp(w.size()+1,vector<int>(W+1));
  for (int i = 1; i <= w.size(); i++) {
    for (int j = 0; j <= W; j++) {
        dp[i][j] = dp[i-1][j];
        if (j-w[i-1] >= 0) {
            dp[i][j] = std::max(dp[i][j],dp[i-1][j-w[i-1]]+w[i-1]);
        }
    }
  }
  return dp[w.size()][W];
}

int main() {
  int n, W;
  std::cin >> W >> n;
  vector<int> w(n);
  for (int i = 0; i < n; i++) {
    std::cin >> w[i];
  }
  std::cout << optimal_weight(W, w) << '\n';
}
