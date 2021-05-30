#include <bits/stdc++.h>

using std::vector;

vector<int> fast_count_segments(vector<int> starts, vector<int> ends, vector<int> points) {
  vector<int> cnt(points.size());
  sort(ends.begin(),ends.end());
  ends.push_back(INT_MAX);
  for (int i = 0; i < starts.size(); i++) {
    starts[i] *= -1;
  }
  sort(starts.begin(),starts.end());
  starts.push_back(INT_MAX);
  for (int i = 0; i < points.size(); i++) {
    cnt[i] = starts.size()-1-(lower_bound(starts.begin(),starts.end(),-points[i])-starts.begin())-(lower_bound(ends.begin(),ends.end(),points[i])-ends.begin());
  }
  return cnt;
}

int main() {
  int n, m;
  std::cin >> n >> m;
  vector<int> starts(n), ends(n);
  for (size_t i = 0; i < starts.size(); i++) {
    std::cin >> starts[i] >> ends[i];
  }
  vector<int> points(m);
  for (size_t i = 0; i < points.size(); i++) {
    std::cin >> points[i];
  }
  //use fast_count_segments
  vector<int> cnt = fast_count_segments(starts, ends, points);
  for (size_t i = 0; i < cnt.size(); i++) {
    std::cout << cnt[i] << ' ';
  }
}
