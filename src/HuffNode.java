// Alexander Agnitti
// 11/24/17


// A class to be used in conjunction with a Tree class
//		in implementing the Huffman Algorithm.
public class HuffNode {

	private Character m_letter;
	private Integer m_count;
	private String m_code;

	public HuffNode(Character letter, Integer count, String code) {
		
		m_letter = letter;
		m_count = count;
		m_code = code;
	}

	public Character getLetter() { return m_letter; }

	public Integer getCount() { return m_count; }

	public String getCode() { return m_code; }

	public void setCode(String code) { m_code =  code; }

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		// if (m_letter == null) {
		// 	sb.append("*");
		// } else {
		// 	sb.append(m_letter);
		// }
		
		sb.append(m_letter);

		sb.append("(");
		sb.append(Integer.toString(m_count));
		sb.append(")");

		if (m_code != null) {
			sb.append(": " + m_code);
		}

		return sb.toString();
	}
}