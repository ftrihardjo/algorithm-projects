#include <bits/stdc++.h>

using std::vector;
using std::string;
using std::max;
using std::min;

long long eval(long long a, long long b, char op) {
  if (op == '*') {
    return a * b;
  } else if (op == '+') {
    return a + b;
  } else if (op == '-') {
    return a - b;
  } else {
    assert(0);
  }
}

long long get_maximum_value(const string &exp) {
  long long numbers[exp.length()/2+1],maxim[exp.length()/2+1][exp.length()/2+1],minim[exp.length()/2+1][exp.length()/2+1];
  char operations[exp.length()/2];
  for (int i = 0; i <= exp.length()/2; i++) {
    numbers[i] = exp[2*i]-'0';
    maxim[i][i] = numbers[i];
    minim[i][i] = numbers[i];
  }
  for (int i = 0; i < exp.length()/2; i++) {
    operations[i] = exp[2*i+1];
  }
  for (int i = exp.length()/2; i+1; i--) {
    for (int j = i+1; j <= exp.length()/2; j++) {
        maxim[i][j] = LLONG_MIN;
        minim[i][j] = LLONG_MAX;
        for (int k = i; k < j; k++) {
            maxim[i][j] = max(maxim[i][j],eval(maxim[i][k],maxim[k+1][j],operations[k]));
            maxim[i][j] = max(maxim[i][j],eval(minim[i][k],maxim[k+1][j],operations[k]));
            maxim[i][j] = max(maxim[i][j],eval(maxim[i][k],minim[k+1][j],operations[k]));
            maxim[i][j] = max(maxim[i][j],eval(minim[i][k],minim[k+1][j],operations[k]));
            minim[i][j] = min(minim[i][j],eval(maxim[i][k],maxim[k+1][j],operations[k]));
            minim[i][j] = min(minim[i][j],eval(minim[i][k],maxim[k+1][j],operations[k]));
            minim[i][j] = min(minim[i][j],eval(maxim[i][k],minim[k+1][j],operations[k]));
            minim[i][j] = min(minim[i][j],eval(minim[i][k],minim[k+1][j],operations[k]));
        }
    }
  }
  return maxim[0][exp.length()/2];
}

int main() {
  string s;
  std::cin >> s;
  std::cout << get_maximum_value(s) << '\n';
}
