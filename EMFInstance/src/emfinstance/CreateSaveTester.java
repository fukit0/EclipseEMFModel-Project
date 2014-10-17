package emfinstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EDataType.Internal.ConversionDelegate.Factory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import EMFModel.Graph.Edge;
import EMFModel.Graph.Graph;
import EMFModel.Graph.GraphFactory;
import EMFModel.Graph.GraphPackage;
import EMFModel.Graph.Node;



public class CreateSaveTester {

	public static GraphFactory factory = GraphFactory.eINSTANCE;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Graph myGraph = loadGraph();
	    
		//printGraph(myGraph.getNodes());	    
	    
	    findBroSis(myGraph.getNodes());
	    //findHusWif(myGraph.getNodes());
	    	    
	    // saveGraph(myGraph);
	    
	    printGraph(myGraph.getNodes());	  
	}
	
	public static void printGraph(EList<Node> nodes) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < nodes.size(); i++) {
			
			for(Edge e : nodes.get(i).getOutgoing()){
				System.out.println(nodes.get(i).getName());
				System.out.println(e.getRelation().toString());
				System.out.println(e.getTarget().getName());
			}
			
			System.out.println("\n");
		}
	}

	public static Graph loadGraph() {
		// Initialize the model
	    GraphPackage.eINSTANCE.eClass();
	    
	    //GraphFactory factory = GraphFactory.eINSTANCE;
	    // Register the XMI resource factory for the .website extension
	    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("graph", new XMIResourceFactoryImpl());
	    
	    // Obtain a new resource set
	    ResourceSet resSet = new ResourceSetImpl();

	    // Get the resource
	    Resource resource = resSet.getResource(URI.createURI("FamilyGraph/My.graph"), true);
	    // Get the first model element and cast it to the right type, in my
	    // example everything is hierarchical included in this first node
	    Graph myGraph = (Graph) resource.getContents().get(0);
	    return myGraph;
	  }

	public static void findHusWif(EList<Node> nodes) {
		// TODO Auto-generated method stub
		
		for(Node n : nodes){
			int counter = 0;
			
			EList<Edge> incomingEdges = n.getIncoming();
			
			for(int i = 0; i < incomingEdges.size(); i++){
				
				if(incomingEdges.get(i).getRelation().equals("child")){
					counter++;
				}
			}
			
			if(counter == 2){
				
				Edge newEdge = factory.createEdge();
				newEdge.setRelation("husband/wife");
				newEdge.setSource(incomingEdges.get(0).getSource());
				newEdge.setTarget(incomingEdges.get(1).getSource());
				
			}
		}
		
	}

	public static void findBroSis(EList<Node> nodes) {
		// TODO Auto-generated method stub
		
		//EList<Node> nodeList = myGraph.getNodes();		
		for(Node n : nodes){		
			//EList<Edge> outgoingEdges = n.getOutgoing();
			
			if(n.getChildTargets().size() > 1){				
				
				int x = 0;
				
				for(Node node : n.getChildTargets()){					
					
					for(int i=0 ; i<n.getChildTargets().size()-x-1 ; i++){
											
						Edge newEdge = factory.createEdge();
						newEdge.setRelation("brother/sister");
						
						Node sourceNode = node;
						newEdge.setSource(sourceNode); 
						
						Node targetSource = n.getChildTargets().get(x+1+i);
						newEdge.setTarget(targetSource); 
						
						findUncleAuntRelation(sourceNode,targetSource);
						
					}				
					x++;
				}
			}
		}
	}

	public static void findUncleAuntRelation(Node x, Node y) {
		// TODO Auto-generated method stub
		
		List<Node> c1 = new ArrayList<Node>();
		List<Node> c2 = new ArrayList<Node>();
		
		if(y.getOutgoing().size() > 0){
			
			for(Edge e : y.getOutgoing()){
				
				if(e.getRelation().equals("child")){
					
					c1.add(e.getTarget());
					Edge newEdge = factory.createEdge();
					newEdge.setRelation("uncle/aunt");
					newEdge.setSource(x);
					newEdge.setTarget(e.getTarget());
				}
			}
		}
		
		if(x.getOutgoing().size() > 0){
			
			for(Edge e : x.getOutgoing()){
							
				if(e.getRelation().equals("child")){
					
					c2.add(e.getTarget());
					Edge newEdge = factory.createEdge();
					newEdge.setRelation("uncle/aunt");
					newEdge.setSource(y);
					newEdge.setTarget(e.getTarget());
				}
			}
		}
		
		findCousin(c1,c2);
		
	}

	public static void findCousin(List<Node> list1, List<Node> list2) {
		// TODO Auto-generated method stub
		
		//int count1 = list1.size();
		//int count2 = list2.size();
		
		for(Node node1 : list1){
			
			for(Node node2 : list2){
				
				Edge edge = factory.createEdge();
				edge.setRelation("cousin");
				edge.setSource(node1);
				edge.setTarget(node2);
			}
		}
		
		
	}
	
	

}
