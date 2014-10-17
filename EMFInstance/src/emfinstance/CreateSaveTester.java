package emfinstance;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EDataType.Internal.ConversionDelegate.Factory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
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
	    findHusWif(myGraph.getNodes());
	    	    
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
			EList<Edge> outgoingEdges = n.getOutgoing();
			
			if(outgoingEdges.size() > 1){				
				
				int index = 0;				
				for(Edge e : outgoingEdges){					
					
					for(int i=0 ; i<outgoingEdges.size()-index-1 ; i++){
											
						Edge newEdge = factory.createEdge();
						newEdge.setRelation("brother/sister");
						
						Node sourceNode = e.getTarget();
						newEdge.setSource(sourceNode); 
						
						Node targetSource = outgoingEdges.get(index+1+i).getTarget();
						newEdge.setTarget(targetSource); 
						
						findUncleAuntRelation(sourceNode,targetSource);
						
					}				
					index++;
				}
			}
		}
	}

	public static void findUncleAuntRelation(Node x, Node y) {
		// TODO Auto-generated method stub
		
		if(y.getOutgoing().size() > 0){
			
			for(Edge e : y.getOutgoing()){
				
				if(e.getRelation().equals("child")){
					
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

					Edge newEdge = factory.createEdge();
					newEdge.setRelation("uncle/aunt");
					newEdge.setSource(y);
					newEdge.setTarget(e.getTarget());
				}
			}
		}
		
	}
	
	

}
