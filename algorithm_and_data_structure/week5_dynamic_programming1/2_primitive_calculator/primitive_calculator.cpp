#include <iostream>
#include <vector>
#include <algorithm>

using std::vector;

vector<int> optimal_sequence(int n) {
  std::vector<int> sequence;
  int dp[n+1];
  dp[1] = 0;
  for (int i = 2; i <= n; i++) {
    dp[i] = dp[i-1]+1;
    if (!(i%2)) {
        dp[i] = std::min(dp[i],dp[i/2]+1);
    }
    if (!(i%3)) {
        dp[i] = std::min(dp[i],dp[i/3]+1);
    }
  }
  sequence.push_back(n);
  while (n > 1) {
    if (dp[n] == dp[n-1]+1) {
        sequence.push_back(--n);
    } else if (!(n%2) && dp[n] == dp[n/2]+1) {
        n /= 2;
        sequence.push_back(n);
    } else {
        n /= 3;
        sequence.push_back(n);
    }
  }
  reverse(sequence.begin(), sequence.end());
  return sequence;
}

int main() {
  int n;
  std::cin >> n;
  vector<int> sequence = optimal_sequence(n);
  std::cout << sequence.size() - 1 << std::endl;
  for (size_t i = 0; i < sequence.size(); ++i) {
    std::cout << sequence[i] << " ";
  }
}
