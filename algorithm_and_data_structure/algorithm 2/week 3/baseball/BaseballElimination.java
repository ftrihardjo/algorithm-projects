import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.HashMap;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination {
    private HashMap<String,Integer> w, r, l, id;
    private int[][] g;
    private int n;
    private ArrayList<String> teams;
    public BaseballElimination(String filename) {                   // create a baseball division from given filename in format specified below
        if (filename == null) {
	    throw new IllegalArgumentException("invalid filename");
	} else {
	    In in = new In(filename);
	    n = in.readInt();
	    g = new int[numberOfTeams()][numberOfTeams()];
	    w = new HashMap<String,Integer>(numberOfTeams());
	    l = new HashMap<String,Integer>(numberOfTeams());
	    r = new HashMap<String,Integer>(numberOfTeams());
	    id = new HashMap<String,Integer>(numberOfTeams());
	    teams = new ArrayList<String>(numberOfTeams());
	    for (int i = 0; i < numberOfTeams(); i++) {
		teams.add(in.readString());
		w.put(teams.get(i),in.readInt());
		l.put(teams.get(i),in.readInt());
		r.put(teams.get(i),in.readInt());
		id.put(teams.get(i),i);
		for (int j = 0; j < numberOfTeams(); j++) {
		    g[i][j] = in.readInt();
		}
	    }
	}
    }
    public              int numberOfTeams() {                       // number of teams
        return n;
    }
    public Iterable<String> teams() {                               // all teams
        return teams;
    }
    public              int wins(String team) {                     // number of wins for given team
	if (team == null || w.get(team) == null) {
	    throw new IllegalArgumentException("invalid team");
	} else {
	    return w.get(team).intValue();
	}
    }
    public              int losses(String team) {                   // number of losses for given team
	if (team == null || l.get(team) == null) {
	    throw new IllegalArgumentException("invalid team");
	} else {
	    return l.get(team).intValue();
	}
    }
    public              int remaining(String team) {                // number of remaining games for given team
	if (team == null || r.get(team) == null) {
	    throw new IllegalArgumentException("invalid team");
	} else {
	    return r.get(team).intValue();
	}
    }
    public              int against(String team1, String team2) {   // number of remaining games between team1 and team2
	if (team1 == null || team2 == null || id.get(team1) == null || id.get(team2) == null) {
	    throw new IllegalArgumentException("invalid team");
	} else {
	    return g[id.get(team1).intValue()][id.get(team2).intValue()];
	}
    }
    public          boolean isEliminated(String team) {             // is given team eliminated?
	if (team == null || id.get(team) == null) {
	    throw new IllegalArgumentException("invalid team");
	} else if (trivial_elimination(team)) {
	    return true;
	} else {
	    FlowNetwork G = new FlowNetwork((numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()+1);
	    int i, j, m, sum = 0;
	    for (i = 0, m = 0; !teams.get(i).equals(team); i++) {
		for (j = i+1; j < numberOfTeams() && !teams.get(j).equals(team); j++, m++) {
		    G.addEdge(new FlowEdge((numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()-1,m,g[i][j]*1.0));
		    sum += g[i][j];
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+i,Double.POSITIVE_INFINITY));
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+j,Double.POSITIVE_INFINITY));
		}
		for (j++; j < numberOfTeams(); j++, m++) {
		    G.addEdge(new FlowEdge((numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()-1,m,g[i][j]*1.0));
		    sum += g[i][j];
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+i,Double.POSITIVE_INFINITY));
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+j-1,Double.POSITIVE_INFINITY));
		}	     
	    }
	    for (i++; i < numberOfTeams(); i++) {
		for (j = i+1; j < numberOfTeams(); j++, m++) {
		    G.addEdge(new FlowEdge((numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()-1,m,g[i][j]*1.0));
		    sum += g[i][j];
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+i-1,Double.POSITIVE_INFINITY));
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+j-1,Double.POSITIVE_INFINITY));
		}	     
	    }
	    for (i = 0; !teams.get(i).equals(team); i++) {
		G.addEdge(new FlowEdge((numberOfTeams()-1)*(numberOfTeams()-2)/2+i,(numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams(),(wins(team)+remaining(team)-wins(teams.get(i)))*1.0));
	    }
	    for (i++; i < numberOfTeams(); i++) {
		G.addEdge(new FlowEdge((numberOfTeams()-1)*(numberOfTeams()-2)/2+i-1,(numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams(),(wins(team)+remaining(team)-wins(teams.get(i)))*1.0));
	    }
	    return sum*1.0 != new FordFulkerson(G,(numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()-1,(numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()).value();
	}
    }
    public Iterable<String> certificateOfElimination(String team) { // subset R of teams that eliminates given team; null if not eliminated
	if (team == null || id.get(team) == null) {
	    throw new IllegalArgumentException("invalid team");
	} else if (isEliminated(team)) {
	    ArrayList<String> R;
	    for (String team_name : teams()) {
		if (!team_name.equals(team) && w.get(team).intValue()+r.get(team).intValue() < w.get(team_name).intValue()) {
		    R = new ArrayList<String>(1);
		    R.add(team_name);
		    return R;
		}
	    }
	    R = new ArrayList<String>(numberOfTeams());
	    FlowNetwork G = new FlowNetwork((numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()+1);
	    int i, j, m;
	    for (i = 0, m = 0; !teams.get(i).equals(team); i++) {
		for (j = i+1; j < numberOfTeams() && !teams.get(j).equals(team); j++, m++) {
		    G.addEdge(new FlowEdge((numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()-1,m,g[i][j]*1.0));
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+i,Double.POSITIVE_INFINITY));
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+j,Double.POSITIVE_INFINITY));
		}
		for (j++; j < numberOfTeams(); j++, m++) {
		    G.addEdge(new FlowEdge((numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()-1,m,g[i][j]*1.0));
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+i,Double.POSITIVE_INFINITY));
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+j-1,Double.POSITIVE_INFINITY));
		}	     
	    }
	    for (i++; i < numberOfTeams(); i++) {
		for (j = i+1; j < numberOfTeams(); j++, m++) {
		    G.addEdge(new FlowEdge((numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()-1,m,g[i][j]*1.0));
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+i-1,Double.POSITIVE_INFINITY));
		    G.addEdge(new FlowEdge(m,(numberOfTeams()-1)*(numberOfTeams()-2)/2+j-1,Double.POSITIVE_INFINITY));
		}	     
	    }	    
	    for (i = 0; !teams.get(i).equals(team); i++) {
		G.addEdge(new FlowEdge((numberOfTeams()-1)*(numberOfTeams()-2)/2+i,(numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams(),wins(team)*1.0+remaining(team)*1.0-wins(teams.get(i))*1.0));		
	    }
	    for (i++; i < numberOfTeams(); i++) {
		G.addEdge(new FlowEdge((numberOfTeams()-1)*(numberOfTeams()-2)/2+i-1,(numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams(),wins(team)*1.0+remaining(team)*1.0-wins(teams.get(i))*1.0));
	    }
	    FordFulkerson H = new FordFulkerson(G,(numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams()-1,(numberOfTeams()-2)*(numberOfTeams()-1)/2+numberOfTeams());
	    for (i = 0; !teams.get(i).equals(team); i++) {
		if (H.inCut(i+(numberOfTeams()-1)*(numberOfTeams()-2)/2)) {
		    R.add(teams.get(i));
		}
	    }
	    for (i++; i < numberOfTeams(); i++) {
		if (H.inCut(i+(numberOfTeams()-1)*(numberOfTeams()-2)/2-1)) {
		    R.add(teams.get(i));
		}
	    }
	    return R;
	} else {
	    return null;
	}
    }
    private boolean trivial_elimination(String team) {
	for (String team_name : teams()) {
	    if (!team_name.equals(team) && w.get(team).intValue()+r.get(team).intValue() < w.get(team_name).intValue()) {
		return true;
	    }
	}
	return false;
    }
    public static void main(String[] args) {
	BaseballElimination division = new BaseballElimination(args[0]);
	for (String team : division.teams()) {
	    if (division.isEliminated(team)) {
		StdOut.print(team + " is eliminated by the subset R = { ");
		for (String t : division.certificateOfElimination(team)) {
		    StdOut.print(t + " ");
		}
		StdOut.println("}");
	    }
	    else {
		StdOut.println(team + " is not eliminated");
	    }
	}
    }
}
