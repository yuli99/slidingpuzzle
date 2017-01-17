package com.wei.slidingpuzzle.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wei.slidingpuzzle.entity.PuzzleImage;
import com.wei.slidingpuzzle.entity.PuzzleUser;
import com.wei.slidingpuzzle.repository.ImageRepository;
import com.wei.slidingpuzzle.repository.UserRepository;
import com.wei.slidingpuzzle.util.SeamCarver;
import com.wei.slidingpuzzle.util.UploadedImage;


@Service
public class ImageService {
	
	@Autowired 
	private ImageRepository imageRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	public void resizeAndSaveImage(String userName, MultipartFile image, String rootDirectory) {
		
		String uniqueImageName = getUniqueImageName();
		Path path = Paths.get(rootDirectory + "/resources/puzzleimages/" + uniqueImageName);
		File file = new File(path.toString());
		
		if(image != null && !image.isEmpty()){
			try {
				image.transferTo(file);
            } catch (Exception exc){
                exc.printStackTrace();
                throw new RuntimeException("Image transfer failed", exc);
            }
        }
		
		UploadedImage resizedImage = resizeUploadedImage(new UploadedImage(file));
		
		resizedImage.save(file);
		
		PuzzleImage puzzleImage = new PuzzleImage(uniqueImageName);
		puzzleImage.setUser(userRepository.findByUserName(userName));
		puzzleImage.setUploadDate(new Timestamp(Calendar.getInstance()
				.getTime().getTime()));
		
		imageRepository.save(puzzleImage);
	}
	
	private String getUniqueImageName() {
		String imagePrefix = "Image";
		String imageSuffix = ".jpg";
		String middle ="";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		middle = sdf.format(new Date());
		
		return imagePrefix + middle + imageSuffix;
	}
	
	// resize uploaded image to 600 pixel x 600 pixel
	private UploadedImage resizeUploadedImage(UploadedImage image) {
		
		SeamCarver seamCarver = new SeamCarver(image);
		int removeSeams = image.getHeight() - image.getWidth();
		
		if (removeSeams > 0) {
			for (int i = 0; i < removeSeams; i++) {
		        int[] horizontalSeam = seamCarver.findHorizontalSeam();
		        seamCarver.removeHorizontalSeam(horizontalSeam);
		    }
		}
		else {
			for (int i = 0; i < - removeSeams; i++) {
		        int[] verticalSeam = seamCarver.findVerticalSeam();
		        seamCarver.removeVerticalSeam(verticalSeam);
		    }
		}
		
		UploadedImage resizedImage = new UploadedImage(600, 600);
		BufferedImage squareImage = seamCarver.getCurrImage().getImage();
		
		Graphics2D g = resizedImage.getImage().createGraphics();
		g.drawImage(squareImage, 0, 0, 600, 600, null);
		g.dispose();
		     
        return resizedImage;
	}
	
	@PreAuthorize("#image.user.userName == authentication.name or hasRole('ROLE_ADMIN')")
	public void deleteImage(@P("image") PuzzleImage image, String rootDirectory) {
	
		Path path = Paths.get(rootDirectory + "/resources/puzzleimages/" + image.getImageUri());

	    if(Files.exists(path)){
	    	try {
	    		Files.delete(path);
	        } catch (Exception exc){
	        	exc.printStackTrace();
	        }
	    }
		
		imageRepository.delete(image);
	}
	
	public PuzzleImage loadImageById(Long imageId) {
		return imageRepository.findOne(imageId);
	}
	
	public List<PuzzleImage> loadImagesByUser(String userName) {
		PuzzleUser user = userRepository.findByUserName(userName);
		return imageRepository.findByUser(user);
	} 

}
