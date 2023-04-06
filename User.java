public class User {
	private String nome;
	private int topico;
	private String body;
	private String flag;
	
	public User(String nome, int topico) {
		this.nome = nome;
		this.topico = topico;
		this.body = "";
		this.flag = "";
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public int getTopico() {
		return topico;
	}
	
	public void setTopico(int topico) {
		this.topico = topico;
	}
	
}
