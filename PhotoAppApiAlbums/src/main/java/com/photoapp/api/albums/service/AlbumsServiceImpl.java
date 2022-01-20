package com.photoapp.api.albums.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.photoapp.api.albums.entity.AlbumEntity;

@Service
public class AlbumsServiceImpl implements AlbumsService {

	@Override
	public List<AlbumEntity> getAlbums(int id) {
    List<AlbumEntity> returnValue = new ArrayList<AlbumEntity>();
        
        AlbumEntity albumEntity = new AlbumEntity();
       
        albumEntity.setAlbumId("album1Id");
        albumEntity.setDescription("album 1 description");
        albumEntity.setId(id);
        albumEntity.setName("album 1 name");
        returnValue.add(albumEntity);
        return returnValue;
	}

}
