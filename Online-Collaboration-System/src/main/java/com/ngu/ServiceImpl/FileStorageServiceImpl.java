/**
 * 
 */
package com.ngu.ServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.transaction.Transactional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ngu.Exception.FileStorageException;
import com.ngu.Exception.MyFileNotFoundException;
import com.ngu.Service.FileStorageService;

@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {

	private final Path fileStorageLocation;

	public FileStorageServiceImpl() {
		this.fileStorageLocation = Paths.get(System.getProperty("user.dir") + "/upload").toAbsolutePath().normalize();

		System.out.println(fileStorageLocation);

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored",
					ex);
		}
	}

	@Override
	public String storeFile(MultipartFile file) throws IOException {

		File f = new File(fileStorageLocation + file.getOriginalFilename());
		
		f.createNewFile();
		FileOutputStream fout = new FileOutputStream(f);
		fout.write(file.getBytes());
		fout.close();
		if (f.exists())
			f.delete();

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence" + fileName);
			}
			String newFileName = System.currentTimeMillis() + "_POSTIMAGE" + fileName;
			Path targetLocation = this.fileStorageLocation.resolve(newFileName);
		
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return newFileName;
		} catch (IOException ex) {
			throw new FileStorageException(String.format("Could not store file %s !! Please try again!", fileName), ex);
		}

	}

	@Override
	public Resource loadFileAsResource(String fileName) throws IOException {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}

	@Override
	public void deleteFile(String fileName) throws IOException {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				System.out.println("From DeleteFileResource ::" + resource.getFilename());
				System.out.println("From DeleteFileResource ::" + resource.getFile().getAbsolutePath());
				resource.getFile().delete();
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}

	@Override
	public Path rootLocation() {
		// TODO Auto-generated method stub
		return this.fileStorageLocation;
	}

}
