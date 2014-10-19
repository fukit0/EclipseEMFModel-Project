/**
 */
package EMFModel.Graph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link EMFModel.Graph.Edge#getSource <em>Source</em>}</li>
 *   <li>{@link EMFModel.Graph.Edge#getTarget <em>Target</em>}</li>
 *   <li>{@link EMFModel.Graph.Edge#getRelation <em>Relation</em>}</li>
 *   <li>{@link EMFModel.Graph.Edge#getDegree <em>Degree</em>}</li>
 * </ul>
 * </p>
 *
 * @see EMFModel.Graph.GraphPackage#getEdge()
 * @model
 * @generated
 */
public interface Edge extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link EMFModel.Graph.Node#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' container reference.
	 * @see #setSource(Node)
	 * @see EMFModel.Graph.GraphPackage#getEdge_Source()
	 * @see EMFModel.Graph.Node#getOutgoing
	 * @model opposite="outgoing" transient="false"
	 * @generated
	 */
	Node getSource();

	/**
	 * Sets the value of the '{@link EMFModel.Graph.Edge#getSource <em>Source</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' container reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Node value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link EMFModel.Graph.Node#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Node)
	 * @see EMFModel.Graph.GraphPackage#getEdge_Target()
	 * @see EMFModel.Graph.Node#getIncoming
	 * @model opposite="incoming"
	 * @generated
	 */
	Node getTarget();

	/**
	 * Sets the value of the '{@link EMFModel.Graph.Edge#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Node value);

	/**
	 * Returns the value of the '<em><b>Relation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relation</em>' attribute.
	 * @see #setRelation(String)
	 * @see EMFModel.Graph.GraphPackage#getEdge_Relation()
	 * @model
	 * @generated
	 */
	String getRelation();

	/**
	 * Sets the value of the '{@link EMFModel.Graph.Edge#getRelation <em>Relation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relation</em>' attribute.
	 * @see #getRelation()
	 * @generated
	 */
	void setRelation(String value);

	/**
	 * Returns the value of the '<em><b>Degree</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Degree</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Degree</em>' attribute.
	 * @see #setDegree(int)
	 * @see EMFModel.Graph.GraphPackage#getEdge_Degree()
	 * @model default="0"
	 * @generated
	 */
	int getDegree();

	/**
	 * Sets the value of the '{@link EMFModel.Graph.Edge#getDegree <em>Degree</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Degree</em>' attribute.
	 * @see #getDegree()
	 * @generated
	 */
	void setDegree(int value);

} // Edge
