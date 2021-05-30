#include <bits/stdc++.h>

using std::vector;
using std::string;
using std::pair;
using std::min;

double minimal_distance(pair<int,int> *points, int n) {
  double d = LLONG_MAX;
  if (n <= 3) {
    for (int i = 0; i < n; i++) {
        for (int j = i+1; j < n; j++) {
            double y = points[j].first-points[i].first, x = points[j].second-points[i].second;
            d = min(d,sqrt(x*x+y*y));
        }
    }
    return d;
  }
  d = min(d,min(minimal_distance(points,n/2),minimal_distance(points+n/2,n-n/2)));
  vector<pair<int,int>> strip;
  for (int i = 0; i < n; i++) {
    if (abs(points[i].first-points[n/2].first) <= d) {
        strip.push_back({points[i].second,points[i].first});
    }
  }
  sort(strip.begin(),strip.end());
  for (int i = 0; i < strip.size(); i++) {
    for (int j = i+1; j < strip.size() && strip[j].first-strip[i].first <= d; j++) {
        double y = strip[j].first-strip[i].first, x = strip[j].second-strip[i].second;
        d = min(d,sqrt(x*x+y*y));
    }
  }
  return d;
}

int main() {
  size_t n;
  std::cin >> n;
  pair<int,int> points[n];
  for (size_t i = 0; i < n; i++) {
    std::cin >> points[i].first >> points[i].second;
  }
  sort(points,points+n);
  std::cout << std::fixed;
  std::cout << std::setprecision(9) << minimal_distance(points,n) << "\n";
}
