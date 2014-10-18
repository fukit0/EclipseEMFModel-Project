package emfinstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
	
	private final static String BROTHER_SISTER_RELATION = "brother/sister";
	private final static String COUSIN_RELATION = "cousin";
	private final static String UNCLE_AUNT_RELATION = "uncle/aunt";
	private final static String HUSBAND_WIFE_RELATION = "husband/wife";
	private final static String CHILD_RELATION = "child";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Initialize the model
	    GraphPackage.eINSTANCE.eClass();
	    
	    //GraphFactory factory = GraphFactory.eINSTANCE;
	    // Register the XMI resource factory for the .graph extension
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
	    
		//Graph myGraph = loadGraph();
	    
		//printGraph(myGraph.getNodes());	    
	    
		findHusbandWifeRelation(myGraph.getNodes());
	    findBrotherSisterRelation(myGraph.getNodes());
	        
	    //saveGraph(myGraph);
	    try {
	        resource.save(Collections.EMPTY_MAP);
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    
	    printGraph(myGraph.getNodes());	  
	}
	
	
	/**
     * Method to print-out nodes and relations between them in graph
     *
     * @param EList<> list of nodes in graph
     * 
     * @return void
     */
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

	/**
     * Method to open .graph file, create an instance
     *
     * @return Graph instance 
     */
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

	/**
     * Method to find parent nodes of a node and add an edge between them
     * 
     * @param EList<> list of nodes in graph
     * 
     * @return void
     */
	public static void findHusbandWifeRelation(EList<Node> nodes) {
		// TODO Auto-generated method stub
		
		for(Node n : nodes){
			
			if(n.getChildSources().size() == 2){
				/*	
				Edge newEdge = factory.createEdge();
				newEdge.setRelation(HUSBAND_WIFE_RELATION);
				newEdge.setSource(n.getChildSources().get(0));
				newEdge.setTarget(n.getChildSources().get(1));
				*/
				Node sourceNode = n.getChildSources().get(0);
				Node targetNode = n.getChildSources().get(1);
				
				if(!isConnected(sourceNode, targetNode, HUSBAND_WIFE_RELATION)){
					
					addNewEdge(sourceNode, targetNode, HUSBAND_WIFE_RELATION);				

				}
				
			}
		}
		
	}

	
	/**
     * Method to find nodes which has same parent nodes and add an edge between them
     *
     * @param EList<> list of nodes in graph
     * 
     * @return void
     */
	public static void findBrotherSisterRelation(EList<Node> nodes) {
		// TODO Auto-generated method stub
		
		//EList<Node> nodeList = myGraph.getNodes();		
		for(Node n : nodes){		
			//EList<Edge> outgoingEdges = n.getOutgoing();
			
			if(n.getChildTargets().size() > 1){				
				
				int x = 0;
				
				for(Node node : n.getChildTargets()){					
					
					for(int i=0 ; i<n.getChildTargets().size()-x-1 ; i++){
											
						//Edge newEdge = factory.createEdge();
						//newEdge.setRelation(BROTHER_SISTER_RELATION);
						Node sourceNode = node;
						//newEdge.setSource(sourceNode); 
						Node targetNode = n.getChildTargets().get(x+1+i);
						//newEdge.setTarget(targetSource);
						
						if(!isConnected(sourceNode, targetNode, BROTHER_SISTER_RELATION)){
							addNewEdge(sourceNode, targetNode, BROTHER_SISTER_RELATION);

						}
						
						findUncleAuntRelation(sourceNode,targetNode);
						
					}				
					x++;
				}
			}
		}
	}

	/**
     * Method to find parent nodes of a node and add an edge between them
     * 
     * @param Node a node specify source
     * 
     * @param Node a node specify target
     * 
     * @return void
     */
	public static void findUncleAuntRelation(Node x, Node y) {
		// TODO Auto-generated method stub
		
		List<Node> childList1 = new ArrayList<Node>();
		List<Node> childList2 = new ArrayList<Node>();
		
		if(y.getOutgoing().size() > 0){
			
			for(Edge e : y.getOutgoing()){
				
				if(e.getRelation().equals(CHILD_RELATION)){
					
					childList1.add(e.getTarget());
					/*
					Edge newEdge = factory.createEdge();
					newEdge.setRelation(UNCLE_AUNT_RELATION);
					newEdge.setSource(x);
					newEdge.setTarget(e.getTarget());
					*/
					if(!isConnected(x, e.getTarget(), UNCLE_AUNT_RELATION)){
						
						addNewEdge(x, e.getTarget(), UNCLE_AUNT_RELATION);

					}
				}
			}
		}
		
		if(x.getOutgoing().size() > 0){
			
			for(Edge e : x.getOutgoing()){
							
				if(e.getRelation().equals(CHILD_RELATION)){
					
					childList2.add(e.getTarget());
					/*
					Edge newEdge = factory.createEdge();
					newEdge.setRelation(UNCLE_AUNT_RELATION);
					newEdge.setSource(y);
					newEdge.setTarget(e.getTarget());
					*/
					if(!isConnected(y, e.getTarget(), UNCLE_AUNT_RELATION)){
						
						addNewEdge(y, e.getTarget(), UNCLE_AUNT_RELATION);
					}
				}
			}
		}
		
		findCousinRelation(childList1,childList2);
		
	}

	/**
     * Method to find nodes which their parent nodes have bro/sis relation and add edges between them
     *
     * @param List<> list of child nodes 
     * 
     * @param List<> list of child nodes
     * 
     * @return void
     */
	public static void findCousinRelation(List<Node> list1, List<Node> list2) {
		// TODO Auto-generated method stub
		
		//int count1 = list1.size();
		//int count2 = list2.size();
		
		for(Node node1 : list1){
			
			for(Node node2 : list2){
				
				/*
				Edge newEdge = factory.createEdge();
				newEdge.setRelation(COUSIN_RELATION);
				newEdge.setSource(node1);
				newEdge.setTarget(node2);
				*/
				if(!isConnected(node1, node2, COUSIN_RELATION)){
					addNewEdge(node1, node2, COUSIN_RELATION);
				}
				
			}
		}
		
		
	}
	
	/**
     * Method to find nodes which their parent nodes have bro/sis relation and add edges between them
     *
     * @param Node source node of edge that will be added
     * 
     * @param Node target node of edge that will be added
     * 
     * @param String relation that will be presented between two nodes
     * 
     * @return void
     */
	public static void addNewEdge(Node sourceNode, Node targetNode, String relation){
		
		Edge newEdge = factory.createEdge();
		newEdge.setRelation(relation);
		newEdge.setSource(sourceNode);
		newEdge.setTarget(targetNode);
		
	}
	
	public static boolean isConnected(Node sourceNode, Node targetNode, String Relation){
		
		List<Edge> outgoinEdges = sourceNode.getOutgoing();
		
		for(Edge e : outgoinEdges){
			
			if(e.getTarget() == targetNode && e.getRelation().equals(Relation))
			{
				return true;
			}
			
		}
		return false;
	}
	
	

}
