package emfusingmodel;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import EMFModel.Graph.Graph;
import EMFModel.Graph.GraphFactory;
import EMFModel.Graph.GraphPackage;
import EMFModel.Graph.Node;
import EMFModel.Graph.impl.GraphPackageImpl;


public class UsingEMFModel {

	
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GraphFactory factory = GraphFactory.eINSTANCE;
		Graph myGraph = factory.createGraph();
		
		/*List<Node> nodes = myGraph.getNodes();
		
		for(Iterator<Node> i = nodes.iterator(); i.hasNext(); ) {
		    
			Node item = i.next();
		    System.out.println(item.getName());	    
		}*/
		
		
		
		System.out.println("deneme");
	
		
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		GraphPackageImpl.init();
	}

}
