package org.aries.ui.tree;


import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@SessionScoped
@Named("treeStateController")
public class TreeStateController /*extends Controller*/ implements Serializable {
	
//	private Map<Integer, TreeState> treeStateMap = new HashMap<Integer, TreeState>();
//
//	public TreeState getTreeState(Integer key) {
//		TreeState treeState = treeStateMap.get(key); 
//		if (treeState == null) {
//			treeState = new TreeState();
//			treeStateMap.put(key, treeState);
//		}
//
//		return treeState;
//	}
//
//	public void setTreeState(Integer key, TreeState treeState) {
//		treeStateMap.put(key, treeState);
//	}
}