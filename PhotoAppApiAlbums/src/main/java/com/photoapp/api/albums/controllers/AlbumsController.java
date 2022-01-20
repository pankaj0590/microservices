package com.photoapp.api.albums.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.TypeToken;
import com.photoapp.api.albums.entity.AlbumEntity;
import com.photoapp.api.albums.service.AlbumsService;
import com.photoapp.api.albums.ui.model.AlbumResponseModel;

@RestController
@RequestMapping("/albums")
public class AlbumsController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AlbumsService albumsService;
	
	@GetMapping("/status/check")
	public String getStatus() {
		return "Album service is working fine...";
	}
	
	@GetMapping("/user/{id}/albums")
	public List<AlbumResponseModel> userAlbums(@PathVariable int id) {
         List<AlbumResponseModel> returnValue = new ArrayList();
        
        List<AlbumEntity> albumsEntities = albumsService.getAlbums(id);
        if(albumsEntities == null || albumsEntities.isEmpty())
        {
            return returnValue;
        }
        
        Type listType = new TypeToken<List<AlbumResponseModel>>(){}.getType();
 
        returnValue = new ModelMapper().map(albumsEntities, listType);
        logger.info("Returning " + returnValue.size() + " albums");
        return returnValue;
	}
	
	@GetMapping("/user/{id}/{albumId}")
	public String test(@PathVariable int id, @PathVariable int albumId) {
		return "test :"+"UserId: "+id+" AlbumId : " +albumId;
	}

	
}
