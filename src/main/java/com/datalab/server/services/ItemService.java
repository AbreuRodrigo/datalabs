package com.datalab.server.services;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.datalab.server.entity.Item;
import com.datalab.server.utils.MongoDB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

@Path("/item")
public class ItemService {
	private final MongoDB mongoDB;
	
	public ItemService() {
		mongoDB = new MongoDB();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void saveItem(Item item) throws IOException {
		System.out.println(item.toString());
		
//		String newFileName = "mkyong-java-image";
//		
//		File imageFile = new File("c:\\JavaWebHosting.png");
//		
//		GridFS gfsPhoto = new GridFS(mongoDB.getDB(), "photo");
//		GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
//		
//		gfsFile.setFilename(newFileName);
//		
//		gfsFile.save();
	}
}
