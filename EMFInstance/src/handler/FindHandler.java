package handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import EMFModel.Graph.Edge;
import EMFModel.Graph.Graph;
import EMFModel.Graph.GraphFactory;
import EMFModel.Graph.GraphPackage;
import EMFModel.Graph.Node;

public class FindHandler extends AbstractHandler implements IHandler {

	 private QualifiedName path = new QualifiedName("xml", "path");

	 private static GraphFactory factory = GraphFactory.eINSTANCE;
		private static Resource resource;
		public static List<Node> childList = new ArrayList<Node>();
		public static int flag = 0;
		
		private final static String BROTHER_SISTER_RELATION = "brother/sister";
		private final static String COUSIN_RELATION = "cousin";
		private final static String UNCLE_AUNT_RELATION = "uncle/aunt";
		private final static String HUSBAND_WIFE_RELATION = "husband/wife";
		private final static String CHILD_RELATION = "child";
		
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
	    Shell shell = HandlerUtil.getActiveShell(event);
	    ISelection sel = HandlerUtil.getActiveMenuSelection(event);
	    IStructuredSelection selection = (IStructuredSelection) sel;

	    Object firstElement = selection.getFirstElement();
	    //if (firstElement instanceof File) {
	      createOutput(shell, firstElement);

	    //} else {
	      //MessageDialog.openInformation(shell, "Info",
	        // "Please select a Java source file");
	    //}
	    return null;
	  }

	  private void createOutput(Shell shell, Object firstElement) {
	    //String directory;
	    
	    IResource res = (IResource) firstElement;
	    //boolean newDirectory = true;
	    //directory = getPersistentProperty(res, path);

	    Graph myGraph = loadGraph(res);
	    findHusbandWifeRelation(myGraph.getNodes());
	    findBrotherSisterRelation(myGraph.getNodes());
	    
	    /*if (directory != null && directory.length() > 0) {
	      newDirectory = !(MessageDialog.openQuestion(shell, "Question",
	          "Use the previous output directory?"));
	    }
	    if (newDirectory) {
	      DirectoryDialog fileDialog = new DirectoryDialog(shell);
	      directory = fileDialog.open();

	    }
	    if (directory != null && directory.length() > 0) {
	      setPersistentProperty(res, path, directory);
	      write(directory, res);
	    }*/
	    printGraph(myGraph.getNodes());
	    write();
	  }

	  private void write() {
		// TODO Auto-generated method stub
		  try {
				//resource.toString();
		        resource.save(Collections.EMPTY_MAP);
		      } catch (IOException e) {
		        e.printStackTrace();
		      }
	}

	protected String getPersistentProperty(IResource res, QualifiedName qn) {
	    try {
	      return res.getPersistentProperty(qn);
	    } catch (CoreException e) {
	      return "";
	    }
	  }

	  protected void setPersistentProperty(IResource res, QualifiedName qn,
	      String value) {
	    try {
	      res.setPersistentProperty(qn, value);
	    } catch (CoreException e) {
	      e.printStackTrace();
	    }
	  }

	  /*private void write(String dir, Graph myGraph) {
	    try {
	      myGraph.getCorrespondingResource().getName();
	      String test = myGraph.getCorrespondingResource().getName();
	      // Need
	      String[] name = test.split("\\.");
	      String htmlFile = dir + "\\" + name[0] + ".html";
	      FileWriter output = new FileWriter(htmlFile);
	      BufferedWriter writer = new BufferedWriter(output);
	      writer.write("<html>");
	      writer.write("<head>");
	      writer.write("</head>");
	      writer.write("<body>");
	      writer.write("<pre>");
	      writer.write(myGraph.getSource());
	      writer.write("</pre>");
	      writer.write("</body>");
	      writer.write("</html>");
	      writer.flush();
	    } catch (JavaModelException e) {
	    } catch (IOException e) {
	      e.printStackTrace();
	    }

	  }*/
	  
	  /**
	     * Method to save updated graph to My.graph file
	     *
	     * @return void
	     */
		private static void saveGraph() {
			// TODO Auto-generated method stub
			try {
				resource.toString();
		        resource.save(Collections.EMPTY_MAP);
		      } catch (IOException e) {
		        e.printStackTrace();
		      }
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
					System.out.print(e.getRelation().toString());
					if(e.getDegree()>0){
						System.out.println(e.getDegree());
					}
					else{
						System.out.print("\n");
					}
					System.out.println(e.getTarget().getName());
				}
				
				System.out.println("\n");
			}
		}

		/**
	     * Method to open .graph file, create an instance
		 * @param res 
	     *
	     * @return Graph instance 
	     */
		public static Graph loadGraph(IResource res) {
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
		    resource = resSet.getResource(URI.createURI(res.getLocationURI().toString()), true); ;
		    // Get the first model element and cast it to the right type, in my
		    // example everything is hierarchical included in this first node
		    Graph graph = (Graph) resource.getContents().get(0);
		    return graph;
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
												
							Node sourceNode = node;
							Node targetNode = n.getChildTargets().get(x+1+i);

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
				
			for(Node node1 : list1){
				
				for(Node node2 : list2){

					if(!isConnected(node1, node2, COUSIN_RELATION)){
						addNewEdge(node1, node2, COUSIN_RELATION);
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
			flag = 0;
			
			//List<Node> childList = new ArrayList<Node>();
			//List<Node> childList2 = new ArrayList<Node>();
			
			if(y.getOutgoing().size() > 0){
				
				for(Edge e : y.getOutgoing()){
					
					if(e.getRelation().equals(CHILD_RELATION)){
						
						//if(flag == 0){
							childList.add(e.getTarget());
					//	}

						if(!isConnected(x, e.getTarget(), UNCLE_AUNT_RELATION)){
							
							addNewEdge(x, e.getTarget(), UNCLE_AUNT_RELATION);

						}
					}
				}
			}
			
			if(x.getOutgoing().size() > 0){
				
				for(Edge e : x.getOutgoing()){
								
					if(e.getRelation().equals(CHILD_RELATION)){
						
						//if(flag == 0){
							childList.add(e.getTarget());
						//}

						if(!isConnected(y, e.getTarget(), UNCLE_AUNT_RELATION)){
							
							addNewEdge(y, e.getTarget(), UNCLE_AUNT_RELATION);
						}
					}
				}
			}
			
			 findCousinRecursive(childList);
			 childList.clear();
			 flag++;
		}
		
		public static int findCousinRecursive(List<Node> children){
			
			
			// Sadece 1 ebeveyne ait çocuk varsa
			if(findNumberOfParents(children) < 2){
				
				return 0;
			}
			
			// Kuzen ilişkili çocuklar varsa
			else{
					
					makeCousin(children);
				    children = makeNewList(children);

				return findCousinRecursive(children);
			}
		}
		
		
		private static List<Node> makeNewList(List<Node> children) {
			// TODO Auto-generated method stub
			
			List<Node> childList = new ArrayList<Node>();
			
			for(Node n : children){
				
				List<Edge> outgoingEdges = n.getOutgoing();
				
				for(Edge e : outgoingEdges){
					
					if(e.getRelation().equals(CHILD_RELATION)){
						
						childList.add(e.getTarget());
					}
						
				}
			}
			
			return childList;
		}


		private static void makeCousin(List<Node> children) {
			// TODO Auto-generated method stub
			
			for(int x=0; x<children.size()-1 ; x++){
				
				for(int y=x+1; y<children.size(); y++){
					
					if(hasDifferentParents(children.get(x),children.get(y)) ){
						
						ArrayList<Object> list = getParentRelation(children.get(x), children.get(y));
						String relation = (String) list.get(0);
						int deg = (int) list.get(1);
						
						switch(relation){
						
						case BROTHER_SISTER_RELATION:
							
							addNewEdge(children.get(x), children.get(y), COUSIN_RELATION, 1);
							break;
							
						case COUSIN_RELATION:
							
							addNewEdge(children.get(x), children.get(y), COUSIN_RELATION, deg+1);

							break;
							
						
						
						
						}
						
					}
				}
				
				
			}
			
		}


		public static ArrayList<Object> getParentRelation(Node node, Node node2) {
			// TODO Auto-generated method stub
			
			List<Node> nodeParents = node.getChildSources();
			List<Node> nodeParents2 = node2.getChildSources();
			String relation = "";
			int degree = 0;
			
			for(Node n : nodeParents){
				
				List<Edge> incomingEdges = n.getIncoming();
				List<Edge> outgoingEdges = n.getOutgoing();
				
				for(Node n2 : nodeParents2){
					
					for(Edge e : incomingEdges){
						
						if(e.getSource() == n2){
							relation = e.getRelation();
							
							if(relation.equals(COUSIN_RELATION)){
								degree = e.getDegree();
							}
						}
					}
					
					for(Edge e : outgoingEdges){
						
						if(e.getTarget() == n2){
							relation = e.getRelation();
							
							if(relation.equals(COUSIN_RELATION)){
								degree = e.getDegree();
							}
						}
					}
					
				}
			}
			
		
			ArrayList<Object> list = new ArrayList<Object>();
			
			
			
			list.add(relation);
			list.add(degree);
			
			
			
			return list;
		}


		public static void addNewEdge(Node node, Node node2, String cousinRelation, int degree) {
			// TODO Auto-generated method stub
			
			Edge newEdge = factory.createEdge();
			newEdge.setRelation(cousinRelation);
			newEdge.setSource(node);
			newEdge.setTarget(node2);
			newEdge.setDegree(degree);
			
		}


		private static boolean hasDifferentParents(Node node, Node node2) {
			// TODO Auto-generated method stub
			
			
			//parentlardan biri paylaşılıyor olabilir
			
			List<Node> parents = node.getChildSources();
			List<Node> parents2 = node2.getChildSources();
			
				for(int i=0; i<parents2.size(); i++){
					
					if(parents.contains(parents2.get(i))){
						
						return false; 
					}	
			}
			
			return true;
		}


		public static int findNumberOfParents(List<Node> children){
			
			//int numberOfParents = 0;
			List<Node> parents = new ArrayList<Node>();
			
			for(Node childNode : children){
				
				List<Edge> incomingEdges = childNode.getIncoming();
				
				for(Edge edge : incomingEdges){
					
					if(edge.getRelation().equals(CHILD_RELATION)){
						if(!parents.contains(edge.getSource())){
							parents.add(edge.getSource());
						}
						//numberOfParents++;
					}
				}
				
			}
				
				
				
			return parents.size();
			
		}
		
		public static void addCousinRelations(){
			
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
		
		/**
	     * Method to determine whether two nodes were connected before with same relation
	     *
	     * @param Node source node of edge 
	     * 
	     * @param Node target node of edge 
	     * 
	     * @param String relation between two nodes
	     * 
	     * @return boolean
	     */
		public static boolean isConnected(Node sourceNode, Node targetNode, String Relation){
			
			List<Edge> outgoinEdges = sourceNode.getOutgoing();
			
			for(Edge e : outgoinEdges){
				
				if(e.getRelation().equals(Relation) && e.getTarget() == targetNode)
				{
					return true;
				}
				
			}
			
			List<Edge> incomingEdges = sourceNode.getIncoming();
			
			for(Edge e : incomingEdges){
				
				if(e.getRelation().equals(Relation) && e.getSource() == targetNode)
				{
					return true;
				}
				
			}
			
			return false;
		}
		
		

	


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
