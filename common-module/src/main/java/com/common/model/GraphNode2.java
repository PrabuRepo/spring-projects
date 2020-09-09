package com.common.model;

import java.util.ArrayList;
import java.util.List;

public class GraphNode2 {
	public int label;
	public List<GraphNode2> neighbors;

	public GraphNode2(int x) {
		label = x;
		neighbors = new ArrayList<>();
	}
}
