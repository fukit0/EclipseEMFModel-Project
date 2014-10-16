package emfinstance;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import EMFModel.Graph.Graph;
import EMFModel.Graph.GraphPackage;
import EMFModel.Graph.Node;

public class CreateSaveTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		// Initialize the model
	    GraphPackage.eINSTANCE.eClass();
	    
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
	    
	    List<Node> nodes = myGraph.getNodes();
	    
	    for (int i = 0; i < nodes.size(); i++) {
			System.out.println(nodes.get(i).getName());
		}
	    
		
	}

}
