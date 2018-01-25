// Alexander Agnitti
// 11/24/17

import java.util.*;


// A class to be used in conjunction with a HuffNode class
//		in implementing the Huffman Algorithm. 
public class Tree {

	private Tree m_parentTree;
	private Tree m_leftTree;
	private Tree m_rightTree;
	private HuffNode m_value;

	public Tree(Tree parentTree, Tree leftTree, Tree rightTree, HuffNode value) {
		m_parentTree = parentTree;
		m_leftTree = leftTree;
		m_rightTree = rightTree;
		m_value = value;
	}

	public Tree getParentTree() { return m_parentTree; }

	public Tree getLeftTree() { return m_leftTree; }

	public Tree getRightTree() { return m_rightTree; }

	public HuffNode getValue() { return m_value; }

	public void setParentTree(Tree parentTree) { m_parentTree = parentTree; }

	public void setLeftTree(Tree leftTree) { m_leftTree = leftTree; }

	public void setRightTree(Tree rightTree) { m_rightTree = rightTree; }

	public String treeToString() {
		StringBuffer buf = new StringBuffer();

		_preOrderToString(buf);

		return buf.toString();
	}

	private void _preOrderToString(StringBuffer buf) {
		
		buf.append("\n" + m_value.toString());

		if (m_leftTree != null) {
			m_leftTree._preOrderToString(buf);
		}

		if (m_rightTree != null) {
			m_rightTree._preOrderToString(buf);
		}
	}
}