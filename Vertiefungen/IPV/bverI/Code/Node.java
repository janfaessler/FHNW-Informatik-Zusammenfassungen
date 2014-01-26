import java.io.Serializable;

/**
 * Node of code tree
 * @author Christoph Stamm
 *
 */
class Node implements Comparable<Node>, Serializable {
	static final long serialVersionUID = 1;
	protected transient double m_p; 	// probability (not needed during decoding)
	protected transient long m_code;	// binary code; maximum 64 bits  (not needed during decoding)
	protected transient byte m_codeLen;	// binary code length (not needed during decoding)
	protected Node m_left, m_right;		// children

	public Node(double p) {
		m_p = p;
	}
	
	public Node(Node left, Node right) {
		m_left = left;
		m_right = right;
		m_p = left.m_p + right.m_p;
	}

	public double getProbability() {
		return m_p;
	}

	public long getCode() {
		return m_code;
	}

	public byte getCodeLen() {
		return m_codeLen;
	}

	public boolean isInnerNode() {
		return m_left != null;
	}
	
	public int compareTo(Node v) {
		if (v != null) {
			if (m_p < v.m_p) {
				return -1;
			} else if (m_p == v.m_p) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return -1;
		}
	}

	public void setCode(long code, int codeLen) {
		m_code = code;
		m_codeLen = (byte)codeLen;

		if (m_left != null) {
			// 0-Bit
			m_left.setCode(code << 1, codeLen + 1);				
		}
		if (m_right != null) {
			// 1-Bit
			m_right.setCode((code << 1) + 1, codeLen + 1);
		}
	}
	
	public Node decodeBit(boolean bit) {
		return (bit) ? m_right : m_left;
	}
}

class Leaf extends Node {
	static final long serialVersionUID = 1;
	private byte m_intensity;	// pixel intensity used during decoding
	
	public Leaf(double p, byte intensity) {
		super(p);
		m_intensity = intensity;
	}

	public byte getIntensity() {
		return m_intensity;
	}
}
