package hello;

public class UpdatePacket {

	private String content;
	
	public UpdatePacket(String content) {
		this.content = content;
		
	}
	//important for JSON PARSE
	  public String getContent() {
	      return content;
	    }
	 

}
