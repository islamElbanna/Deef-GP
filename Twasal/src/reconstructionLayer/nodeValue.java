import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.LabelFactory;


public class nodeValue implements Label {
	private String vString;
	public nodeValue (String value ){
		vString = value;
	}
	@Override
	public LabelFactory labelFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFromString(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(String arg0) {
		// TODO Auto-generated method stub
		vString = arg0;
	}

	@Override
	public String value() {
		// TODO Auto-generated method stub
		return vString;
	}

}
