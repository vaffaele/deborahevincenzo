package raf.sollecito.model;

import java.util.List;
import java.util.Set;

public class PostFrontend {
	private Integer id;
	private String nome;
	private String filePath;
	private String fileName;

	private String fileContent;
	private String description;
	private Set<Commento> commenti ;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(Set<Commento> commenti) {
		this.commenti = commenti;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}




}
