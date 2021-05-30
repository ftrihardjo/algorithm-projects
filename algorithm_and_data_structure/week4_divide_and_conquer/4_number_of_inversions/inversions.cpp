#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp> // Common file
#include <ext/pb_ds/tree_policy.hpp>
using namespace std;
namespace tree {
  template<typename T,
           typename cmp_fn = less_equal<T>>
  using order_statistic_tree =
    __gnu_pbds::tree<T,
                     __gnu_pbds::null_type,
                     cmp_fn,
                     __gnu_pbds::rb_tree_tag,
                     __gnu_pbds::tree_order_statistics_node_update>;
}
long long get_number_of_inversions(vector<int> &a, size_t left, size_t right) {
  long long number_of_inversions = 0;
  tree::order_statistic_tree<int> b;
  for (b.insert(INT_MAX); right > left; b.insert(a[--right])) {
    number_of_inversions += b.order_of_key(a[right-1]);
  }
  return number_of_inversions;
}

int main() {
  int n;
  cin >> n;
  vector<int> a(n);
  for (size_t i = 0; i < a.size(); i++) {
    cin >> a[i];
  }
  cout << get_number_of_inversions(a, 0, a.size()) << '\n';
}
