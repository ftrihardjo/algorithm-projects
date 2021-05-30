#include <bits/stdc++.h>

using namespace std;

double get_optimal_value(int capacity, vector<int> weights, vector<int> values) {
  double value = 0.0;

  pair<double,int> density[weights.size()];
  for (int i = 0; i < weights.size(); i++) {
    density[i] = {values[i]*1.0/weights[i],weights[i]};
  }
  sort(density,density+weights.size(),greater<pair<double,int>>());
  for (int i = 0; capacity; i++) {
    value += density[i].first*min(capacity,density[i].second);
    capacity -= min(capacity,density[i].second);
  }

  return value;
}

int main() {
  int n;
  int capacity;
  cin >> n >> capacity;
  vector<int> values(n);
  vector<int> weights(n);
  for (int i = 0; i < n; i++) {
        cin >> values[i] >> weights[i];
  }

  double optimal_value = get_optimal_value(capacity, weights, values);

  cout.precision(10);
  cout << optimal_value << endl;
  return 0;
}
