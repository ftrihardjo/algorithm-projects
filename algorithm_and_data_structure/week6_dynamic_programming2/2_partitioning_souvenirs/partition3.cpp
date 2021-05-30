#include <iostream>
#include <vector>

using std::vector;

int partition3(vector<int> &A) {
  int sum = 0, total = 0;
  for (int a : A) {
    sum += a;
  }
  if (sum%3) {
    return 0;
  }
  vector<vector<vector<bool>>> dp(sum+1,vector<vector<bool>>(sum+1,vector<bool>(sum+1)));
  dp[0][0][0] = true;
  for (int a : A) {
    total += a;
    for (int i = 0; i <= total; i++) {
        for (int j = 0; j+i <= total; j++) {
            if (total-j-i >= a) {
                dp[total-j-i][i][j] = dp[total-j-i][i][j]||dp[total-j-i-a][i][j];
            }
            if (i >= a) {
                dp[total-j-i][i][j] = dp[total-j-i][i][j]||dp[total-j-i][i-a][j];
            }
            if (j >= a) {
                dp[total-j-i][i][j] = dp[total-j-i][i][j]||dp[total-j-i][i][j-a];
            }
        }
    }
  }
  return dp[sum/3][sum/3][sum/3];
}

int main() {
  int n;
  std::cin >> n;
  vector<int> A(n);
  for (size_t i = 0; i < A.size(); ++i) {
    std::cin >> A[i];
  }
  std::cout << partition3(A) << '\n';
}
