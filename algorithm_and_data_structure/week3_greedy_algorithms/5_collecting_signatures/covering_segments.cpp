#include <algorithm>
#include <iostream>
#include <climits>
#include <vector>

using std::vector;

vector<int> optimal_points(vector<std::pair<int,int>> &segments) {
  vector<int> points;
  sort(segments.begin(),segments.end());
  points.push_back(segments[0].first);
  for (size_t i = 1, p = segments[0].first; i < segments.size(); ++i) {
    if (p < segments[i].second) {
        points.push_back(segments[i].first);
        p = segments[i].first;
    }
  }
  return points;
}

int main() {
  int n;
  std::cin >> n;
  vector<std::pair<int,int>> segments(n);
  for (size_t i = 0; i < segments.size(); ++i) {
    std::cin >> segments[i].second >> segments[i].first;
  }
  vector<int> points = optimal_points(segments);
  std::cout << points.size() << "\n";
  for (size_t i = 0; i < points.size(); ++i) {
    std::cout << points[i] << " ";
  }
}
