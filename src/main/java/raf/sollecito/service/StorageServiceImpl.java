package raf.sollecito.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String store(MultipartFile file) {
		try{
			String filePath = System.getProperty("user.dir"); 
			//file.transferTo(new File(filePath));
			byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath + file.getOriginalFilename());
            Files.write(path, bytes);
	        System.out.println("File has been written");
	        return filePath + file.getOriginalFilename();
	    }catch(Exception e){
	        System.out.println("Could not create file"+e.getMessage());
	    }
		return null;
		
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource loadAsResource(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
