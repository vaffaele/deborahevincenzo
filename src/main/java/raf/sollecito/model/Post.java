package raf.sollecito.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OrderBy;

@Entity
public class Post {

	@Override
	public String toString() {
		return "{id=" + id + ", nome=" + nome + ", filePath=" + filePath + ", fileName=" + fileName + " , description=" + description + "}";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@OrderBy(clause = "id desc")
	private Integer id;

        @Column(columnDefinition = "VARCHAR(6000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
	private String nome;
	private String filePath;
	private String fileName;
	
	@Lob
    private String content;
//	private byte[] fileContent;
	@Column(columnDefinition = "VARCHAR(6000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
	private String description;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy(clause = "data_creazione asc")
	private Set<Commento> commenti ;

	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

//	public byte[] getFileContent() {
//		return fileContent;
//	}
//
//	public void setFileContent(byte[] fileContent) {
//		this.fileContent = fileContent;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
