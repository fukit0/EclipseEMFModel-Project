package emfusingmodel;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import EMFModel.Graph.Graph;
import EMFModel.Graph.GraphFactory;
import EMFModel.Graph.GraphPackage;
import EMFModel.Graph.Node;
import EMFModel.Graph.impl.GraphImpl;
import EMFModel.Graph.impl.GraphPackageImpl;
import EMFModel.Graph.impl.NodeImpl;


public class UsingEMFModel {

	
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphPackageImpl.init();
		
		GraphFactory factory = GraphFactory.eINSTANCE;
		
		Graph myGraph = factory.createGraph();
		
		Node d = factory.createNode();
		d.setName("D");
		
		Node b = factory.createNode();
		b.setName("B");
		
		//System.out.println(d.getName());
		
		//
		//addNode -> nullPointerException
		//
		
		if(myGraph == null){
			System.out.println("null");
		}
		else{
			if(b == null || d == null)
			{
				System.out.println("nodes are null");
				
			}
			else{
				myGraph.addNode(b);
				myGraph.addNode(d);
			}
			
		}
		
		List<Node> nodes = myGraph.getNodes();
		
		for (int i = 0; i < nodes.size(); i++) {
			System.out.println(nodes.get(i).getName());
		}
		
		
		
		//System.out.println("deneme");
	
		
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
